package com.sebi.deliver.controller;

import com.sebi.deliver.exception.user.EmailAlreadyExistsException;
import com.sebi.deliver.model.ApiError;
import com.sebi.deliver.model.User;
import com.sebi.deliver.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Register user", description = "Register user and return it",
                tags = {"User"},
                responses = {
                        @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
                        @ApiResponse(responseCode = "400", description = "Generic exception", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))),
                        @ApiResponse(responseCode = "400", description = "Missing fields", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))),
                        @ApiResponse(responseCode = "400", description = "Email already exists", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)))
                })
    @PostMapping("register")
    public User registerUser(@RequestBody User user) {
        return userService.register(user);
    }

    @Operation(summary = "Login user", description = "Login user and return it",
                tags = {"User"},
                responses = {
                        @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
                        @ApiResponse(responseCode = "400", description = "Generic exception", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))),
                        @ApiResponse(responseCode = "400", description = "Missing fields", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))),
                        @ApiResponse(responseCode = "400", description = "Wrong credentials", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)))
                })
    @PostMapping("login")
    public User loginUser(@RequestBody User user) {
        return userService.login(user);
    }

    @Operation(summary = "Delete user", description = "Delete user by id and return it",
                tags = {"User"},
                responses = {
                        @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
                        @ApiResponse(responseCode = "400", description = "Generic exception", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))),
                        @ApiResponse(responseCode = "400", description = "User not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)))
                })
    @DeleteMapping("{id}")
    public User deleteUser(@PathVariable Long id) {
        return userService.deleteUser(id);
    }

    @Operation(summary = "Get user", description = "Get user by id and return it",
                tags = {"User"},
                responses = {
                        @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
                        @ApiResponse(responseCode = "400", description = "Generic exception", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))),
                        @ApiResponse(responseCode = "400", description = "User not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)))
                })
    @GetMapping("{id}")
    public User getUser(@PathVariable Long id) {
        return userService.getUser(id);
    }

    @Operation(summary = "Update user", description = "Update user by id and return it",
                tags = {"User"},
                responses = {
                        @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
                        @ApiResponse(responseCode = "400", description = "Generic exception", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))),
                        @ApiResponse(responseCode = "400", description = "User not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)))
                })
    @PutMapping("{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User user) {
        return userService.updateUser(id, user);
    }
}
