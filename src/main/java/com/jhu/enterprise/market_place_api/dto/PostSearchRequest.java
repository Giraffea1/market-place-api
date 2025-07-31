package com.jhu.enterprise.market_place_api.dto;

import lombok.Data;
import java.util.List;

@Data
public class PostSearchRequest {
    private String searchTerm; // For title/description search
    private List<String> tags; // For tag search
    private String sellerUsername; // For user search
    private Integer page = 0;
    private Integer size = 20;
    private String sortBy = "createdAt";
    private String sortDirection = "desc";
}