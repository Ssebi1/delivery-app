package com.sebi.deliver.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sebi.deliver.dto.UserRequest;
import com.sebi.deliver.model.User;
import com.sebi.deliver.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = UserController.class)
public class UserControllerIT {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private UserService userService;

    @Test
    public void createUser() throws Exception {
        UserRequest request = new UserRequest("Name", "Password", "Email");

        when(userService.register(any())).thenReturn(new User(1L, "Name", "Email", "Password", "Phone", "Email", "City", "notes", null, false));

        mockMvc.perform(post("/api/users/register")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status() .isOk())
                .andExpect(jsonPath("$.name").value(request.getName()))
                .andExpect(jsonPath("$.email").value(request.getEmail()));
    }

    @Test
    public void loginUser() throws Exception {
        UserRequest request = new UserRequest("Name", "Password", "Email");

        when(userService.login(any())).thenReturn(new User(1L, "Name", "Email", "Password", "Phone", "Email", "City", "notes", null, false));

        mockMvc.perform(post("/api/users/login")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status() .isOk())
                .andExpect(jsonPath("$.name").value(request.getName()))
                .andExpect(jsonPath("$.email").value(request.getEmail()));
    }

    @Test
    public void deleteUser() throws Exception {
        User user = new User(1L, "Name", "Email", "Password", "Phone", "Email", "City", "notes", null, false);

        when(userService.deleteUser(anyLong())).thenReturn(user);

        mockMvc.perform(delete("/api/users/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(user.getName()))
                .andExpect(jsonPath("$.email").value(user.getEmail()));
    }

    @Test
    public void getUser() throws Exception {
        User user = new User(1L, "Name", "Email", "Password", "Phone", "Email", "City", "notes", null, false);

        when(userService.getUser(anyLong())).thenReturn(user);

        mockMvc.perform(get("/api/users/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(user.getName()))
                .andExpect(jsonPath("$.email").value(user.getEmail()));
    }

    @Test
    public void updateUser() throws Exception {
        UserRequest request = new UserRequest("Name", "Password", "Email");
        User user = new User(1L, "Name", "Email", "Password", "Phone", "Email", "City", "notes", null, false);

        when(userService.updateUser(anyLong(), any())).thenReturn(user);

        mockMvc.perform(put("/api/users/{id}", 1L)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(user.getName()))
                .andExpect(jsonPath("$.email").value(user.getEmail()));
    }
}
