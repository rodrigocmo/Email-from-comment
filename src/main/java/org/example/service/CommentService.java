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
        // 1. Salvar comentário
        comment.setCreatedAt(LocalDateTime.now());
        Comment savedComment = commentRepository.save(comment);

        // 2. Buscar dados para o evento
        Post post = postRepository.findById(comment.getPostId()).orElse(null);
        User postAuthor = userRepository.findById(post.getAuthorId()).orElse(null);
        User commentAuthor = userRepository.findById(comment.getAuthorId()).orElse(null);

        // 3. Só envia evento se o autor do post quer receber notificações
        if (postAuthor != null && postAuthor.getEmailNotifications()) {
            CommentCreatedEvent event = new CommentCreatedEvent(
                    savedComment.getId(),
                    post.getId(),
                    post.getTitle(),
                    postAuthor.getEmail(),
                    postAuthor.getName(),
                    commentAuthor.getName(),
                    savedComment.getContent(),
                    savedComment.getCreatedAt(),
                    postAuthor.getEmailNotifications()
            );

            // 4. Publicar no Kafka
            kafkaProducerService.publishCommentCreated(event);
        }

        return savedComment;
    }
}