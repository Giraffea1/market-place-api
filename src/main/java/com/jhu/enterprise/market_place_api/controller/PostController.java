package com.jhu.enterprise.market_place_api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jhu.enterprise.market_place_api.dto.PostRequest;
import com.jhu.enterprise.market_place_api.dto.PostResponse;
import com.jhu.enterprise.market_place_api.dto.PostSearchRequest;
import com.jhu.enterprise.market_place_api.services.PostService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/posts")
@CrossOrigin(origins = "*")
public class PostController {

    @Autowired
    private PostService postService;

    // Get post by Id
    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPostbyId(@PathVariable Long id) {
        return ResponseEntity.ok(postService.getPostbyId(id));
    }

    // Create Post
    @PostMapping
    public ResponseEntity<PostResponse> createPost(@Valid @RequestBody PostRequest postRequest,
            Authentication authentication) {
        return ResponseEntity.ok(postService.createPost(postRequest, authentication));
    }

    // update post
    @PutMapping("/{id}")
    public ResponseEntity<PostResponse> updatePost(@PathVariable Long id, @Valid @RequestBody PostRequest postRequest,
            Authentication authentication) {
        return ResponseEntity.ok(postService.updatePost(id, postRequest, authentication));
    }

    // mark and item as sold
    @PutMapping("/{id}/sold")
    public ResponseEntity<PostResponse> markPostAsSold(@PathVariable Long id, Authentication authentication) {
        return ResponseEntity.ok(postService.markPostAsSold(id, authentication));
    }

    // delete a post
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePost(@PathVariable Long id, Authentication authentication) {
        postService.deletePost(id, authentication);
        return ResponseEntity.ok("Post deleted successfully");
    }

    // Get recent posts (paginated)
    @GetMapping
    public ResponseEntity<Page<PostResponse>> getRecentPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(postService.getRecentPosts(page, size));
    }

    // Search posts (unified search endpoint)
    @GetMapping("/search")
    public ResponseEntity<Page<PostResponse>> searchPosts(PostSearchRequest request) {
        return ResponseEntity.ok(postService.searchPosts(request));
    }

    // Get posts by user (paginated)
    @GetMapping("/user/{userId}")
    public ResponseEntity<Page<PostResponse>> getPostsByUser(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(postService.getPostsByUser(userId, page, size));
    }
}