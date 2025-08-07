package com.jhu.enterprise.market_place_api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "Market Place API is running! Visit /swagger-ui/index.html for API documentation.";
    }

    @GetMapping("/health")
    public String health() {
        return "OK";
    }
}