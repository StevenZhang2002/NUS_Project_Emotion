package com.example.demo.Controller;

import cn.hutool.core.convert.Convert;
import com.example.demo.DTO.OrderDTO;
import com.example.demo.Service.PayOrderService;
import com.example.demo.Utils.Result;
import com.example.demo.Utils.ThreadLocalUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import cn.hutool.core.convert.Convert;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/pay")
@Tag(name = "支付相关接口")
public class PayOrderController {

    @Autowired
    private PayOrderService payOrderService;

    // 生成支付单并完成支付
    @Operation(summary = "生成支付单并扣减积分")
    @PostMapping("/create")
    public Result createPayOrder(@RequestParam Integer productId, @RequestParam Integer quantity) {
        Map<String, Object> claims = ThreadLocalUtil.get();
            Integer userId = Convert.toInt(claims.get("id"));
        log.info("生成支付单：用户ID={}, 商品ID={}, 商品数量={}", userId, productId, quantity);
        OrderDTO order = payOrderService.createPayOrder(userId, productId,quantity);
        return Result.success(order);
    }
}

