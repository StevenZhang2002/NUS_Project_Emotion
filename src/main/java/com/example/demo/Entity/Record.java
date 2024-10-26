package com.example.demo.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.sql.Timestamp;
import java.util.List;
import cn.hutool.json.JSON;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Record {
    private Integer recordId;
    private Integer userId;
    @NotEmpty
    private String title;
    @NotEmpty
    private String content;
    private String mood;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private String topEmotion;
    private String comfortLanguage;
    private String behavioralGuidance;
    @JsonIgnore
    List<Photo> photoList;
}
