package com.sebi.deliver.controller;

import com.sebi.deliver.model.ApiError;
import com.sebi.deliver.model.Order;
import com.sebi.deliver.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @Operation(summary = "Get all orders", description = "Get all orders",
            tags = {"Order"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Order.class)))),
                    @ApiResponse(responseCode = "400", description = "Generic exception", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)))
            })
    @GetMapping
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    @Operation(summary = "Get user orders", description = "Get user orders by id",
            tags = {"Order"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Order.class)))),
                    @ApiResponse(responseCode = "400", description = "Generic exception", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))),
                    @ApiResponse(responseCode = "400", description = "User not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)))
            })
    @GetMapping("{id}")
    public List<Order> getUserOrders(@PathVariable Long id) {
        return orderService.getUserOrders(id);
    }

    @Operation(summary = "Add order", description = "Add order to user by id",
            tags = {"Order"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Order.class))),
                    @ApiResponse(responseCode = "400", description = "Generic exception", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))),
                    @ApiResponse(responseCode = "400", description = "User not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))),
                    @ApiResponse(responseCode = "400", description = "Missing fields", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))),
            })
    @PostMapping("{id}")
    public Order addOrder(@PathVariable Long id, @RequestBody Order order) {
        return orderService.addOrder(id, order);
    }
}
