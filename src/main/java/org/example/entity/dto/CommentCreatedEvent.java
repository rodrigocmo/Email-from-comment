package org.example.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.entity.Comment;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentCreatedEvent {
    private Long commentId;
    private Long postId;
    private Long userId;
    private String eventId;
    private String postTitle;
    private String postAuthorEmail;
    private String postAuthorName;
    private String commentAuthorName;
    private String commentContent;
    private LocalDateTime createdAt;
    private Boolean emailNotificationsEnabled;


    public static CommentCreatedEvent create(Comment comment) {
        return CommentCreatedEvent.builder()
                .commentId(comment.getId())
                .eventId(UUID.randomUUID().toString())
                .createdAt(LocalDateTime.now())
                .postId(comment.getPostId())
                .userId(comment.getAuthorId())
                .commentContent(comment.getContent())
                .build();
    }
}