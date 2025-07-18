package com.jhu.enterprise.market_place_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jhu.enterprise.market_place_api.model.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
    
}
