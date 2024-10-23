package com.example.demo.DTO;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONObject;
import jakarta.json.Json;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class MoodHistoryDTO {
    int userId;
    int recordId;
    String mood;
}
