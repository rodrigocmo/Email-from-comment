package org.example.service;


import lombok.extern.slf4j.Slf4j;
import org.example.entity.dto.CommentCreatedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaConsumerService {

    @Autowired
    private EmailService emailService;

    @KafkaListener(topics = "comment-created", groupId = "email-notification-group")
    public void handleCommentCreated(CommentCreatedEvent event) {
        log.info("Recebido evento do Kafka: commentId={}, postId={}",
                event.getCommentId(), event.getPostId());

        try {
            emailService.sendCommentNotification(event);
            log.info("Email enviado com sucesso para: {}", event.getPostAuthorEmail());
        } catch (Exception e) {
            log.error("Erro ao enviar email: {}", e.getMessage(), e);
        }
    }
}