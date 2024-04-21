package com.sebi.deliver.service;

import com.sebi.deliver.exception.GenericException;
import com.sebi.deliver.model.CartItem;
import com.sebi.deliver.model.Product;
import com.sebi.deliver.model.User;
import com.sebi.deliver.repository.CartRepository;
import com.sebi.deliver.repository.ProductRepository;
import com.sebi.deliver.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private static final Logger logger = LoggerFactory.getLogger(CartService.class);

    @Autowired
    public CartService(CartRepository cartRepository, UserRepository userRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    public List<CartItem> getCart(Long id) {
        return cartRepository.findByUserId(id);
    }

    public CartItem deleteProductFromCart(Long id, Long productId) {
        logger.info("Deleting product with id: {} from cart.", productId);
        Optional<CartItem> cartItem = cartRepository.findByUserIdAndProductId(id, productId);
        if (cartItem.isEmpty()) {
            logger.error("Product with id: " + productId + " not found in cart.");
            throw new GenericException();
        }
        cartRepository.deleteById(cartItem.get().getId());
        return cartItem.get();
    }

    public CartItem addProductToCart(Long id, Long productId) {
        logger.info("Adding product with id: {} to cart.", productId);
        CartItem cartItem = new CartItem();
        Optional<User> user = userRepository.findById(id);
        Optional<Product> product = productRepository.findById(productId);
        if (user.isEmpty() || product.isEmpty()) {
            logger.error("User with id: " + id + " or product with id: " + productId + " not found.");
            throw new GenericException();
        }
        cartItem.setUser(user.get());
        cartItem.setProduct(product.get());

        Optional<CartItem> cartItemFromDb = cartRepository.findByUserIdAndProductId(id, productId);
        if (cartItemFromDb.isPresent()) {
            logger.info("Product with id: {} already in cart. Increasing quantity.", productId);
            cartItemFromDb.get().setQuantity(cartItemFromDb.get().getQuantity() + 1);
            cartRepository.save(cartItemFromDb.get());
            return cartItemFromDb.get();
        }
        cartItem.setQuantity(1);
        cartRepository.save(cartItem);
        return cartItem;
    }

    public void deleteCart(Long id) {
        List<CartItem> cart = cartRepository.findByUserId(id);
        logger.info("Clearing {} items from cart for user with id: {}", cart.size(), id);
        for (CartItem cartItem : cart) {
            cartRepository.deleteById(cartItem.getId());
        }
    }

}
