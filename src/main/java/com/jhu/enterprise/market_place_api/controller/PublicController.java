package com.jhu.enterprise.market_place_api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/public")
public class PublicController {
    @GetMapping
    public String publicEndpoint() {
        return "This is a public endpoint";
    }
}
