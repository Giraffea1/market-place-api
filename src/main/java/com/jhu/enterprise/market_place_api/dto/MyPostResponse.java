package com.jhu.enterprise.market_place_api.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.jhu.enterprise.market_place_api.model.Post;

import lombok.Data;

@Data
public class MyPostResponse {

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

    // Private information only visible to the owner
    private Integer viewCount;
    private Integer messageCount;
    private Boolean canEdit;
    private Boolean canDelete;
    private String sellerEmail; // For contact purposes
    private String sellerPhoneNumber; // For contact purposes

    public MyPostResponse(Post post) {
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

        // Owner-specific information
        this.viewCount = 0;
        this.messageCount = 0;
        this.canEdit = true;
        this.canDelete = true;
        this.sellerEmail = post.getSeller().getEmail();
        this.sellerPhoneNumber = post.getSeller().getPhoneNumber();
    }
}