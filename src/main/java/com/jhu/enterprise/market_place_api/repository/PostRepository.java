package com.jhu.enterprise.market_place_api.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jhu.enterprise.market_place_api.model.Post;
import com.jhu.enterprise.market_place_api.model.Post.PostStatus;
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
    List<Post> searchPosts(@Param("query") String query, @Param("userId") Long userId, @Param("tag") String tag);

    // Get recent posts
    Page<Post> findByStatusOrderByCreatedAtDesc(PostStatus status, Pageable pageable);

    // Get posts by seller
    Page<Post> findBySellerIdOrderByCreatedAtDesc(Long sellerId, Pageable pageable);

    // Search by title and description
    @Query("SELECT p FROM Post p WHERE p.status = :status AND " +
            "(LOWER(p.title) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(p.description) LIKE LOWER(CONCAT('%', :searchTerm, '%'))) " +
            "ORDER BY p.createdAt DESC")
    Page<Post> searchByTitleAndDescription(@Param("searchTerm") String searchTerm,
            @Param("status") PostStatus status,
            Pageable pageable);

    // Search by tags
    @Query("SELECT p FROM Post p WHERE p.status = :status AND :tag MEMBER OF p.tags " +
            "ORDER BY p.createdAt DESC")
    Page<Post> findByTag(@Param("tag") String tag,
            @Param("status") PostStatus status,
            Pageable pageable);

    // Search by multiple tags
    @Query("SELECT p FROM Post p WHERE p.status = :status AND " +
            "EXISTS (SELECT t FROM p.tags t WHERE t IN :tags) " +
            "ORDER BY p.createdAt DESC")
    Page<Post> findByTags(@Param("tags") List<String> tags,
            @Param("status") PostStatus status,
            Pageable pageable);

    // Search by user (seller)
    @Query("SELECT p FROM Post p WHERE p.status = :status AND " +
            "LOWER(p.seller.username) LIKE LOWER(CONCAT('%', :username, '%')) " +
            "ORDER BY p.createdAt DESC")
    Page<Post> findBySellerUsername(@Param("username") String username,
            @Param("status") PostStatus status,
            Pageable pageable);
}