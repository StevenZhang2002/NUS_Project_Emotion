package com.example.demo.Impl;

import com.example.demo.DTO.IntensityDTO;
import com.example.demo.DTO.MoodHistoryDTO;
import com.example.demo.Entity.Record;
import com.example.demo.Mapper.RecordMapper;
import com.example.demo.Service.RecordService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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


    private static final String EXCHANGE = "points.exchange";

    private static final String ROUTING_KEY = "record.routingkey";


    @Override
    public List<MoodHistoryDTO> getMoodHistory(int type, int userId) {
        return recordMapper.getMoodHistory(type, userId);
    }

    @Override
    public List<IntensityDTO> getIntensity(int type, int userId) {

        return recordMapper.getIntensity(type,userId);
    }

    @Override
    public void addRecord(Record record) {
        recordMapper.addRecord(record.getUserId(),record.getTitle(),record.getContent());
        // 发送消息到 RabbitMQ
        rabbitTemplate.convertAndSend(EXCHANGE, ROUTING_KEY, record);

    }
}
