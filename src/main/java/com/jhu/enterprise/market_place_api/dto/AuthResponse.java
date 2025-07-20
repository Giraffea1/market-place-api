package com.jhu.enterprise.market_place_api.dto;

import lombok.Data;

@Data
public class AuthResponse {

    private String token;
    private String type = "Bearer";
    private String username;
    private String email;
    private String role;

    public AuthResponse() {
    }

    public AuthResponse(String token, String username, String email, String role) {
        this.token = token;
        this.username = username;
        this.email = email;
        this.role = role;
    }
}