package com.sebi.deliver.service;

import com.sebi.deliver.exception.GenericException;
import com.sebi.deliver.exception.MissingFieldsException;
import com.sebi.deliver.exception.user.EmailAlreadyExistsException;
import com.sebi.deliver.exception.user.WrongCredentialsException;
import com.sebi.deliver.model.User;
import com.sebi.deliver.repository.UserRepository;
import jdk.jfr.Description;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import com.sebi.deliver.utils.Hash;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    @Description("Test for registering a user")
    void register() {
        // arrange
        User user = new User("Sebi", "test@yahoo.com", "test");
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.empty());
        User savedUser = new User(1L, "Sebi", "test@yahoo.com", "test");
        when(userRepository.save(user)).thenReturn(savedUser);

        // act
        User registeredUser = userService.register(user);

        // assert
        assertNotNull(registeredUser);
        assertEquals(savedUser.getName(), registeredUser.getName());
        assertEquals(savedUser.getEmail(), registeredUser.getEmail());
        assertNotEquals(savedUser.getPassword(), registeredUser.getPassword());

        verify(userRepository).findByEmail(user.getEmail());
        verify(userRepository).save(user);
    }

    @Test
    @Description("Test for registering an admin user")
    void registerAdmin() {
        // arrange
        User user = new User("Sebi", "test@yahoo.com", "test");
        user.setAdmin(true);
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.empty());

        // act
        User registeredUser = userService.register(user);

        // assert
        assertNotNull(registeredUser);
        assertEquals(user.getName(), registeredUser.getName());
        assertEquals(user.getEmail(), registeredUser.getEmail());
        assertEquals(user.isAdmin(), registeredUser.isAdmin());
    }

    @Test
    @Description("Test for registering an admin user when there's already an admin")
    void registerAdmin_adminExists_throwsException() {
        // arrange
        User adminUser = new User("admin", "admin@yahoo.com", "admin");
        adminUser.setAdmin(true);
        User user = new User("Sebi", "test@yahoo.com", "test");
        user.setAdmin(true);
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.empty());
        when(userRepository.findAdmin()).thenReturn(Optional.of(adminUser));

        // act
        GenericException exception = assertThrows(GenericException.class,
                () -> userService.register(user));

        // assert
        assertNotNull(exception);
        assertEquals("Could not process request. Please try again later", exception.getMessage());
    }

    @Description("Test for registering a user with missing fields")
    @ParameterizedTest
    @ValueSource(strings = {"name", "email", "password"})
    void register_missing_fields_throwsException(String missingField) {
        // arrange
        String name = "Sebi", email = "test@yahoo.com", password = "test";
        if (missingField.equals("name")) { name = ""; }
        if (missingField.equals("email")) { email = ""; }
        if (missingField.equals("password")) { password = ""; }
        User user = new User(name, email, password);

        // act
        MissingFieldsException exception = assertThrows(MissingFieldsException.class,
                () -> userService.register(user));

        // assert
        assertNotNull(exception);
        assertEquals("Missing fields", exception.getMessage());
    }

    @Test
    @Description("Test for registering a user with email already existing")
    void register_mail_already_exists_throwsException() {
        // arrange
        String name = "Sebi", email = "test@yahoo.com", password = "test";
        User user = new User(name, email, password);
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        // act
        EmailAlreadyExistsException exception = assertThrows(EmailAlreadyExistsException.class,
                () -> userService.register(user));

        // assert
        assertNotNull(exception);
        assertEquals("There's already an account with this email", exception.getMessage());
    }

    @Test
    @Description("Test for logging in a user")
    void login_isSuccess() {
        // arrange
        String name = "Sebi", email = "test@yahoo.com", password = Hash.hash("test");
        User user = new User(name, email, password);
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        User newUser = new User(name, email, "test");

        // act
        User loggedInUser = userService.login(newUser);

        // assert
        assertNotNull(loggedInUser);
        assertEquals(user.getName(), loggedInUser.getName());
        assertEquals(user.getEmail(), loggedInUser.getEmail());
    }

    @Test
    @Description("Test for logging in a user with wrong credentials")
    void login_wrongCredentials_throwsException() {
        // arrange
        String name = "Sebi", email = "test@yahoo.com", password = "test";
        User user = new User(name, email, password);
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        user.setPassword("wrongPassword");

        // act
        WrongCredentialsException exception = assertThrows(WrongCredentialsException.class,
                () -> userService.login(user));

        // assert
        assertNotNull(exception);
        assertEquals("Wrong credentials", exception.getMessage());
    }

    @Test
    @Description("Test for getting a user")
    void getUser_isSuccess() {
        // arrange
        String name = "Sebi", email = "test@yahoo.com", password = "test";
        User user = new User(1L, name, email, password);
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        // act
        User getUser = userService.getUser(user.getId());

        // assert
        assertNotNull(getUser);
        assertEquals(user.getName(), getUser.getName());
        assertEquals(user.getEmail(), getUser.getEmail());
    }

    @Test
    @Description("Test for getting a user that does not exist")
    void getUser_notExisting_throwsException() {
        // arrange
        String name = "Sebi", email = "test@yahoo.com", password = "test";
        User user = new User(1L, name, email, password);

        // act
        GenericException exception = assertThrows(GenericException.class,
                () -> userService.getUser(user.getId()));

        // assert
        assertNotNull(exception);
        assertEquals("Could not process request. Please try again later", exception.getMessage());
    }

    @Test
    @Description("Test for deleting a user")
    void deleteUser_isSuccess() {
        // arrange
        String name = "Sebi", email = "test@yahoo.com", password = "test";
        User user = new User(1L, name, email, password);
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        // act
        User deletedUser = userService.deleteUser(user.getId());

        // assert
        assertNotNull(deletedUser);
        assertEquals(user.getName(), deletedUser.getName());
        assertEquals(user.getEmail(), deletedUser.getEmail());
    }

    @Test
    @Description("Test for deleting a user that does not exist")
    void deleteUser_notExisting_throwsException() {
        // arrange
        String name = "Sebi", email = "test@yahoo.com", password = "test";
        User user = new User(1L, name, email, password);

        // act
        GenericException exception = assertThrows(GenericException.class,
                () -> userService.deleteUser(user.getId()));

        // assert
        assertNotNull(exception);
        assertEquals("Could not process request. Please try again later", exception.getMessage());
    }

    @Test
    @Description("Test for updating a user")
    void updateUser_isSuccess() {
        // arrange
        String name = "Sebi", email = "test@yahoo.com", password = "test";
        User user = new User(1L, name, email, password);
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        // act
        User updateUser = userService.updateUser(user.getId(), user);

        // assert
        assertNotNull(updateUser);
        assertEquals(user.getName(), updateUser.getName());
        assertEquals(user.getEmail(), updateUser.getEmail());
        assertEquals(user.getAddress(), updateUser.getAddress());

    }
}