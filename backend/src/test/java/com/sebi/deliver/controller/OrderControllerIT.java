package com.sebi.deliver.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sebi.deliver.model.Order;
import com.sebi.deliver.model.security.User;
import com.sebi.deliver.service.OrderService;
import com.sebi.deliver.service.UserService;
import com.sebi.deliver.service.security.JWTUtils;
import com.sebi.deliver.service.security.UserDetailsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = OrderController.class)
@AutoConfigureMockMvc(addFilters = false)
public class OrderControllerIT {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private UserService userService;
    @MockBean
    private OrderService orderService;
    @MockBean
    private JWTUtils jwtUtils;
    @MockBean
    private UserDetailsService userDetailsService;

//    @Test
//    public void addOrder() throws Exception {
//        User user = new User(1L, "Name", "Email", "Password", "Phone", "Email", "City", "notes", false);
//        when(userService.getUser(anyLong())).thenReturn(user);
//        Order order = new Order(1L, user, "products", 10.0, "date");
//        when(orderService.addOrder(anyLong(), any())).thenReturn(new Order(1L, user, "products", 10.0, "date"));
//
//        mockMvc.perform(post("/api/orders/{id}", 1L)
//                        .contentType("application/json")
//                        .content(objectMapper.writeValueAsString(order)))
//                .andExpect(status() .isOk())
//                .andExpect(jsonPath("$.products").value(order.getProducts()))
//                .andExpect(jsonPath("$.price").value(order.getPrice()));
//    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void getAllOrders() throws Exception {
        User user = new User(1L, "Name", "Email", "Password", "Phone", "Email", "City", "notes", false);
        Order order = new Order(1L, user, "products", 10.0, "date");
        when(orderService.getAllOrders()).thenReturn(java.util.List.of(order));

        mockMvc.perform(get("/api/orders/admin"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].products").value(order.getProducts()))
                .andExpect(jsonPath("$[0].price").value(order.getPrice()));
    }

    @Test
    @WithMockUser(roles = "USER")
    public void getUserOrders() throws Exception {
        User user = new User(1L, "Name", "Email", "Password", "Phone", "Email", "City", "notes", false);
        Order order = new Order(1L, user, "products", 10.0, "date");
        when(orderService.getUserOrders(anyLong())).thenReturn(java.util.List.of(order));

        mockMvc.perform(get("/api/orders/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].products").value(order.getProducts()))
                .andExpect(jsonPath("$[0].price").value(order.getPrice()));
    }
}
