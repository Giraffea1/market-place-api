package com.jhu.enterprise.market_place_api.controller;

import com.jhu.enterprise.market_place_api.dto.PostResponse;
import com.jhu.enterprise.market_place_api.dto.MyPostResponse;
import com.jhu.enterprise.market_place_api.dto.UserUpdateRequest;
import com.jhu.enterprise.market_place_api.model.User;
import com.jhu.enterprise.market_place_api.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.data.domain.Page;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@Tag(name = "User Management", description = "User profile and post management APIs")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService userService;

    @Operation(summary = "Get user profile", description = "Retrieves the current authenticated user's profile information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Profile retrieved successfully", content = @Content(schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized - invalid or missing token")
    })
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/profile")
    public ResponseEntity<User> getProfile(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return ResponseEntity.ok(user);
    }

    @Operation(summary = "Update user profile", description = "Updates the current user's profile information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Profile updated successfully", content = @Content(schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - invalid or missing token"),
            @ApiResponse(responseCode = "409", description = "Email already exists")
    })
    @SecurityRequirement(name = "Bearer Authentication")
    @PutMapping("/profile")
    public ResponseEntity<User> updateProfile(
            @Valid @RequestBody UserUpdateRequest request,
            Authentication authentication) {
        User currentUser = (User) authentication.getPrincipal();
        User updatedUser = userService.updateProfile(currentUser.getId(), request);
        return ResponseEntity.ok(updatedUser);
    }

    @Operation(summary = "Delete user account", description = "Permanently deletes the current user's account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User account deleted successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - invalid or missing token")
    })
    @SecurityRequirement(name = "Bearer Authentication")
    @DeleteMapping("/profile")
    public ResponseEntity<?> deleteProfile(Authentication authentication) {
        User currentUser = (User) authentication.getPrincipal();
        userService.deleteUser(currentUser.getId());
        return ResponseEntity.ok("User deleted successfully");
    }

    @Operation(summary = "Get my posts", description = "Retrieves all posts created by the current user (including SOLD posts) with private information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Posts retrieved successfully", content = @Content(schema = @Schema(implementation = MyPostResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized - invalid or missing token")
    })
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/posts")
    public ResponseEntity<Page<MyPostResponse>> getMyPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            Authentication authentication) {
        User currentUser = (User) authentication.getPrincipal();
        return ResponseEntity.ok(userService.getMyPosts(currentUser.getId(), page, size));
    }
}