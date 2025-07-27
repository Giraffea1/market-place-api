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

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/messages")
@CrossOrigin(origins = "*")
public class MessageController {
    
    @Autowired
    MessageService messageService; 
    
    @PostMapping
    public ResponseEntity<MessageResponse> sendMessage(@Valid @RequestBody MessageRequest messageRequest, Authentication authentication) {
        return ResponseEntity.ok(messageService.sendMessage(messageRequest, authentication));
    }

    @GetMapping("/posts/{id}")
    public ResponseEntity<List<MessageResponse>> getMessageByPost(@PathVariable Long id, Authentication authentication){
        return ResponseEntity.ok(messageService.messageByPost(id, authentication)); 
    }

    @GetMapping("/post/{postId}/conversation/{userId}")
    public ResponseEntity<List<MessageResponse>> getConversation(@PathVariable Long postId, @PathVariable Long userId, Authentication authentication){
        return ResponseEntity.ok(messageService.getConversation(postId, userId, authentication));
    }
}
