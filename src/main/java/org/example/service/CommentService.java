package org.example.service;


import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.example.entity.Comment;
import org.example.entity.Post;
import org.example.entity.User;
import org.example.entity.dto.CommentCreatedEvent;
import org.example.repository.CommentRepository;
import org.example.repository.PostRepository;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
@Slf4j
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private KafkaProducerService kafkaProducerService;

    @Transactional
    public Comment createComment(Comment comment) {
        saveComment(comment);
        construirEvento(comment);
        return comment;
    }

    public void saveComment(Comment comment){
        try {
            comment.setCreatedAt(LocalDateTime.now());
            commentRepository.save(comment);
        }catch (Exception e){
            throw new RuntimeException("Falha ao salvar comentario");
        }
    }

    public void construirEvento(Comment comment){
        Post post = postRepository.findById(comment.getPostId()).orElse(null);
        User postAuthor = userRepository.findById(post.getAuthorId()).orElse(null);
        User commentAuthor = userRepository.findById(comment.getAuthorId()).orElse(null);

        if (Objects.nonNull(postAuthor) && postAuthor.getEmailNotifications()) {
            CommentCreatedEvent event = CommentCreatedEvent.builder()
                    .commentId(comment.getId())
                    .postId(post.getId())
                    .postTitle(post.getTitle())
                    .postAuthorEmail(postAuthor.getEmail())
                    .postAuthorName(postAuthor.getName())
                    .commentAuthorName(commentAuthor.getName())
                    .commentContent(comment.getContent())
                    .createdAt(comment.getCreatedAt())
                    .emailNotificationsEnabled(postAuthor.getEmailNotifications())
                    .build();

            kafkaProducerService.publishCommentCreated(event);
        }
    }
}