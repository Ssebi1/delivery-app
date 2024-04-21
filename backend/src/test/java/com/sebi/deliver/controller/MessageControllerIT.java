package com.sebi.deliver.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sebi.deliver.dto.MessageRequest;
import com.sebi.deliver.dto.UserRequest;
import com.sebi.deliver.model.Message;
import com.sebi.deliver.model.User;
import com.sebi.deliver.service.MessageService;
import com.sebi.deliver.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = MessageController.class)
public class MessageControllerIT {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private MessageService messageService;
    @MockBean
    private UserService userService;

    @Test
    public void createMessage() throws Exception {
        MessageRequest request = new MessageRequest("Name", "Email", "Message", "Phone", "Company");
        User user = new User(1L, "Name", "Email", "Password", "City", "Phone", "Address", "Notes", null, false);

        when(userService.getUser(anyLong())).thenReturn(user);
        when(messageService.addMessage(anyLong(), any())).thenReturn(new Message(1L, user, "Message", "Date", "Name", "Email", "Phone"));

        mockMvc.perform(post("/api/messages/{id}", 1L)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status() .isOk())
                .andExpect(jsonPath("$.name").value(request.getName()))
                .andExpect(jsonPath("$.email").value(request.getEmail()))
                .andExpect(jsonPath("$.message").value(request.getMessage()))
                .andExpect(jsonPath("$.phone").value(request.getPhone()));
    }

    @Test
    public void getAllMessages() throws Exception {
        Message message = new Message(1L, null, "Message", "Date", "Name", "Email", "Phone");
        when(messageService.getAllMessages()).thenReturn(java.util.List.of(message));

        mockMvc.perform(get("/api/messages"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(message.getName()))
                .andExpect(jsonPath("$[0].email").value(message.getEmail()))
                .andExpect(jsonPath("$[0].message").value(message.getMessage()))
                .andExpect(jsonPath("$[0].phone").value(message.getPhone()));
    }

    @Test
    public void getUserMessages() throws Exception {
        Message message = new Message(1L, null, "Message", "Date", "Name", "Email", "Phone");
        when(messageService.getUserMessages(anyLong())).thenReturn(java.util.List.of(message));

        mockMvc.perform(get("/api/messages/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(message.getName()))
                .andExpect(jsonPath("$[0].email").value(message.getEmail()))
                .andExpect(jsonPath("$[0].message").value(message.getMessage()))
                .andExpect(jsonPath("$[0].phone").value(message.getPhone()));
    }
}
