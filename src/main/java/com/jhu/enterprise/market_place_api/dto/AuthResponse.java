package com.jhu.enterprise.market_place_api.dto;

import lombok.Data;

@Data
public class AuthResponse {

    private Long id;
    private String token;
    private String type = "Bearer";
    private String username;
    private String email;
    private String role;

    public AuthResponse() {
    }

    public AuthResponse(Long id, String token, String username, String email, String role) {
        this.id = id;
        this.token = token;
        this.username = username;
        this.email = email;
        this.role = role;
    }
}