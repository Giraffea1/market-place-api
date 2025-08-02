package com.jhu.enterprise.market_place_api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jhu.enterprise.market_place_api.dto.MessageRequest;
import com.jhu.enterprise.market_place_api.dto.MessageResponse;
import com.jhu.enterprise.market_place_api.services.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/messages")
@Tag(name = "Messaging", description = "Message management APIs for buyer-seller communication")
@CrossOrigin(origins = "*")
public class MessageController {

    @Autowired
    MessageService messageService;

    @Operation(summary = "Send message", description = "Sends a message to another user regarding a post")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Message sent successfully", content = @Content(schema = @Schema(implementation = MessageResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid message data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - requires authentication")
    })
    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping
    public ResponseEntity<MessageResponse> sendMessage(@Valid @RequestBody MessageRequest messageRequest,
            Authentication authentication) {
        return ResponseEntity.ok(messageService.sendMessage(messageRequest, authentication));
    }

    @Operation(summary = "Get messages by post", description = "Retrieves all messages for a specific post")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Messages retrieved successfully", content = @Content(schema = @Schema(implementation = MessageResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized - requires authentication")
    })
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/posts/{id}")
    public ResponseEntity<List<MessageResponse>> getMessageByPost(@PathVariable Long id,
            Authentication authentication) {
        return ResponseEntity.ok(messageService.messageByPost(id, authentication));
    }

    @Operation(summary = "Get conversation", description = "Retrieves conversation between two users for a specific post")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Conversation retrieved successfully", content = @Content(schema = @Schema(implementation = MessageResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized - requires authentication")
    })
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/post/{postId}/conversation/{userId}")
    public ResponseEntity<List<MessageResponse>> getConversation(@PathVariable Long postId, @PathVariable Long userId,
            Authentication authentication) {
        return ResponseEntity.ok(messageService.getConversation(postId, userId, authentication));
    }
}
