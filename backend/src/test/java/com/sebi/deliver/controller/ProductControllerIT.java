package com.sebi.deliver.controller;

import com.fasterxml.jackson.databind.*;
import com.sebi.deliver.dto.ProductRequest;
import com.sebi.deliver.model.Product;
import com.sebi.deliver.service.ProductService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.autoconfigure.web.servlet.*;
import org.springframework.boot.test.mock.mockito.*;
import org.springframework.test.web.servlet.*;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ProductController.class)
public class ProductControllerIT {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private ProductService productService;

    @Test
    public void createProduct() throws Exception {
        ProductRequest request = new ProductRequest("Name", "Descrption", 10.0, 5.0, 5.0, "https://images.pexels.com/photos/1640777/pexels-photo-1640777.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940");

        when(productService.createProduct(any())).thenReturn(new Product(1L, "Name", "Descrption", 10.0, 5.0, 5.0, "https://images.pexels.com/photos/1640777/pexels-photo-1640777.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940"));

        mockMvc.perform(post("/api/products")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status() .isOk())
                .andExpect(jsonPath("$.name").value(request.getName()))
                .andExpect(jsonPath("$.price").value(request.getPrice()))
                .andExpect(jsonPath("$.description").value(request.getDescription()))
                .andExpect(jsonPath("$.salePrice").value(request.getSalePrice()))
                .andExpect(jsonPath("$.weight").value(request.getWeight()))
                .andExpect(jsonPath("$.imageUrl").value(request.getImageUrl()));
    }

    @Test
    public void deleteProduct() throws Exception {
        Product product = new Product(1L, "Name", "Descrption", 10.0, 5.0, 5.0, "https://images.pexels.com/photos/1640777/pexels-photo-1640777.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940");

        when(productService.deleteProduct(anyLong())).thenReturn(product);

        mockMvc.perform(delete("/api/products/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(product.getName()))
                .andExpect(jsonPath("$.price").value(product.getPrice()))
                .andExpect(jsonPath("$.description").value(product.getDescription()))
                .andExpect(jsonPath("$.salePrice").value(product.getSalePrice()))
                .andExpect(jsonPath("$.weight").value(product.getWeight()))
                .andExpect(jsonPath("$.imageUrl").value(product.getImageUrl()));
    }

    @Test
    public void getProducts() throws Exception {
        Product product = new Product(1L, "Name", "Descrption", 10.0, 5.0, 5.0, "https://images.pexels.com/photos/1640777/pexels-photo-1640777.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940");

        when(productService.getAllProducts()).thenReturn(List.of(product));

        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(product.getName()))
                .andExpect(jsonPath("$[0].price").value(product.getPrice()))
                .andExpect(jsonPath("$[0].description").value(product.getDescription()))
                .andExpect(jsonPath("$[0].salePrice").value(product.getSalePrice()))
                .andExpect(jsonPath("$[0].weight").value(product.getWeight()))
                .andExpect(jsonPath("$[0].imageUrl").value(product.getImageUrl()));
    }

    @Test
    public void getProductsByName() throws Exception {
        Product product = new Product(1L, "Name", "Descrption", 10.0, 5.0, 5.0, "https://images.pexels.com/photos/1640777/pexels-photo-1640777.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940");

        when(productService.getProductByName(anyString())).thenReturn(product);

        mockMvc.perform(get("/api/products/name/{name}", "Name"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(product.getName()))
                .andExpect(jsonPath("$.price").value(product.getPrice()))
                .andExpect(jsonPath("$.description").value(product.getDescription()))
                .andExpect(jsonPath("$.salePrice").value(product.getSalePrice()))
                .andExpect(jsonPath("$.weight").value(product.getWeight()))
                .andExpect(jsonPath("$.imageUrl").value(product.getImageUrl()));
    }

    @Test
    public void getProduct() throws Exception {
        Product product = new Product(1L, "Name", "Descrption", 10.0, 5.0, 5.0, "https://images.pexels.com/photos/1640777/pexels-photo-1640777.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940");

        when(productService.getProduct(anyLong())).thenReturn(product);

        mockMvc.perform(get("/api/products/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(product.getName()))
                .andExpect(jsonPath("$.price").value(product.getPrice()))
                .andExpect(jsonPath("$.description").value(product.getDescription()))
                .andExpect(jsonPath("$.salePrice").value(product.getSalePrice()))
                .andExpect(jsonPath("$.weight").value(product.getWeight()))
                .andExpect(jsonPath("$.imageUrl").value(product.getImageUrl()));
    }

    @Test
    public void updateProduct() throws Exception {
        ProductRequest request = new ProductRequest("Name", "Descrption", 10.0, 5.0, 5.0, "https://images.pexels.com/photos/1640777/pexels-photo-1640777.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940");

        when(productService.updateProduct(anyLong(), any())).thenReturn(new Product(1L, "Name", "Descrption", 10.0, 5.0, 5.0, "https://images.pexels.com/photos/1640777/pexels-photo-1640777.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940"));

        mockMvc.perform(put("/api/products/{id}", 1L)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status() .isOk())
                .andExpect(jsonPath("$.name").value(request.getName()))
                .andExpect(jsonPath("$.price").value(request.getPrice()))
                .andExpect(jsonPath("$.description").value(request.getDescription()))
                .andExpect(jsonPath("$.salePrice").value(request.getSalePrice()))
                .andExpect(jsonPath("$.weight").value(request.getWeight()))
                .andExpect(jsonPath("$.imageUrl").value(request.getImageUrl()));
    }
}
