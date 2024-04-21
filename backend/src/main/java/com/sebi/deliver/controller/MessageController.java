package com.sebi.deliver.controller;

import com.sebi.deliver.model.ApiError;
import com.sebi.deliver.model.Message;
import com.sebi.deliver.service.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/messages")
public class MessageController {

    private final MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @Operation(summary = "Get all messages", description = "Get all messages",
            tags = {"Message"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Message.class)))),
                    @ApiResponse(responseCode = "400", description = "Generic exception", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)))
            })
    @GetMapping
    public List<Message> getAllMessages() {
        return messageService.getAllMessages();
    }

    @Operation(summary = "Get user messages", description = "Get user messages by id",
            tags = {"Message"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Message.class)))),
                    @ApiResponse(responseCode = "400", description = "Generic exception", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))),
                    @ApiResponse(responseCode = "400", description = "User not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)))
            })
    @GetMapping("{id}")
    public List<Message> getUserMessages(@PathVariable Long id) {
        return messageService.getUserMessages(id);
    }

    @Operation(summary = "Add message", description = "Add message to user by id",
            tags = {"Message"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Message.class))),
                    @ApiResponse(responseCode = "400", description = "Generic exception", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))),
                    @ApiResponse(responseCode = "400", description = "User not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))),
                    @ApiResponse(responseCode = "400", description = "Missing fields", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))),
            })
    @PostMapping("{id}")
    public Message addMessage(@PathVariable Long id, @RequestBody Message message) {
        return messageService.addMessage(id, message);
    }
}
