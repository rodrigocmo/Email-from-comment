package org.example.service;

import lombok.extern.slf4j.Slf4j;
import org.example.entity.dto.CommentCreatedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaProducerService {

    private static final String TOPIC = "comment-created";

    @Autowired
    private KafkaTemplate<String, CommentCreatedEvent> kafkaTemplate;

    public void publishCommentCreated(CommentCreatedEvent event) {
        log.info("Enviando evento para Kafka: commentId={}, postId={}",
                event.getCommentId(), event.getPostId());

        kafkaTemplate.send(TOPIC, event.getCommentId().toString(), event);

    }
}