package com.example.demo.Impl;

import cn.hutool.json.JSONUtil;
import com.example.demo.Config.AppConfig;
import com.example.demo.DTO.IntensityDTO;
import com.example.demo.DTO.RecordIntensityDTO;
import com.example.demo.DTO.MoodHistoryDTO;
import com.example.demo.DTO.SentimentResponse;
import com.example.demo.Entity.Record;
import com.example.demo.Mapper.RecordMapper;
import com.example.demo.Service.RecordService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RecordServiceImpl implements RecordService{

    @Autowired
    RecordMapper recordMapper;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private RestTemplate restTemplate;


    private static final String EXCHANGE = "points.exchange";

    private static final String ROUTING_KEY = "record.routingkey";

    String flaskUrl = "http://localhost:5000/sentimentAnalysis";



    @Override
    public List<MoodHistoryDTO> getMoodHistory(int type, int userId) {
        List<MoodHistoryDTO>list = recordMapper.getMoodHistory(type, userId);
        for(MoodHistoryDTO dto:list){
            dto.setMoodJson(JSONUtil.parse(dto.getMood()));
        }
        return list;
    }

//    @RabbitListener(queues = "callback.queue")
//    @Transactional
//    public void getIntensity(IntensityDTO intensityDTO) {
//        // intensityDTO 将自动从消息中反序列化
//        String jsonData = JSONUtil.toJsonStr(intensityDTO.getData());
//        jsonData = jsonData.substring(1, jsonData.length() - 1);
//        int recordId = intensityDTO.getRecord_Id();
//        String topEmotion = intensityDTO.getTop_emotion();
//        String comfortLanguage = intensityDTO.getComfort_language();
//        String behavioralGuidance = intensityDTO.getBehavioral_guidance();
//        // 更新到数据库
//        recordMapper.setIntensity(jsonData, topEmotion, comfortLanguage, behavioralGuidance, recordId);
//    }

//    @Override
//    public void addRecord(Record record) {
//        recordMapper.addRecord(record);
//        // 发送消息到 RabbitMQ
//        rabbitTemplate.convertAndSend(EXCHANGE, ROUTING_KEY, record);
//    }


    @Override
    public void addRecord(Record record) {
        HttpHeaders httpHeaders = new HttpHeaders();
        // 设置请求体
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("content", record.getContent());

        HttpEntity<Map<String, String>> request = new HttpEntity<>(requestBody, httpHeaders);
        // 发送请求并接收响应
        ResponseEntity<SentimentResponse> responseBack = restTemplate.postForEntity(flaskUrl, request, SentimentResponse.class);
        SentimentResponse sentimentResponse = responseBack.getBody();
        record.setTopEmotion(sentimentResponse.getTopEmotion());
        record.setComfortLanguage(sentimentResponse.getComfortLanguage());
        record.setMood(sentimentResponse.getData());
        record.setBehavioralGuidance(sentimentResponse.getBehavioralGuidance());
        record.setMoodJson(JSONUtil.parse(sentimentResponse.getData()));
        recordMapper.addRecordAll(record);
    }



    @Override
    public List<RecordIntensityDTO> getRecordIntensity(int type, int userId) {
        return recordMapper.getRecordIntensity(type,userId);
    }

    @Override
    public Record getLatestRecord(int userId) {
        return recordMapper.getLatestRecord(userId);
    }

    @Override
    public List<MoodHistoryDTO> getMoodHistoryTimeRange(int userId, Timestamp start, Timestamp end) {

        List<MoodHistoryDTO>res = recordMapper.getHistoryByTimePeriod(userId,start,end);
        for(MoodHistoryDTO dto:res){
            dto.setMoodJson(JSONUtil.parse(dto.getMood()));
        }
        return res;

    }
}
