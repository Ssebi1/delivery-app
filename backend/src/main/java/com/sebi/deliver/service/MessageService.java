package com.sebi.deliver.service;

import com.sebi.deliver.exception.GenericException;
import com.sebi.deliver.exception.MissingFieldsException;
import com.sebi.deliver.model.CartItem;
import com.sebi.deliver.model.Message;
import com.sebi.deliver.model.Order;
import com.sebi.deliver.model.User;
import com.sebi.deliver.repository.CartRepository;
import com.sebi.deliver.repository.MessageRepository;
import com.sebi.deliver.repository.OrderRepository;
import com.sebi.deliver.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class MessageService {

    private final UserRepository userRepository;
    private final MessageRepository messageRepository;


    @Autowired
    public MessageService(UserRepository userRepository, MessageRepository messageRepository) {
        this.userRepository = userRepository;
        this.messageRepository = messageRepository;
    }

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    public List<Message> getUserMessages(Long id) {
        return messageRepository.findByUserId(id);
    }

    public Message addMessage(Long id, Message message) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new GenericException();
        }
        if (message.getMessage().isEmpty() || message.getName().isEmpty() || message.getEmail().isEmpty() || message.getPhone().isEmpty()) {
            throw new MissingFieldsException();
        }
        message.setUser(user.get());
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        message.setDate(formatter.format(new Date()));
        messageRepository.save(message);
        return message;
    }
}
