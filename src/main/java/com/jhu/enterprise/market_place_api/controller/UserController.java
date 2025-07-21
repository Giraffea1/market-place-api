package com.jhu.enterprise.market_place_api.controller;

import com.jhu.enterprise.market_place_api.dto.PostResponse;
import com.jhu.enterprise.market_place_api.dto.UserUpdateRequest;
import com.jhu.enterprise.market_place_api.model.User;
import com.jhu.enterprise.market_place_api.services.PostService;
import com.jhu.enterprise.market_place_api.services.UserService;
import jakarta.validation.Valid;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    @GetMapping("/profile")
    public ResponseEntity<User> getProfile(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return ResponseEntity.ok(user);
    }

    @PutMapping("/profile")
    public ResponseEntity<User> updateProfile(
            @Valid @RequestBody UserUpdateRequest request,
            Authentication authentication) {
        User currentUser = (User) authentication.getPrincipal();
        User updatedUser = userService.updateProfile(currentUser.getId(), request);
        return ResponseEntity.ok(updatedUser);
    }

    @GetMapping("/{id}/posts")
    public ResponseEntity<List<PostResponse>> getUserPosts(@PathVariable Long id) {
        // TODO: Implement this
        return ResponseEntity.ok(postService.getUsersPosts(id));
    }

    @DeleteMapping("/profile")
    public ResponseEntity<?> deleteProfile(Authentication authentication) {
        User currentUser = (User) authentication.getPrincipal();
        userService.deleteUser(currentUser.getId());
        return ResponseEntity.ok().build();
    }
}