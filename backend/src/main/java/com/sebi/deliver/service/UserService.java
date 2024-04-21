package com.sebi.deliver.service;

import com.sebi.deliver.exception.GenericException;
import com.sebi.deliver.exception.MissingFieldsException;
import com.sebi.deliver.exception.user.EmailAlreadyExistsException;
import com.sebi.deliver.exception.user.WrongCredentialsException;
import com.sebi.deliver.model.ApiError;
import com.sebi.deliver.model.User;
import com.sebi.deliver.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sebi.deliver.utils.Hash;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User register(User user) {
        if (user.getName().isEmpty() || user.getEmail().isEmpty() || user.getPassword().isEmpty()) {
            throw new MissingFieldsException();
        }
        Optional<User> userByEmail = userRepository.findByEmail(user.getEmail());
        if (userByEmail.isPresent()) {
            throw new EmailAlreadyExistsException();
        }

        if (user.isAdmin()) {
            // check if there is already an admin
            Optional<User> admin = userRepository.findAdmin();
            if (admin.isPresent()) { throw new GenericException(); }
        }

        // hash password
        String newPassword = Hash.hash(user.getPassword());
        user.setPassword(newPassword);
        userRepository.save(user);
        return user;
    }

    public User login(User user) {
        if (user.getEmail().isEmpty() || user.getPassword().isEmpty()) { throw new MissingFieldsException(); }
        Optional<User> userByEmail = userRepository.findByEmail(user.getEmail());
        if (userByEmail.isEmpty()) { throw new WrongCredentialsException(); }

        User userFromDb = userByEmail.get();
        String hashedPassword = userFromDb.getPassword();
        if (!Hash.check(user.getPassword(), hashedPassword)) { throw new WrongCredentialsException(); }
        return userFromDb;
    }

    public User deleteUser(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new GenericException();
        }
        userRepository.deleteById(id);
        return user.get();
    }

    public User getUser(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) { throw new GenericException(); }
        return user.get();
    }

    public User updateUser(Long id, User user) {
        Optional<User> userFromDb = userRepository.findById(id);
        if (userFromDb.isEmpty()) { throw new GenericException(); }
        User userToUpdate = userFromDb.get();
        if (!user.getName().isEmpty()) { userToUpdate.setName(user.getName()); }
        if (!user.getEmail().isEmpty()) { userToUpdate.setEmail(user.getEmail()); }
        if (user.getAddress() != null && !user.getAddress().isEmpty()) { userToUpdate.setAddress(user.getAddress()); }
        if (user.getCity() != null && !user.getCity().isEmpty()) { userToUpdate.setCity(user.getCity()); }
        if (user.getPhone() != null && !user.getPhone().isEmpty()) { userToUpdate.setPhone(user.getPhone()); }
        if (user.getNotes() != null && !user.getNotes().isEmpty()) { userToUpdate.setNotes(user.getNotes()); }
        userRepository.save(userToUpdate);
        return userToUpdate;
    }
}
