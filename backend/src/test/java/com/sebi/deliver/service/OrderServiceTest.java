package com.sebi.deliver.service;

import com.sebi.deliver.exception.GenericException;
import com.sebi.deliver.exception.MissingFieldsException;
import com.sebi.deliver.model.*;
import com.sebi.deliver.repository.CartRepository;
import com.sebi.deliver.repository.OrderRepository;
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
public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CartRepository cartRepository;

    @InjectMocks
    private OrderService orderService;

    @Test
    @Description("Create order")
    void createOrder_isSuccess() {
        // Arrange
        Order order = new Order();
        order.setPrice(10.0);
        order.setProducts("1xproduct1, 2xproduct2");
        User user = new User();
        user.setId(1L);
        user.setName("Username");
        user.setPassword("Password");
        user.setAddress("Address");
        user.setPhone("Phone");
        user.setEmail("Email");
        user.setCity("City");
        order.setUser(user);
        CartItem cart = new CartItem();
        cart.setId(1L);
        cart.setProduct(new Product());
        cart.setQuantity(1);
        when(cartRepository.findByUserId(user.getId())).thenReturn(List.of(cart));
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        // Act
        Order createdOrder = orderService.addOrder(user.getId(), order);

        // Assert
        assertEquals(order.getPrice(), createdOrder.getPrice());
        assertEquals(order.getProducts(), createdOrder.getProducts());
        assertEquals(order.getUser(), createdOrder.getUser());
    }

    @ParameterizedTest
    @Description("Create order with missing address")
    @ValueSource(strings = {"address", "city", "phone"})
    void createOrder_missingAddress_throwsException(String missingField) {
        // Arrange
        Order order = new Order();
        order.setPrice(10.0);
        order.setProducts("1xproduct1, 2xproduct2");
        User user = new User();
        user.setId(1L);
        user.setName("Username");
        user.setPassword("Password");
        user.setAddress("Address");
        user.setPhone("Phone");
        user.setEmail("Email");
        user.setCity("City");
        if (Objects.equals(missingField, "address")) { user.setAddress(""); }
        if (Objects.equals(missingField, "city")) { user.setCity(""); }
        if (Objects.equals(missingField, "phone")) { user.setPhone(""); }
        order.setUser(user);
        CartItem cart = new CartItem();
        cart.setId(1L);
        cart.setProduct(new Product());
        cart.setQuantity(1);
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        // act
        MissingFieldsException exception = assertThrows(MissingFieldsException.class,
                () -> orderService.addOrder(user.getId(), order));

        // assert
        assertNotNull(exception);
        assertEquals("Missing fields", exception.getMessage());
    }

    @Test
    @Description("Create order with missing address")
    void createOrder_missingUser_throwsException() {
        // Arrange
        Order order = new Order();
        order.setPrice(10.0);
        order.setProducts("1xproduct1, 2xproduct2");
        CartItem cart = new CartItem();
        cart.setId(1L);
        cart.setProduct(new Product());
        cart.setQuantity(1);

        // act
        GenericException exception = assertThrows(GenericException.class,
                () -> orderService.addOrder(1L, order));

        // assert
        assertNotNull(exception);
        assertEquals("Could not process request. Please try again later", exception.getMessage());
    }

    @Test
    @Description("Test for getting all orders")
    void getAllOrders_isSuccess() {
        // arrange
        Order order = new Order();
        order.setPrice(10.0);
        order.setProducts("1xproduct1, 2xproduct2");
        User user = new User();
        user.setId(1L);
        user.setName("Username");
        user.setPassword("Password");
        user.setAddress("Address");
        user.setPhone("Phone");
        user.setEmail("Email");
        user.setCity("City");
        order.setUser(user);
        when(orderRepository.findAll()).thenReturn(List.of(order));

        // act
        List<Order> orders = orderService.getAllOrders();

        // assert
        assertEquals(1, orders.size());
        assertEquals(order, orders.get(0));
    }

    @Test
    @Description("Test for getting user orders")
    void getUserOrders_isSuccess() {
        // arrange
        Order order = new Order();
        order.setPrice(10.0);
        order.setProducts("1xproduct1, 2xproduct2");
        User user = new User();
        user.setId(1L);
        user.setName("Username");
        user.setPassword("Password");
        user.setAddress("Address");
        user.setPhone("Phone");
        user.setEmail("Email");
        user.setCity("City");
        order.setUser(user);
        when(orderRepository.findByUserId(user.getId())).thenReturn(List.of(order));

        // act
        List<Order> orders = orderService.getUserOrders(user.getId());

        // assert
        assertEquals(1, orders.size());
        assertEquals(order, orders.get(0));
    }
}
