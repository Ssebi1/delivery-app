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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    public OrderService(OrderRepository orderRepository, UserRepository userRepository, CartRepository cartRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
    }

    public List<Order> getUserOrders(Long id) {
        logger.info("Getting orders for user with id: {}", id);
        return orderRepository.findByUserId(id);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order addOrder(Long id, Order order) {
        logger.info("Adding order for user with id: {}", id);
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            logger.error("User with id: {} not found.", id);
            throw new GenericException();
        }
        if (user.get().getCity().isEmpty() || user.get().getAddress().isEmpty() || user.get().getPhone().isEmpty()) {
            logger.error("Missing fields from address.");
            throw new MissingFieldsException();
        }
        order.setUser(user.get());
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        order.setDate(formatter.format(new Date()));
        orderRepository.save(order);
        logger.info("Order added successfully.");

        // clear cart
        List<CartItem> cartItems = cartRepository.findByUserId(id);
        logger.info("Clearing {} items from cart for user with id: {}", cartItems.size() ,id);
        for (CartItem cartItem : cartItems) {
            cartRepository.deleteById(cartItem.getId());
        }
        return order;
    }

}
