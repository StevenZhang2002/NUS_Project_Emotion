package com.example.demo.Entity;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
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
    List<Photo> photoList;
}
