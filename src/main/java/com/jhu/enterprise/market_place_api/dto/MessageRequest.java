package com.jhu.enterprise.market_place_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MessageRequest {
    @NotNull(message= "Post ID is required")
    private Long postId; 

    @NotNull(message= "Receiver ID isrequired")
    private Long receiverID; 

    @NotBlank(message = "Content is required")
    private String content; 
}
