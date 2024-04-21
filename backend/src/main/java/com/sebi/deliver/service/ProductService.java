package com.sebi.deliver.service;

import com.sebi.deliver.exception.GenericException;
import com.sebi.deliver.exception.MissingFieldsException;
import com.sebi.deliver.exception.product.SalePriceBiggerThanPriceException;
import com.sebi.deliver.model.Product;
import com.sebi.deliver.model.User;
import com.sebi.deliver.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product createProduct(Product product) {
        handle_create_update_errors(product);
        productRepository.save(product);
        return product;
    }

    public Product updateProduct(Long id, Product product) {
        Optional<Product> productFromDb = productRepository.findById(id);
        if (productFromDb.isEmpty()) { throw new GenericException(); }
        product.setId(id);
        productRepository.save(product);
        return product;
    }

    public Product getProduct(Long id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isEmpty()) { throw new GenericException(); }
        return product.get();
    }

    public Product deleteProduct(Long id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isEmpty()) {
            throw new GenericException();
        }
        productRepository.deleteById(id);
        return product.get();
    }

    public Product getProductByName(String name) {
        Optional<Product> product = productRepository.findByName(name);
        if (product.isEmpty()) {
            throw new GenericException();
        }
        return product.get();
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public void handle_create_update_errors(Product product) {
        if (product.getName().isEmpty() || product.getPrice() == 0) { throw new MissingFieldsException(); }
        if (product.getSalePrice() != null && product.getSalePrice() > product.getPrice()) { throw new SalePriceBiggerThanPriceException(); }
    }
}
