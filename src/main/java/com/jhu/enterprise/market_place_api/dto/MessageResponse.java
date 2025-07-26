package com.jhu.enterprise.market_place_api.dto;

import java.time.LocalDateTime;

import com.jhu.enterprise.market_place_api.model.Message;

import lombok.Data;

@Data
public class MessageResponse {
    private Long id; 
    private String content; 
    private Long senderId; 
    private String senderUsername; 
    private Long receiverId; 
    private String receiverUsername; 
    private Long postId; 
    private String postTitle;
    private LocalDateTime createdAt; 


    public MessageResponse(Message message){
        this.id = message.getId(); 
        this.content = message.getContent(); 
        this.senderId = message.getSender().getId(); 
        this.senderUsername = message.getSender().getUsername(); 
        this.receiverId = message.getReceiver().getId(); 
        this.receiverUsername = message.getReceiver().getUsername(); 
        this.postId = message.getPost().getId(); 
        this.postTitle = message.getPost().getTitle(); 
        this.createdAt = message.getCreatedAt(); 
    }
}
