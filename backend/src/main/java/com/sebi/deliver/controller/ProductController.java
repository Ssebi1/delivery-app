package com.sebi.deliver.controller;

import com.sebi.deliver.model.ApiError;
import com.sebi.deliver.model.Product;
import com.sebi.deliver.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/products")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @Operation(summary = "Create product", description = "Create a product and return it",
                tags = {"Product"},
                responses = {
                        @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Product.class))),
                        @ApiResponse(responseCode = "400", description = "Generic exception", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))),
                        @ApiResponse(responseCode = "400", description = "Missing fields", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))),
                        @ApiResponse(responseCode = "400", description = "Sale price is greater than price", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)))
                })
    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        return productService.createProduct(product);
    }

    @Operation(summary = "Update product", description = "Update a product by id and return it",
                tags = {"Product"},
                responses = {
                        @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Product.class))),
                        @ApiResponse(responseCode = "400", description = "Generic exception", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))),
                        @ApiResponse(responseCode = "400", description = "Missing fields", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))),
                        @ApiResponse(responseCode = "400", description = "Sale price is greater than price", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)))
                })
    @PutMapping("{id}")
    public Product updateProduct(@PathVariable Long id, @RequestBody Product product) {
        return productService.updateProduct(id, product);
    }

    @Operation(summary = "Get product", description = "Get a product by id and return it",
                tags = {"Product"},
                responses = {
                        @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Product.class))),
                        @ApiResponse(responseCode = "400", description = "Product not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))),
                        @ApiResponse(responseCode = "400", description = "User not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)))
                })
    @GetMapping("{id}")
    public Product getProduct(@PathVariable Long id) {
        return productService.getProduct(id);
    }

    @Operation(summary = "Delete product", description = "Delete a product by id and return it",
                tags = {"Product"},
                responses = {
                        @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Product.class))),
                        @ApiResponse(responseCode = "400", description = "Product not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)))
                })
    @DeleteMapping("{id}")
    public Product deleteProduct(@PathVariable Long id) {
        return productService.deleteProduct(id);
    }

    @Operation(summary = "Get all products", description = "Get all products",
                tags = {"Product"},
                responses = {
                        @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = "application/json", array = @io.swagger.v3.oas.annotations.media.ArraySchema(schema = @Schema(implementation = Product.class)))),
                        @ApiResponse(responseCode = "400", description = "Generic exception", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)))
                })
    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @Operation(summary = "Get product by name", description = "Get a product by name and return it",
                tags = {"Product"},
                responses = {
                        @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Product.class))),
                        @ApiResponse(responseCode = "400", description = "Products not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)))
                })
    @GetMapping("name/{name}")
    public Product getProductByName(@PathVariable String name) {
        return productService.getProductByName(name);
    }
}
