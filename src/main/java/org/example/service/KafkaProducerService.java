package org.example.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.example.entity.Comment;
import org.example.entity.dto.CommentCreatedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class KafkaProducerService {

    private static final String TOPIC = "comment-created";

    @Autowired
    private KafkaTemplate<String, CommentCreatedEvent> kafkaTemplate;


    public void publishCommentCreated(Comment comment) {

        CommentCreatedEvent event = CommentCreatedEvent.create(comment);
        //postId como chave para garantir que comentários do mesmo post
        // vão sempre para a mesma partição (mantendo ordem)
        String partitionKey = comment.getPostId().toString();

        try {
            // usar interface future para manter async
            CompletableFuture<SendResult<String, CommentCreatedEvent>> future =
                    kafkaTemplate.send("comment-created", partitionKey, event);

            future.whenComplete((result, erro) -> {
                if (Objects.nonNull(erro)) {
                    log.error("Erro ao enviar mensagem para Kafka: {}", erro.getMessage(), erro);
                } else {
                    RecordMetadata metadata = result.getRecordMetadata();
                    log.info("Mensagem enviada com sucesso - Topic: {}, Partition: {}, Offset: {}",
                            metadata.topic(),
                            metadata.partition(),
                            metadata.offset());
                }
            }); // caso nao queira usar biconsumer pode usar thenaccept e exceptionally

        } catch (Exception e) {
            log.error("Erro inesperado ao enviar evento: commentId={}", comment.getId(), e);
            throw new RuntimeException("Falha ao publicar evento", e);
        }
    }


    public void publishCommentCreatedSync(Comment comment) {
        CommentCreatedEvent event = CommentCreatedEvent.create(comment);

        // Envio SÍNCRONO - bloqueia até confirmar entrega
        try {
            SendResult<String, CommentCreatedEvent> result = kafkaTemplate
                    .send("comment-created", comment.getPostId().toString(), event)
                    .get(10, TimeUnit.SECONDS); // get prende a thread até a conclusao ou timeout

            log.info("Evento confirmado sincronamente: offset={}",
                    result.getRecordMetadata().offset());
        }catch (Exception e){
            log.error("Erro inesperado ao enviar evento: commentId={}", comment.getId(), e);
        }
    }


}