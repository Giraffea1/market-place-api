package com.jhu.enterprise.market_place_api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jhu.enterprise.market_place_api.model.Post;
import com.jhu.enterprise.market_place_api.model.User;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findBySeller(User seller);

    @Query("SELECT p FROM Post p WHERE " +
            "(:query IS NULL OR " +
            "LOWER(p.title) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(p.description) LIKE LOWER(CONCAT('%', :query, '%'))) AND " +
            "(:userId IS NULL OR p.seller.id = :userId) AND " +
            "(:tag IS NULL OR :tag MEMBER OF p.tags) AND " +
            "p.status = 'AVAILABLE'")
    List<Post> searchPosts(@Param ("query") String query, @Param("userId") Long userId, @Param("tag") String tag);
}
