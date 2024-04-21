package com.sebi.deliver.service;

import com.sebi.deliver.exception.GenericException;
import com.sebi.deliver.exception.MissingFieldsException;
import com.sebi.deliver.exception.product.SalePriceBiggerThanPriceException;
import com.sebi.deliver.model.Product;
import com.sebi.deliver.repository.ProductRepository;
import jdk.jfr.Description;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    @Description("Create product")
    void createProduct_isSuccess() {
        // Arrange
        Product product = new Product();
        product.setName("Product");
        product.setPrice(10.0);
        product.setSalePrice(5.0);
        product.setDescription("Description");
        product.setWeight(5.0);

        // Act
        Product createdProduct = productService.createProduct(product);

        // Assert
        assertEquals(product.getName(), createdProduct.getName());
        assertEquals(product.getPrice(), createdProduct.getPrice());
        assertEquals(product.getDescription(), createdProduct.getDescription());
        assertEquals(product.getSalePrice(), createdProduct.getSalePrice());
        assertEquals(product.getWeight(), createdProduct.getWeight());
        assertEquals(product.getImageUrl(), "https://images.pexels.com/photos/1640777/pexels-photo-1640777.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940");
    }

    @Description("Create product with missing fields")
    @ParameterizedTest
    @ValueSource(strings = {"name", "price"})
    void createProduct_missingFields_throwsError(String missingField) {
        // Arrange
        Product product = new Product();
        product.setName("Test");
        product.setPrice(10.0);
        if (missingField.equals("name")) { product.setName(""); }
        if (missingField.equals("price")) { product.setPrice(0.0); }
        product.setSalePrice(5.0);
        product.setDescription("Description");
        product.setWeight(5.0);

        // act
        MissingFieldsException exception = assertThrows(MissingFieldsException.class,
                () -> productService.createProduct(product));

        // assert
        assertNotNull(exception);
        assertEquals("Missing fields", exception.getMessage());
    }

    @Description("Create product with sale price bigger than price")
    @Test
    void createProduct_salePriceBigger_throwsError() {
        // Arrange
        Product product = new Product();
        product.setName("Test");
        product.setSalePrice(5.0);
        product.setPrice(3.0);
        product.setDescription("Description");
        product.setWeight(5.0);

        // act
        SalePriceBiggerThanPriceException exception = assertThrows(SalePriceBiggerThanPriceException.class,
                () -> productService.createProduct(product));

        // assert
        assertNotNull(exception);
        assertEquals("Sale price bigger than price", exception.getMessage());
    }

    @Test
    @Description("Update product")
    void updateProduct_isSuccess() {
        // Arrange
        Product product = new Product();
        product.setId(1L);
        product.setName("Product");
        product.setPrice(10.0);
        product.setSalePrice(5.0);
        product.setDescription("Description");
        product.setWeight(5.0);
        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));

        // Act
        Product updatedProduct = productService.updateProduct(1L, product);

        // Assert
        assertEquals(product.getId(), updatedProduct.getId());
        assertEquals(product.getName(), updatedProduct.getName());
        assertEquals(product.getPrice(), updatedProduct.getPrice());
        assertEquals(product.getDescription(), updatedProduct.getDescription());
        assertEquals(product.getSalePrice(), updatedProduct.getSalePrice());
        assertEquals(product.getWeight(), updatedProduct.getWeight());
        assertEquals(product.getImageUrl(), "https://images.pexels.com/photos/1640777/pexels-photo-1640777.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940");
    }

    @Test
    @Description("Update product that does not exist")
    void updateProduct_notExisting_throwsError() {
        // Arrange
        Product product = new Product();
        product.setId(1L);
        product.setName("Product");
        product.setPrice(10.0);
        product.setSalePrice(5.0);
        product.setDescription("Description");
        product.setWeight(5.0);

        // act
        GenericException exception = assertThrows(GenericException.class,
                () -> productService.updateProduct(1L, product));

        // assert
        assertNotNull(exception);
        assertEquals("Could not process request. Please try again later", exception.getMessage());
    }

    @Test
    @Description("Test for getting a product")
    void getProduct_isSuccess() {
        // arrange
        Product product = new Product();
        product.setId(1L);
        product.setName("Product");
        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));

        // act
        Product getProduct = productService.getProduct(product.getId());

        // assert
        assertNotNull(product);
        assertEquals(product.getName(), getProduct.getName());
        assertEquals(product.getPrice(), getProduct.getPrice());
    }

    @Test
    @Description("Test for getting a product that does not exist")
    void getProduct_notExisting_throwsException() {
        // arrange
        Product product = new Product();
        product.setId(1L);
        product.setName("Product");

        // act
        GenericException exception = assertThrows(GenericException.class,
                () -> productService.getProduct(product.getId()));

        // assert
        assertNotNull(exception);
        assertEquals("Could not process request. Please try again later", exception.getMessage());
    }

    @Test
    @Description("Test for deleting a product")
    void deleteProduct_isSuccess() {
        // arrange
        Product product = new Product();
        product.setId(1L);
        product.setName("Product");
        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));

        // act
        Product getProduct = productService.deleteProduct(product.getId());

        // assert
        assertNotNull(product);
        assertEquals(product.getName(), getProduct.getName());
        assertEquals(product.getPrice(), getProduct.getPrice());
    }

    @Test
    @Description("Test for deleting a product that does not exist")
    void deleteProduct_notExisting_throwsException() {
        // arrange
        Product product = new Product();
        product.setId(1L);
        product.setName("Product");

        // act
        GenericException exception = assertThrows(GenericException.class,
                () -> productService.deleteProduct(product.getId()));

        // assert
        assertNotNull(exception);
        assertEquals("Could not process request. Please try again later", exception.getMessage());
    }

    @Test
    @Description("Test for getting a product by name")
    void getProductByName_isSuccess() {
        // arrange
        Product product = new Product();
        product.setId(1L);
        product.setName("Product");
        when(productRepository.findByName(product.getName())).thenReturn(Optional.of(product));

        // act
        Product getProduct = productService.getProductByName(product.getName());

        // assert
        assertNotNull(product);
        assertEquals(product.getName(), getProduct.getName());
        assertEquals(product.getPrice(), getProduct.getPrice());
    }

    @Test
    @Description("Test for getting a product by name that does not exist")
    void getProductByName_notExisting_throwsException() {
        // arrange
        Product product = new Product();
        product.setId(1L);
        product.setName("Product");

        // act
        GenericException exception = assertThrows(GenericException.class,
                () -> productService.getProductByName(product.getName()));

        // assert
        assertNotNull(exception);
        assertEquals("Could not process request. Please try again later", exception.getMessage());
    }

    @Test
    @Description("Test for getting all products")
    void getAllProducts_isSuccess() {
        // arrange
        Product product = new Product();
        product.setId(1L);
        product.setName("Product");
        when(productRepository.findAll()).thenReturn(List.of(product));

        // act
        List<Product> products = productService.getAllProducts();

        // assert
        assertNotNull(products);
        assertEquals(1, products.size());
        assertEquals(product.getName(), products.get(0).getName());
        assertEquals(product.getPrice(), products.get(0).getPrice());
    }
}
