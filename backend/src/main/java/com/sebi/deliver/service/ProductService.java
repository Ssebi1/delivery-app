package com.sebi.deliver.service;

import com.sebi.deliver.exception.GenericException;
import com.sebi.deliver.exception.MissingFieldsException;
import com.sebi.deliver.exception.product.SalePriceBiggerThanPriceException;
import com.sebi.deliver.model.Product;
import com.sebi.deliver.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    private final int items_per_page = 4;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product createProduct(Product product) {
        logger.info("Creating product with name: {}", product.getName());
        handle_create_update_errors(product);
        productRepository.save(product);
        logger.info("Product with name: {} created successfully.", product.getName());
        return product;
    }

    public Product updateProduct(Long id, Product product) {
        logger.info("Updating product with id: {}", id);
        Optional<Product> productFromDb = productRepository.findById(id);
        if (productFromDb.isEmpty()) {
            logger.error("Product with id: " + id + " not found.");
            throw new GenericException();
        }
        product.setId(id);
        productRepository.save(product);
        logger.info("Product with id: {} updated successfully.", id);
        return product;
    }

    public Product getProduct(Long id) {
        logger.info("Getting product with id: {}", id);
        Optional<Product> product = productRepository.findById(id);
        if (product.isEmpty()) {
            logger.error("Product with id: " + id + " not found.");
            throw new GenericException();
        }
        return product.get();
    }

    public Product deleteProduct(Long id) {
        logger.info("Deleting product with id: {}", id);
        Optional<Product> product = productRepository.findById(id);
        if (product.isEmpty()) {
            logger.error("Product with id: " + id + " not found.");
            throw new GenericException();
        }
        productRepository.deleteById(id);
        logger.info("Product with id: {} deleted successfully.", id);
        return product.get();
    }

    public Product getProductByName(String name) {
        logger.info("Getting product with name: {}", name);
        Optional<Product> product = productRepository.findByName(name);
        if (product.isEmpty()) {
            logger.error("Product with name: " + name + " not found.");
            throw new GenericException();
        }
        return product.get();
    }

    public Page<Product> getAllProducts(Integer page, String sortBy, String sortOrder) {
        logger.info("Getting all products from page {}.", page);
        Sort.Direction sortDirection = Sort.Direction.ASC;
        if (sortOrder.equals("desc")) {
            sortDirection = Sort.Direction.DESC;
        }
        String sortProperty = "name";
        if (sortBy.equals("price") || sortBy.equals("weight")){
            sortProperty = sortBy;
        }
        return productRepository.findAll(PageRequest.of(page, items_per_page, Sort.by(sortDirection, sortProperty)));
    }

    public void handle_create_update_errors(Product product) {
        if (product.getName().isEmpty() || product.getPrice() == 0) {
            logger.error("Missing fields.");
            throw new MissingFieldsException();
        }
        if (product.getSalePrice() != null && product.getSalePrice() > product.getPrice()) {
            logger.error("Sale price bigger than price.");
            throw new SalePriceBiggerThanPriceException();
        }
    }
}
