package org.example.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.example.entity.ProcessedEvent;
import org.example.entity.dto.CommentCreatedEvent;
import org.example.repository.ProcessedEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Slf4j
public class KafkaConsumerService {

    @Autowired
    private EmailService emailService;

    @Autowired
    private ProcessedEventRepository processedEventRepository;


    @KafkaListener(topics = "comment-created", groupId = "email-notification-group")
    @Transactional
    public void handleCommentCreated(CommentCreatedEvent event, Acknowledgment acknowledgment) {
        log.info("Recebido evento do Kafka: commentId={}, postId={}, eventId={}",
                event.getCommentId(), event.getPostId(), event.getEventId());

        try {
            if (isEventAlreadyProcessed(event.getEventId())) {
                log.info("Evento {} já foi processado anteriormente. Ignorando...", event.getEventId());
                acknowledgment.acknowledge();
                return;
            }
            saveProcessedEvent(event);

            //emailService.sendCommentNotification(event);
            log.info("Email enviado com sucesso para: {}", event.getPostAuthorEmail());

            //Confirma o processamento no Kafka
            acknowledgment.acknowledge();

        } catch (Exception e) {
            log.error("Erro ao processar evento {}: {}", event.getEventId(), e.getMessage(), e);
            throw new RuntimeException("Falha no processamento do evento", e);
        }
    }

    private boolean isEventAlreadyProcessed(String eventId) {
        return Optional.ofNullable(processedEventRepository.findByEventId(eventId)).isPresent();
    }

    private void saveProcessedEvent(CommentCreatedEvent event) {
        ProcessedEvent processedEvent = ProcessedEvent.builder()
                .eventId(event.getEventId())
                .eventType("comment-created")
                .payload(convertToJson(event))
                .processedAt(LocalDateTime.now())
                .status("SUCCESS")
                .build();

        processedEventRepository.save(processedEvent);
        log.info("Evento {} registrado como processado", event.getEventId());
    }

    private String convertToJson(CommentCreatedEvent event) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(event);
        } catch (Exception e) {
            log.warn("Erro ao converter evento para JSON: {}", e.getMessage());
            return event.toString();
        }
    }
}
