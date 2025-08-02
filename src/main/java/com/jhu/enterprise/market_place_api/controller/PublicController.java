package com.jhu.enterprise.market_place_api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/public")
@Tag(name = "Public", description = "Public endpoints that don't require authentication")
public class PublicController {

    @Operation(summary = "Public endpoint", description = "A simple public endpoint for testing connectivity")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Public endpoint accessible")
    })
    @GetMapping
    public String publicEndpoint() {
        return "This is a public endpoint";
    }
}
