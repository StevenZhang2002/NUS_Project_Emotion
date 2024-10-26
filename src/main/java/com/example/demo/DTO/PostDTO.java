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
    private String Title;
    private String Content;
    private String Mood;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private String TopEmotion;
    private String ComfortLanguage;
    private String BehavioralGuidance;
}
