package com.jhu.enterprise.market_place_api.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.jhu.enterprise.market_place_api.dto.PostRequest;
import com.jhu.enterprise.market_place_api.dto.PostResponse;
import com.jhu.enterprise.market_place_api.model.Post;
import com.jhu.enterprise.market_place_api.model.Role;
import com.jhu.enterprise.market_place_api.model.User;
import com.jhu.enterprise.market_place_api.repository.PostRepository;
import com.jhu.enterprise.market_place_api.repository.UserRepository;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository; 

    @Autowired
    private UserRepository userRepository; 

    public List<PostResponse> getAllPosts() {
        return postRepository.findAll().stream().map(PostResponse::new).collect(Collectors.toList());
    }

    public PostResponse getPostbyId(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new RuntimeException("Post not found")); 
        return new PostResponse(post); 
    }

    public PostResponse createPost(PostRequest postRequest, Authentication authentication) {
        User currentUser = (User) authentication.getPrincipal(); 

        Post post = new Post(); 
        post.setTitle(postRequest.getTitle());
        post.setDescription(postRequest.getDescription()); 
        post.setTags(postRequest.getTags());
        post.setAskingPrice(postRequest.getAskingPrice());
        post.setSeller(currentUser);

        Post savPost = postRepository.save(post);
        return new PostResponse(savPost);
    }

    public PostResponse updatePost(long id, PostRequest postRequest, Authentication authentication) {

        User currentUser = (User) authentication.getPrincipal();
        Post post = postRepository.findById(id).orElseThrow(() -> new RuntimeException("Post not found"));

        //if user is not owner of the post or an admin
        if(!post.getSeller().getId().equals(currentUser.getId())){
            throw new RuntimeException("You are not authorized to update this post"); 
        }

        post.setTitle(postRequest.getTitle());
        post.setDescription(postRequest.getDescription());
        post.setTags(postRequest.getTags());
        post.setAskingPrice(postRequest.getAskingPrice());

        Post updatePost = postRepository.save(post);
        return new PostResponse(updatePost);
    }

    public PostResponse markPostAsSold(Long id, Authentication authentication) {
        User currentUser = (User) authentication.getPrincipal(); 

        Post post = postRepository.findById(id).orElseThrow(() -> new RuntimeException("Post not found"));

        //if user is not owner of the post or an admin
        if(!post.getSeller().getId().equals(currentUser.getId())){
            throw new RuntimeException("You are not authorized to update this post"); 
        }

        post.setStatus(Post.PostStatus.SOLD);
        Post updaPost = postRepository.save(post);
        return new PostResponse(updaPost);
    }

    public void deletePost(Long id, Authentication authentication) {
        User currentUser = (User) authentication.getPrincipal();
        Post post = postRepository.findById(id).orElseThrow(() -> new RuntimeException("Post not found"));  
        
        //if user is not owner of the post or an admin
        if(!post.getSeller().getId().equals(currentUser.getId())){
            throw new RuntimeException("You are not authorized to update this post"); 
        }

        postRepository.delete(post);
    }

    //delete post as an admin
    public String adminDelete(Long id, Authentication authentication){

        User admin = (User) authentication.getPrincipal();
        if(!admin.getRole().equals(Role.ADMIN)){
            throw new AccessDeniedException("Only admins can perform this action");
        }
        
        Post post = postRepository.findById(id).orElseThrow(() -> new RuntimeException("Post not found with id: "+ id)); 
        String postTitle = post.getTitle();
        postRepository.delete(post);
        return "Deleted successfully: Post ID " + id + " - " + postTitle; 
    }

    public List<PostResponse> searchPosts(String query, Long userId, String tag) {
        return postRepository.searchPosts(query, userId, tag).stream().map(PostResponse::new).collect(Collectors.toList());
    }

    public List<PostResponse> getUsersPosts(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        return postRepository.findBySeller(user).stream().map(PostResponse::new).collect(Collectors.toList());
    }

}
