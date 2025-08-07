package org.example.service;


import lombok.extern.slf4j.Slf4j;
import org.example.entity.Email;
import org.example.entity.dto.CommentCreatedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private EmailRepository emailRepository;

    public void sendCommentNotification(CommentCreatedEvent event) {
        String subject = "Novo comentário no seu post: " + event.getPostTitle();
        String text = String.format(
                "Olá %s,\n\n" +
                        "%s comentou no seu post '%s':\n\n" +
                        "\"%s\"\n\n" +
                        "Acesse o sistema para ver mais detalhes.\n\n" +
                        "Atenciosamente,\nEquipe do Sistema",
                event.getPostAuthorName(),
                event.getCommentAuthorName(),
                event.getPostTitle(),
                event.getCommentContent()
        );

        try {
            // Enviar email
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(event.getPostAuthorEmail());
            message.setSubject(subject);
            message.setText(text);
            mailSender.send(message);

            // Registrar envio bem-sucedido
            saveEmailRecord(event, subject, "SENT", null);

        } catch (Exception e) {
            log.error("Erro ao enviar email: {}", e.getMessage());
            saveEmailRecord(event, subject, "FAILED", e.getMessage());
            throw e;
        }
    }

    private void saveEmailRecord(CommentCreatedEvent event, String subject,
                                 String status, String errorMessage) {
        Email email = new Email();
        email.setCommentId(event.getCommentId());
        email.setRecipientEmail(event.getPostAuthorEmail());
        email.setSubject(subject);
        email.setSentAt(LocalDateTime.now());
        email.setStatus(status);
        email.setErrorMessage(errorMessage);

        emailRepository.save(email);
    }
}