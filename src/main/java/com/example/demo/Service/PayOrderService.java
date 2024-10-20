package com.example.demo.Service;

import com.example.demo.DTO.OrderDTO;

public interface PayOrderService {

    OrderDTO createPayOrder(Integer userId, Integer productId, Integer quantity, int addressId);
}
