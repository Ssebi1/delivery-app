package com.sebi.deliver.service;

import com.sebi.deliver.exception.GenericException;
import com.sebi.deliver.exception.MissingFieldsException;
import com.sebi.deliver.exception.product.SalePriceBiggerThanPriceException;
import com.sebi.deliver.model.Message;
import com.sebi.deliver.model.Product;
import com.sebi.deliver.model.User;
import com.sebi.deliver.repository.MessageRepository;
import com.sebi.deliver.repository.ProductRepository;
import com.sebi.deliver.repository.UserRepository;
import jdk.jfr.Description;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MessageServiceTest {

    @Mock
    private MessageRepository messageRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private MessageService messageService;

    @Test
    @Description("Create message")
    void addMessage_isSuccess() {
        // Arrange
        Message message = new Message();
        message.setMessage("Message");
        message.setName("Name");
        message.setEmail("Email");
        message.setPhone("Phone");
        User user = new User();
        user.setId(1L);
        user.setName("Username");
        user.setPassword("Password");
        user.setAddress("Address");
        user.setPhone("Phone");
        user.setEmail("Email");
        message.setUser(user);
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        // Act
        Message createdMessage = messageService.addMessage(user.getId(), message);

        // Assert
        assertEquals(message.getMessage(), createdMessage.getMessage());
        assertEquals(message.getName(), createdMessage.getName());
        assertEquals(message.getEmail(), createdMessage.getEmail());
        assertEquals(message.getPhone(), createdMessage.getPhone());
        assertEquals(message.getUser(), createdMessage.getUser());
    }

    @Description("Create message with missing fields")
    @ParameterizedTest
    @ValueSource(strings = {"message", "name", "email", "phone"})
    void addMessage_missingFields_throwsError(String missingField) {
        // Arrange
        Message message = new Message();
        message.setMessage("Message");
        message.setName("Name");
        message.setEmail("Email");
        message.setPhone("Phone");
        if(Objects.equals(missingField, "message")) {message.setMessage("");}
        if(Objects.equals(missingField, "name")) {message.setName("");}
        if(Objects.equals(missingField, "email")) {message.setEmail("");}
        if(Objects.equals(missingField, "phone")) {message.setPhone("");}
        User user = new User();
        user.setId(1L);
        user.setName("Username");
        user.setPassword("Password");
        user.setAddress("Address");
        user.setPhone("Phone");
        user.setEmail("Email");
        message.setUser(user);
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        // act
        MissingFieldsException exception = assertThrows(MissingFieldsException.class,
                () -> messageService.addMessage(user.getId(), message));

        // assert
        assertNotNull(exception);
        assertEquals("Missing fields", exception.getMessage());
    }

    @Description("Create message with missing fields")
    @Test
    void addMessage_missingUser_throwsError() {
        // Arrange
        Message message = new Message();
        message.setMessage("Message");
        message.setName("Name");
        message.setEmail("Email");
        message.setPhone("Phone");

        // act
        GenericException exception = assertThrows(GenericException.class,
                () -> messageService.addMessage(1L, message));

        // assert
        assertNotNull(exception);
        assertEquals("Could not process request. Please try again later", exception.getMessage());
    }

    @Test
    @Description("Test for getting all messages")
    void getAllMessages_isSuccess() {
        // arrange
        Message message = new Message();
        message.setMessage("Message");
        message.setName("Name");
        message.setEmail("Email");
        message.setPhone("Phone");
        User user = new User();
        user.setId(1L);
        user.setName("Username");
        user.setPassword("Password");
        user.setAddress("Address");
        user.setPhone("Phone");
        user.setEmail("Email");
        message.setUser(user);
        when(messageRepository.findAll()).thenReturn(List.of(message));

        // act
        List<Message> messages = messageService.getAllMessages();

        // assert
        assertEquals(1, messages.size());
        assertEquals(message, messages.get(0));
    }

    @Test
    @Description("Test for getting user messages")
    void getUserMessages_isSuccess() {
        // arrange
        Message message = new Message();
        message.setMessage("Message");
        message.setName("Name");
        message.setEmail("Email");
        message.setPhone("Phone");
        User user = new User();
        user.setId(1L);
        user.setName("Username");
        user.setPassword("Password");
        user.setAddress("Address");
        user.setPhone("Phone");
        user.setEmail("Email");
        message.setUser(user);
        when(messageRepository.findByUserId(user.getId())).thenReturn(List.of(message));

        // act
        List<Message> messages = messageService.getUserMessages(user.getId());

        // assert
        assertEquals(1, messages.size());
        assertEquals(message, messages.get(0));
    }
}
