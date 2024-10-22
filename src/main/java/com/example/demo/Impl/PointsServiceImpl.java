package com.example.demo.Impl;

import com.example.demo.DTO.PointDTO;
import com.example.demo.DTO.PointsTransactionDTO;
import com.example.demo.Mapper.PointsMapper;
import com.example.demo.Service.PointsService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PointsServiceImpl implements PointsService {
    @Autowired
    PointsMapper pointsMapper;

    @Autowired
    private RabbitTemplate rabbitTemplate;


    private static final String EXCHANGE = "points.exchange";

    private static final String ROUTING_KEY = "record.routingkey";

    @Override
    public void initiateScore(int userId) {
        pointsMapper.initiatePoints(userId);
        PointsTransactionDTO transaction = new PointsTransactionDTO();
        transaction.setUserId(userId);
        transaction.setChangeAmount(800); // 消耗积分
        transaction.setTransactionType("Earn");
        transaction.setDescription("Record Reward");
        rabbitTemplate.convertAndSend(EXCHANGE, ROUTING_KEY, transaction);
    }

    @Override
    public void addPoints(int userId, int points) {
        PointDTO pointDTO = pointsMapper.selectPointDTOByUserId(userId);
        int updatedPoints = pointDTO.getPointsBalance()+points;
        pointsMapper.updatePointsBalance(userId,updatedPoints);
        PointsTransactionDTO transaction = new PointsTransactionDTO();
        transaction.setUserId(userId);
        transaction.setChangeAmount(points); // 消耗积分
        transaction.setTransactionType("Earn");
        transaction.setDescription("Record Reward");
        rabbitTemplate.convertAndSend(EXCHANGE, ROUTING_KEY, transaction);
    }
}
