package com.jhu.enterprise.market_place_api.service;

import org.springframework.stereotype.Service;

import com.jhu.enterprise.market_place_api.dto.request.CreatePostRequest;
import com.jhu.enterprise.market_place_api.model.Post;
import com.jhu.enterprise.market_place_api.repository.PostRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository; 

    public Post createPost(CreatePostRequest request, User seller) {
        Post post = new Post(); 
        post.setTitle(request.getTitle());
        post.setDescription(request.getDescription());
        post.setAskPrice(request.getPrice());
        post.setSeller(seller);
        return postRepository.save(post); 
        
    }
}
