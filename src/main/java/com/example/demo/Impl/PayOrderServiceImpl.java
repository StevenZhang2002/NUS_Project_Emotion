package com.example.demo.Impl;

import com.example.demo.DTO.OrderDTO;
import com.example.demo.DTO.PointDTO;
import com.example.demo.DTO.ProductDTO;
import com.example.demo.Mapper.PayOrderMapper;
import com.example.demo.Mapper.PointsMapper;
import com.example.demo.Mapper.ProductsMapper;
import com.example.demo.Service.PayOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;

@Slf4j
@Service
public class PayOrderServiceImpl implements PayOrderService {

    @Autowired
    private PointsMapper pointsMapper;

    @Autowired
    private ProductsMapper productsMapper;

    @Autowired
    private PayOrderMapper payOrderMapper;

    @Transactional
    public OrderDTO createPayOrder(Integer userId, Integer productId, Integer quantity) {
        // 1. 查询商品信息
        ProductDTO product = productsMapper.selectProductDTOById(productId);
        if (product == null) {
            throw new RuntimeException("商品不存在");
        } else if (product.getStock() < quantity) {
            throw new RuntimeException("商品库存不足");
        }

        // 2.. 查询用户积分信息
        PointDTO userPoints = pointsMapper.selectPointDTOByUserId(userId);
        if (userPoints == null || userPoints.getPointsBalance() < (product.getPointsCost())*quantity) {
            throw new RuntimeException("用户积分不足");
        }

        //3.扣减商品库存
        int productsStock = product.getStock() - quantity;
        productsMapper.setProductsStock(productsStock,productId);

        // 4. 扣减用户积分
        userPoints.setPointsBalance(userPoints.getPointsBalance() - (product.getPointsCost())*quantity);
        int userPointsBalance = userPoints.getPointsBalance();
        pointsMapper.updatePointsBalance(userPointsBalance,userId);

        // 5. 创建订单
        OrderDTO order = new OrderDTO();
        order.setUserId(userId);
        order.setProductId(productId);
        order.setQuantity(quantity);
        order.setTotalPoints((product.getPointsCost())*quantity);
        order.setCreatedAt(new Timestamp(System.currentTimeMillis()));

        // 6. 保存订单
        payOrderMapper.insertOrder(order);

        return order;  // 返回生成的订单信息
        }
}
