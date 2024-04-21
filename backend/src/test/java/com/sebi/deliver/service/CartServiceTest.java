package com.sebi.deliver.service;

import com.sebi.deliver.exception.GenericException;
import com.sebi.deliver.exception.MissingFieldsException;
import com.sebi.deliver.model.CartItem;
import com.sebi.deliver.model.Order;
import com.sebi.deliver.model.Product;
import com.sebi.deliver.model.User;
import com.sebi.deliver.repository.CartRepository;
import com.sebi.deliver.repository.OrderRepository;
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
public class CartServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CartRepository cartRepository;

    @InjectMocks
    private CartService cartService;

    @Test
    @Description("Add product to cart")
    void addProductToCart_isSuccess() {
        // Arrange
        CartItem cartItem = new CartItem();
        cartItem.setId(1L);
        Product product = new Product(1L, "Product", "Description", 5.0, 4.0, 5.0, "https://images.pexels.com/photos/1640777/pexels-photo-1640777.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940");
        cartItem.setProduct(product);
        cartItem.setQuantity(1);
        User user = new User();
        user.setId(1L);
        user.setName("Username");
        user.setPassword("Password");
        user.setAddress("Address");
        user.setPhone("Phone");
        user.setEmail("Email");
        user.setCity("City");
        cartItem.setUser(user);
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));
        when(cartRepository.findByUserIdAndProductId(user.getId(), product.getId())).thenReturn(Optional.empty());

        // Act
        CartItem createdCartItem = cartService.addProductToCart(user.getId(), cartItem.getProduct().getId());

        // Assert
        assertNotNull(createdCartItem);
        assertEquals(cartItem.getProduct(), createdCartItem.getProduct());
        assertEquals(cartItem.getQuantity(), createdCartItem.getQuantity());
        assertEquals(cartItem.getUser(), createdCartItem.getUser());
    }

    @Test
    @Description("Add product to missing user")
    void addProductToCart_missingUser_throwsException() {
        // Arrange
        CartItem cartItem = new CartItem();
        cartItem.setId(1L);
        Product product = new Product(1L, "Product", "Description", 5.0, 4.0, 5.0, "https://images.pexels.com/photos/1640777/pexels-photo-1640777.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940");
        cartItem.setProduct(product);
        cartItem.setQuantity(1);
        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));

        // Act
        GenericException exception = assertThrows(GenericException.class,
                () -> cartService.addProductToCart(1L, cartItem.getProduct().getId()));

        // Assert
        assertNotNull(exception);
        assertEquals("Could not process request. Please try again later", exception.getMessage());
    }

    @Test
    @Description("Test for getting cart")
    void getCart_isSuccess() {
        // arrange
        CartItem cartItem = new CartItem();
        cartItem.setId(1L);
        Product product = new Product(1L, "Product", "Description", 5.0, 4.0, 5.0, "https://images.pexels.com/photos/1640777/pexels-photo-1640777.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940");
        cartItem.setProduct(product);
        cartItem.setQuantity(1);
        User user = new User(1L, "Username", "Password", "Address", "Phone", "Email", "City", "notes", "token", false);
        cartItem.setUser(user);
        when(cartRepository.findByUserId(user.getId())).thenReturn(List.of(cartItem));

        // act
        List<CartItem> cart = cartService.getCart(user.getId());

        // assert
        assertEquals(1, cart.size());
        assertEquals(cartItem, cart.get(0));
    }

    @Test
    @Description("Test for deleting product from cart cart")
    void deleteProductFromCart_isSuccess() {
        // arrange
        CartItem cartItem = new CartItem();
        cartItem.setId(1L);
        Product product = new Product(1L, "Product", "Description", 5.0, 4.0, 5.0, "https://images.pexels.com/photos/1640777/pexels-photo-1640777.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940");
        cartItem.setProduct(product);
        cartItem.setQuantity(1);
        User user = new User(1L, "Username", "Password", "Address", "Phone", "Email", "City", "notes", "token", false);
        cartItem.setUser(user);
        when(cartRepository.findByUserIdAndProductId(user.getId(), product.getId())).thenReturn(Optional.of(cartItem));

        // act
        CartItem deletedCartItem = cartService.deleteProductFromCart(user.getId(), product.getId());

        // assert
        assertEquals(cartItem, deletedCartItem);
    }

    @Test
    @Description("Test for deleting cart")
    void deleteCart_isSuccess() {
        // arrange
        CartItem cartItem = new CartItem();
        cartItem.setId(1L);
        Product product = new Product(1L, "Product", "Description", 5.0, 4.0, 5.0, "https://images.pexels.com/photos/1640777/pexels-photo-1640777.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940");
        cartItem.setProduct(product);
        cartItem.setQuantity(1);
        User user = new User(1L, "Username", "Password", "Address", "Phone", "Email", "City", "notes", "token", false);
        cartItem.setUser(user);
        when(cartRepository.findByUserId(user.getId())).thenReturn(List.of(cartItem));

        // act
        cartService.deleteCart(user.getId());
    }
}
