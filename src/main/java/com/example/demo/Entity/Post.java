package com.example.demo.Entity;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.sql.Timestamp;

import java.util.List;
@Data
public class Post {
    private Integer postId;
    private Integer recordId; // 外键
    @NotNull(message = "UserId is required")
    private Integer userId; // 外键
    private Timestamp createdAt;
    private Timestamp updatedAt;
    List<Likes>likesList;
}