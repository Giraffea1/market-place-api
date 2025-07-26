package com.jhu.enterprise.market_place_api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jhu.enterprise.market_place_api.model.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query("SELECT m FROM Message m WHERE m.post.id = :postId ORDER BY m.createdAt ASC")
    List<Message> findByPostOrderByCreatedAtAsc(@Param("postId") Long postId);

    @Query("SELECT m FROM Message m WHERE m.post.id = :postId " +
           "AND ((m.sender.id = :userId1 AND m.receiver.id = :userId2) OR " +
           "(m.sender.id = :userId2 AND m.receiver.id = :userId1)) " +
           "ORDER BY m.createdAt ASC")
    List<Message> findByPostAndSenderOrderByCreatedAtAsc(Long postId, Long userId1, Long userId2); 
}
