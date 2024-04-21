package com.sebi.deliver.controller;

import com.sebi.deliver.model.ApiError;
import com.sebi.deliver.model.CartItem;
import com.sebi.deliver.service.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/cart")
public class CartController {
    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @Operation(
            summary = "Get cart items",
            description = "Get cart items by id",
            tags = {"Cart"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = CartItem.class)))),
                    @ApiResponse(responseCode = "400", description = "Cart not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))),
                    @ApiResponse(responseCode = "400", description = "User not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)))
            }
    )
    @GetMapping("{id}")
    public List<CartItem> getCart(@PathVariable Long id) {
        return cartService.getCart(id);
    }

    @Operation(
            summary = "Delete product from cart",
            description = "Delete product from cart by id and product_id",
            tags = {"Cart"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully deleted", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CartItem.class))),
                    @ApiResponse(responseCode = "400", description = "Cart not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))),
                    @ApiResponse(responseCode = "400", description = "Product not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))),
                    @ApiResponse(responseCode = "400", description = "User not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)))
            }
    )
    @DeleteMapping("{id}/{product_id}")
    public CartItem deleteProductFromCart(@PathVariable Long id, @PathVariable Long product_id) {
        return cartService.deleteProductFromCart(id, product_id);
    }

    @Operation(
            summary = "Add product to cart",
            description = "Add product to cart by id and product_id",
            tags = {"Cart"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully added", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CartItem.class))),
                    @ApiResponse(responseCode = "400", description = "Cart not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))),
                    @ApiResponse(responseCode = "400", description = "Product not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))),
                    @ApiResponse(responseCode = "400", description = "User not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)))
            }
    )
    @PostMapping("{id}/{product_id}")
    public CartItem addProductToCart(@PathVariable Long id, @PathVariable Long product_id) {
        return cartService.addProductToCart(id, product_id);
    }

    @Operation(
            summary = "Delete cart",
            description = "Delete cart by user id",
            tags = {"Cart"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully deleted", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CartItem.class))),
                    @ApiResponse(responseCode = "400", description = "Cart not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))),
                    @ApiResponse(responseCode = "400", description = "User not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)))
            }
    )
    @DeleteMapping("{id}")
    public void deleteCart(@PathVariable Long id) {
        cartService.deleteCart(id);
    }
}
