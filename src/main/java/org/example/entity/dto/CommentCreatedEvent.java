package org.example.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentCreatedEvent {
    private Long commentId;
    private Long postId;
    private String postTitle;
    private String postAuthorEmail;
    private String postAuthorName;
    private String commentAuthorName;
    private String commentContent;
    private LocalDateTime createdAt;
    private Boolean emailNotificationsEnabled;
}