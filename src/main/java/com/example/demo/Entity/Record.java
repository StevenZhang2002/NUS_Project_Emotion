package com.example.demo.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.sql.Timestamp;
import java.util.List;

@Data
public class Record {
    private Integer recordId;
    private Integer userId;
    @NotEmpty
    private String title;
    @NotEmpty
    private String content;
    private Integer mood;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    @JsonIgnore
    List<Photo> photoList;
}
