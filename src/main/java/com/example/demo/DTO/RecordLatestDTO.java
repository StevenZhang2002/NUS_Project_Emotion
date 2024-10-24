package com.example.demo.DTO;

import cn.hutool.json.JSON;
import com.example.demo.Entity.Photo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;
@Data
@AllArgsConstructor
public class RecordLatestDTO {
    private Integer recordId;
    private Integer userId;
    private String      content;
    private String mood;
    private JSON moodJson;
    private String topEmotion;
    private String comfortLanguage;
    private String behavioralGuidance;
    private Timestamp createdAt;
    private Timestamp updatedAt;

}
