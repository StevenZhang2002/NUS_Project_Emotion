package com.example.demo.Impl;

import com.example.demo.DTO.PointsTransactionDTO;
import com.example.demo.Entity.PageBean;
import com.example.demo.Entity.PointsTransaction;
import com.example.demo.Entity.Post;
import com.example.demo.Mapper.PointsMapper;
import com.example.demo.Mapper.TransactionMapper;
import com.example.demo.Service.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private PointsMapper pointsMapper;

    @Autowired
    private TransactionMapper transactionMapper;

    @RabbitListener(queues = "points.queue")
    @Transactional
    public void receiveTransaction(PointsTransactionDTO transaction) {
        log.info("Received message from RabbitMQ: {}", transaction);

        // 创建交易记录
        createTransaction(transaction.getUserId(), transaction.getChangeAmount(),
                transaction.getTransactionType(), transaction.getDescription());
    }

    @Transactional
    public void createTransaction(Integer userId, Integer changeAmount, String transactionType, String description) {
        // 1. 创建交易记录
        PointsTransactionDTO transaction = new PointsTransactionDTO();
        transaction.setUserId(userId);
        transaction.setChangeAmount(changeAmount);
        transaction.setTransactionType(transactionType);
        transaction.setDescription(description);

        // 2. 保存交易记录
        transactionMapper.insertTransaction(transaction);
    }

    @Override
    public PageBean page(int userId, Integer page, Integer pageSize) {
        //1、获取总记录数
        Long count = transactionMapper.count();

        //2、获取分页查询结果列表
        Integer start = (page - 1) * pageSize; //计算起始索引 , 公式: (页码-1)*页大小
        List<PointsTransactionDTO> empList = transactionMapper.list(userId,start, pageSize);

        //3、封装PageBean对象
        PageBean pageBean = new PageBean(count , empList);
        return pageBean;
    }



}

