package com.sebi.deliver.service;

import com.sebi.deliver.exception.GenericException;
import com.sebi.deliver.model.CartItem;
import com.sebi.deliver.model.Product;
import com.sebi.deliver.model.User;
import com.sebi.deliver.repository.CartRepository;
import com.sebi.deliver.repository.ProductRepository;
import com.sebi.deliver.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

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
        Optional<CartItem> cartItem = cartRepository.findByUserIdAndProductId(id, productId);
        if (cartItem.isEmpty()) {
            throw new GenericException();
        }
        cartRepository.deleteById(cartItem.get().getId());
        return cartItem.get();
    }

    public CartItem addProductToCart(Long id, Long productId) {
        CartItem cartItem = new CartItem();
        Optional<User> user = userRepository.findById(id);
        Optional<Product> product = productRepository.findById(productId);
        if (user.isEmpty() || product.isEmpty()) {
            throw new GenericException();
        }
        cartItem.setUser(user.get());
        cartItem.setProduct(product.get());

        Optional<CartItem> cartItemFromDb = cartRepository.findByUserIdAndProductId(id, productId);
        if (cartItemFromDb.isPresent()) {
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
        for (CartItem cartItem : cart) {
            cartRepository.deleteById(cartItem.getId());
        }
    }

}
