package com.sebi.deliver.service;

import com.sebi.deliver.exception.GenericException;
import com.sebi.deliver.exception.MissingFieldsException;
import com.sebi.deliver.exception.user.EmailAlreadyExistsException;
import com.sebi.deliver.exception.user.WrongCredentialsException;
import com.sebi.deliver.model.User;
import com.sebi.deliver.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sebi.deliver.utils.Hash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User register(User user) {
        logger.info("Registering user with email: {}", user.getEmail());

        if (user.getName().isEmpty() || user.getEmail().isEmpty() || user.getPassword().isEmpty()) {
            logger.error("Missing fields.");
            throw new MissingFieldsException();
        }
        Optional<User> userByEmail = userRepository.findByEmail(user.getEmail());
        if (userByEmail.isPresent()) {
            logger.error("Email {} already exists.", user.getEmail());
            throw new EmailAlreadyExistsException();
        }

        if (user.isAdmin()) {
            logger.info("Trying to create an admin user. Checking if there is already an admin.");
            // check if there is already an admin
            Optional<User> admin = userRepository.findAdmin();
            if (admin.isPresent()) {
                logger.error("Admin already exists.");
                throw new GenericException();
            }
        }

        // hash password
        String newPassword = Hash.hash(user.getPassword());
        user.setPassword(newPassword);
        userRepository.save(user);
        logger.info("User {} with email {} registered successfully.", user.getName(), user.getEmail());
        return user;
    }

    public User login(User user) {
        logger.info("Logging in user with email: {}", user.getEmail());

        if (user.getEmail().isEmpty() || user.getPassword().isEmpty()) { throw new MissingFieldsException(); }
        Optional<User> userByEmail = userRepository.findByEmail(user.getEmail());
        if (userByEmail.isEmpty()) {
            logger.error("User with email {} not found.", user.getEmail());
            throw new WrongCredentialsException();
        }

        User userFromDb = userByEmail.get();
        String hashedPassword = userFromDb.getPassword();
        if (!Hash.check(user.getPassword(), hashedPassword)) { throw new WrongCredentialsException(); }
        logger.info("User {} with email {} logged in successfully.", userFromDb.getName(), userFromDb.getEmail());
        return userFromDb;
    }

    public User deleteUser(Long id) {
        logger.info("Deleting user with id: {}", id);
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            logger.error("User with id {} not found.", id);
            throw new GenericException();
        }
        userRepository.deleteById(id);
        logger.info("User with id {} deleted successfully.", id);
        return user.get();
    }

    public User getUser(Long id) {
        logger.info("Getting user with id: {}", id);
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            logger.error("User with id {} not found.", id);
            throw new GenericException();
        }
        return user.get();
    }

    public User updateUser(Long id, User user) {
        logger.info("Updating user with id: {}", id);
        Optional<User> userFromDb = userRepository.findById(id);
        if (userFromDb.isEmpty()) {
            logger.error("User with id {} not found.", id);
            throw new GenericException();
        }
        User userToUpdate = userFromDb.get();
        if (!user.getName().isEmpty()) { userToUpdate.setName(user.getName()); }
        if (!user.getEmail().isEmpty()) { userToUpdate.setEmail(user.getEmail()); }
        if (user.getAddress() != null && !user.getAddress().isEmpty()) { userToUpdate.setAddress(user.getAddress()); }
        if (user.getCity() != null && !user.getCity().isEmpty()) { userToUpdate.setCity(user.getCity()); }
        if (user.getPhone() != null && !user.getPhone().isEmpty()) { userToUpdate.setPhone(user.getPhone()); }
        if (user.getNotes() != null && !user.getNotes().isEmpty()) { userToUpdate.setNotes(user.getNotes()); }
        userRepository.save(userToUpdate);
        logger.info("User with id {} updated successfully.", id);
        return userToUpdate;
    }
}
