package com.jhu.enterprise.market_place_api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.jhu.enterprise.market_place_api.dto.request.CreatePostRequest;
import com.jhu.enterprise.market_place_api.model.Post;
import com.jhu.enterprise.market_place_api.service.PostService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/marketplace/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService; 

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Post createPost (@Validated @RequestBody CreatePostRequest request, @AuthenticationPrincipal User seller) {
        return postService.createPost(request, seller); 
    }

}
