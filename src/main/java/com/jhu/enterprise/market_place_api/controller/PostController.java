package com.jhu.enterprise.market_place_api.controller;

import java.util.Collections;
import java.util.Map;

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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/posts")
@Tag(name = "Post Management", description = "Marketplace post management and search APIs")
@CrossOrigin(origins = "*")
public class PostController {

    @Autowired
    private PostService postService;

    @Operation(summary = "Get post by ID", description = "Retrieves a specific post by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Post retrieved successfully", content = @Content(schema = @Schema(implementation = PostResponse.class))),
            @ApiResponse(responseCode = "404", description = "Post not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPostbyId(@PathVariable Long id) {
        return ResponseEntity.ok(postService.getPostbyId(id));
    }

    @Operation(summary = "Create new post", description = "Creates a new marketplace post")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Post created successfully", content = @Content(schema = @Schema(implementation = PostResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid post data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - requires authentication")
    })
    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping
    public ResponseEntity<PostResponse> createPost(@Valid @RequestBody PostRequest postRequest,
            Authentication authentication) {
        return ResponseEntity.ok(postService.createPost(postRequest, authentication));
    }

    @Operation(summary = "Update post", description = "Updates an existing post (owner only)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Post updated successfully", content = @Content(schema = @Schema(implementation = PostResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid post data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - requires authentication"),
            @ApiResponse(responseCode = "403", description = "Forbidden - not the post owner")
    })
    @SecurityRequirement(name = "Bearer Authentication")
    @PutMapping("/{id}")
    public ResponseEntity<PostResponse> updatePost(@PathVariable Long id, @Valid @RequestBody PostRequest postRequest,
            Authentication authentication) {
        return ResponseEntity.ok(postService.updatePost(id, postRequest, authentication));
    }

    @Operation(summary = "Mark post as sold", description = "Marks a post as sold (owner only)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Post marked as sold successfully", content = @Content(schema = @Schema(implementation = PostResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized - requires authentication"),
            @ApiResponse(responseCode = "403", description = "Forbidden - not the post owner")
    })
    @SecurityRequirement(name = "Bearer Authentication")
    @PutMapping("/{id}/sold")
    public ResponseEntity<PostResponse> markPostAsSold(@PathVariable Long id, Authentication authentication) {
        return ResponseEntity.ok(postService.markPostAsSold(id, authentication));
    }

    @Operation(summary = "Delete post", description = "Deletes a post (owner only)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Post deleted successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - requires authentication"),
            @ApiResponse(responseCode = "403", description = "Forbidden - not the post owner")
    })
    @SecurityRequirement(name = "Bearer Authentication")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePost(@PathVariable Long id, Authentication authentication) {
        postService.deletePost(id, authentication);
        return ResponseEntity.ok("Post deleted successfully");
    }

    @Operation(summary = "Delete post", description = "Deletes a post (Admin only)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Post deleted successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - requires authentication"),
            @ApiResponse(responseCode = "403", description = "Forbidden - not the admin")
    })
    @SecurityRequirement(name = "Bearer Authentication")
    @DeleteMapping("/admin/{id}")
    public ResponseEntity<Map<String, String>> adminDeletePost(@PathVariable Long id, Authentication authentication){
        String result = postService.adminDelete(id, authentication);
        return ResponseEntity.ok(Collections.singletonMap("message", result)); 
    }

    @Operation(summary = "Get recent posts", description = "Retrieves paginated list of recent available posts")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Posts retrieved successfully", content = @Content(schema = @Schema(implementation = PostResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid pagination parameters")
    })
    @GetMapping
    public ResponseEntity<Page<PostResponse>> getRecentPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(postService.getRecentPosts(page, size));
    }

    @Operation(summary = "Search posts", description = "Searches posts by title, description, tags, or seller username")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Search results retrieved successfully", content = @Content(schema = @Schema(implementation = PostResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid search parameters")
    })
    @GetMapping("/search")
    public ResponseEntity<Page<PostResponse>> searchPosts(PostSearchRequest request) {
        return ResponseEntity.ok(postService.searchPosts(request));
    }

    @Operation(summary = "Get posts by user", description = "Retrieves all available posts by a specific user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Posts retrieved successfully", content = @Content(schema = @Schema(implementation = PostResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid pagination parameters")
    })
    @GetMapping("/user/{userId}")
    public ResponseEntity<Page<PostResponse>> getPostsByUser(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(postService.getPostsByUser(userId, page, size));
    }
}