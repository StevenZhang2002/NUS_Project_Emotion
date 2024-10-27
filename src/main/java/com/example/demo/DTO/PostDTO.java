package com.example.demo.DTO;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO {
    private int postId;
    private int recordId;
    private int userId;
    private String username;
    private String email;
    private String title;
    private String content;
    private String mood;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private String topEmotion;
    private String comfortLanguage;
    private String behavioralGuidance;
}
