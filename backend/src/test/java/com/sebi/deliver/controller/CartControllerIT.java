package com.sebi.deliver.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sebi.deliver.model.CartItem;
import com.sebi.deliver.model.Product;
import com.sebi.deliver.model.User;
import com.sebi.deliver.service.CartService;
import com.sebi.deliver.service.ProductService;
import com.sebi.deliver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CartController.class)
public class CartControllerIT {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private CartService cartService;
    @MockBean
    private UserService userService;
    @MockBean
    private ProductService productService;

    @Test
    public void getCart() throws Exception {
        User user = new User(1L, "Name", "Email", "Password", "Phone", "Email", "City", "notes", null, false);
        Product product = new Product(1L, "Name", "Description", 10.0, 5.0, 5.0, "https://images.pexels.com/photos/1640777/pexels-photo-1640777.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940");
        CartItem cartItem = new CartItem(1L, user, product, 1);
        when(cartService.getCart(anyLong())).thenReturn(java.util.List.of(cartItem));

        mockMvc.perform(get("/api/cart/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].quantity").value(cartItem.getQuantity()));
    }

    @Test
    public void deleteProductFromCart() throws Exception {
        User user = new User(1L, "Name", "Email", "Password", "Phone", "Email", "City", "notes", null, false);
        Product product = new Product(1L, "Name", "Description", 10.0, 5.0, 5.0, "https://images.pexels.com/photos/1640777/pexels-photo-1640777.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940");
        CartItem cartItem = new CartItem(1L, user, product, 1);
        when(cartService.deleteProductFromCart(anyLong(), anyLong())).thenReturn(cartItem);

        mockMvc.perform(delete("/api/cart/{id}/{product_id}", 1L, 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.quantity").value(cartItem.getQuantity()));
    }

    @Test
    public void addProductToCart() throws Exception {
        User user = new User(1L, "Name", "Email", "Password", "Phone", "Email", "City", "notes", null, false);
        Product product = new Product(1L, "Name", "Description", 10.0, 5.0, 5.0, "https://images.pexels.com/photos/1640777/pexels-photo-1640777.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940");
        CartItem cartItem = new CartItem(1L, user, product, 1);
        when(cartService.addProductToCart(anyLong(), anyLong())).thenReturn(cartItem);

        mockMvc.perform(post("/api/cart/{id}/{product_id}", 1L, 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.quantity").value(cartItem.getQuantity()));
    }

    @Test
    public void deleteCart() throws Exception {
        User user = new User(1L, "Name", "Email", "Password", "Phone", "Email", "City", "notes", null, false);
        Product product = new Product(1L, "Name", "Description", 10.0, 5.0, 5.0, "https://images.pexels.com/photos/1640777/pexels-photo-1640777.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940");
        CartItem cartItem = new CartItem(1L, user, product, 1);

        mockMvc.perform(delete("/api/cart/{id}", 1L))
                .andExpect(status().isOk());
    }
}
