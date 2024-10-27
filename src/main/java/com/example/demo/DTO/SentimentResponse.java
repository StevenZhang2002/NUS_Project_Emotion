package com.example.demo.DTO;

import lombok.Data;

import java.util.List;
import java.util.Map;
@Data
public class SentimentResponse {
    private List<List<IntensityDTO.EmotionLabel>> data;
    @Data
    public static class EmotionLabel {
        private String label;
        private double score;
        @Override
        public String toString() {
            return "{" + "label='" + label + '\'' + ", score=" + score + '}';
        }
    }
    private String topEmotion;
    private String comfortLanguage;
    private String behavioralGuidance;

    // Getters and setters

}
