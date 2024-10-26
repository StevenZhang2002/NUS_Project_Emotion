package com.example.demo.DTO;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONObject;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.json.Json;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class MoodHistoryDTO {
    int userId;
    int recordId;
    @JsonIgnore
    String mood;
    JSON moodJson;
    String content;
    Timestamp createdAt;

}
