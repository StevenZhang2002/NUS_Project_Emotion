package com.example.demo.DTO;

import jakarta.validation.constraints.NotEmpty;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class PostDTO {
    private int postId;
    private int recordId;
    private int userId;
    private String title;
    private String content;
    private String mood;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private String topEmotion;
    private String comfortLanguage;
    private String behavioralGuidance;
}
