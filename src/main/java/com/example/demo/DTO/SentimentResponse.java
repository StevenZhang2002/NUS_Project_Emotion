package com.example.demo.DTO;

import lombok.Data;

import java.util.List;
import java.util.Map;


@Data
public class SentimentResponse {

    private String data; // 用于解析data字段中的情绪数据
    private String topEmotion;
    private String comfortLanguage;
    private String behavioralGuidance;


}
