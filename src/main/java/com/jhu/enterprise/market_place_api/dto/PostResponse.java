package com.jhu.enterprise.market_place_api.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.jhu.enterprise.market_place_api.model.Post;

import lombok.Data;

@Data
public class PostResponse {
    
    private Long id;
    private String title;
    private String description;
    private List<String> tags;
    private Double askingPrice;
    private String status;
    private Long sellerId;
    private String sellerUsername;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public PostResponse(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.description = post.getDescription();
        this.tags = post.getTags(); 
        this.askingPrice = post.getAskingPrice(); 
        this.status = post.getStatus().name();
        this.sellerId = post.getSeller().getId();
        this.sellerUsername = post.getSeller().getUsername(); 
        this.createdAt = post.getCreatedAt();
        this.updatedAt = post.getUpdatedAt(); 
    }
}
