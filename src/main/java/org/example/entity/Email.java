package org.example.entity;

import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Setter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "email")
public class Email {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "comment_id", nullable = false)
    private Long commentId;

    @Column(name = "recipient_email", nullable = false)
    private String recipientEmail;

    @Column(name = "subject", length = 500)
    private String subject;

    @Column(name = "sent_at")
    private LocalDateTime sentAt;

    @Column(name = "status", length = 20)
    private String status; // SENT, FAILED, PENDING

    @Column(name = "error_message", columnDefinition = "TEXT")
    private String errorMessage;

    // constructors, getters, setters
}