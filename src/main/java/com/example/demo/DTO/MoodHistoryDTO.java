package com.example.demo.DTO;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class MoodHistoryDTO {
    int userId;
    int recordId;
    String mood;

}
