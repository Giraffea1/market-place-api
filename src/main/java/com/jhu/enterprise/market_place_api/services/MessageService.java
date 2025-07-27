package com.jhu.enterprise.market_place_api.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.jhu.enterprise.market_place_api.dto.MessageRequest;
import com.jhu.enterprise.market_place_api.dto.MessageResponse;
import com.jhu.enterprise.market_place_api.model.Message;
import com.jhu.enterprise.market_place_api.model.Post;
import com.jhu.enterprise.market_place_api.model.User;
import com.jhu.enterprise.market_place_api.repository.MessageRepository;
import com.jhu.enterprise.market_place_api.repository.PostRepository;
import com.jhu.enterprise.market_place_api.repository.UserRepository;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository; 

    @Autowired
    private UserRepository userRepository; 

    @Autowired
    private PostRepository postRepository; 

    public MessageResponse sendMessage(MessageRequest messageRequest, Authentication authentication) {
        
        User currUser = (User) authentication.getPrincipal(); 
        User receiver = userRepository.findById(messageRequest.getReceiverID()).orElseThrow(() -> new RuntimeException("Receiver not found!")); 
        Post post = postRepository.findById(messageRequest.getPostId()).orElseThrow(() -> new RuntimeException("Post not found!")); 

        //prevents from messaging theirselves 
        if(currUser.getId().equals(receiver.getId())){
            throw new IllegalArgumentException("Cannot send message to yourself"); 
        }

        Message message = new Message(
            messageRequest.getContent(),
            currUser,
            receiver,
            post
        );

        Message savedMessage = messageRepository.save(message); 
        return new MessageResponse(savedMessage); 
    }

    public List<MessageResponse> messageByPost(Long postId, Authentication authentication){

        User currUser = (User) authentication.getPrincipal(); 
        Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Post not found!")); 

        //Get all messages for this post
        List<Message> messages = messageRepository.findByPostOrderByCreatedAtAsc(postId)
        .stream()
        .filter(m -> m.getSender().getId().equals(currUser.getId()) || 
                    m.getReceiver().getId().equals(currUser.getId()))
        .collect(Collectors.toList());

        return messages.stream().map(MessageResponse::new).collect(Collectors.toList()); 

    }

    public List<MessageResponse> getConversation(Long postId, Long otherUserId, Authentication authentication) {
        User currUser = (User) authentication.getPrincipal(); 
        return messageRepository.findByPostAndSenderOrderByCreatedAtAsc(postId, currUser.getId(), otherUserId).stream().map(MessageResponse::new).collect(Collectors.toList());
    }

    
}
