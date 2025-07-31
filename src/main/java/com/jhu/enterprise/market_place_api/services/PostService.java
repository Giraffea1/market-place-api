package com.jhu.enterprise.market_place_api.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.jhu.enterprise.market_place_api.dto.PostRequest;
import com.jhu.enterprise.market_place_api.dto.PostResponse;
import com.jhu.enterprise.market_place_api.dto.PostSearchRequest;
import com.jhu.enterprise.market_place_api.model.Post;
import com.jhu.enterprise.market_place_api.model.Post.PostStatus;
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

        // if user is not owner of the post or an admin
        if (!post.getSeller().getId().equals(currentUser.getId())) {
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

        // if user is not owner of the post or an admin
        if (!post.getSeller().getId().equals(currentUser.getId())) {
            throw new RuntimeException("You are not authorized to update this post");
        }

        post.setStatus(Post.PostStatus.SOLD);
        Post updaPost = postRepository.save(post);
        return new PostResponse(updaPost);
    }

    public void deletePost(Long id, Authentication authentication) {
        User currentUser = (User) authentication.getPrincipal();
        Post post = postRepository.findById(id).orElseThrow(() -> new RuntimeException("Post not found"));

        // if user is not owner of the post or an admin
        if (!post.getSeller().getId().equals(currentUser.getId())) {
            throw new RuntimeException("You are not authorized to update this post");
        }

        postRepository.delete(post);
    }

    // Get recent posts
    public Page<PostResponse> getRecentPosts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Post> postPage = postRepository.findByStatusOrderByCreatedAtDesc(PostStatus.AVAILABLE, pageable);
        return postPage.map(PostResponse::new);
    }

    // Search posts
    public Page<PostResponse> searchPosts(PostSearchRequest request) {
        Pageable pageable = PageRequest.of(
                request.getPage(),
                request.getSize(),
                Sort.by(Sort.Direction.fromString(request.getSortDirection()), request.getSortBy()));

        // Search by title and description
        if (request.getSearchTerm() != null && !request.getSearchTerm().trim().isEmpty()) {
            Page<Post> postPage = postRepository.searchByTitleAndDescription(
                    request.getSearchTerm().trim(),
                    PostStatus.AVAILABLE,
                    pageable);
            return postPage.map(PostResponse::new);
        }

        // Search by tags
        if (request.getTags() != null && !request.getTags().isEmpty()) {
            Page<Post> postPage = postRepository.findByTags(request.getTags(), PostStatus.AVAILABLE, pageable);
            return postPage.map(PostResponse::new);
        }

        // Search by seller username
        if (request.getSellerUsername() != null && !request.getSellerUsername().trim().isEmpty()) {
            Page<Post> postPage = postRepository.findBySellerUsername(
                    request.getSellerUsername().trim(),
                    PostStatus.AVAILABLE,
                    pageable);
            return postPage.map(PostResponse::new);
        }

        // Default: return recent posts
        return getRecentPosts(request.getPage(), request.getSize());
    }

    // Get posts by user (public view - only AVAILABLE posts)
    public Page<PostResponse> getPostsByUser(Long userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Post> postPage = postRepository.findBySellerIdAndStatusOrderByCreatedAtDesc(userId, PostStatus.AVAILABLE,
                pageable);
        return postPage.map(PostResponse::new);
    }
}
