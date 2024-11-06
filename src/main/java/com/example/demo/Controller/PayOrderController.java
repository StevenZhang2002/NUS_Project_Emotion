package com.example.demo.Controller;

import cn.hutool.core.convert.Convert;
import com.example.demo.DTO.OrderDTO;
import com.example.demo.DTO.PointDTO;
import com.example.demo.DTO.PurchaseEvent;
import com.example.demo.Service.*;
import com.example.demo.Utils.Result;
import com.example.demo.Utils.ThreadLocalUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/pay")
@Tag(name = "支付相关接口")
public class PayOrderController {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private PayOrderService payOrderService;
    @Autowired
    private ProductService productService;
    @Autowired
    private PointsService pointsService;
    @Autowired
    private UserService userService;

    // 生成支付单并完成支付
    @Operation(summary = "生成支付单并扣减积分")
    @PostMapping("/create")
    public Result createPayOrder(@RequestParam Integer productId, @RequestParam Integer quantity, int addressId) throws IOException {
        Map<String, Object> claims = ThreadLocalUtil.get();
            Integer userId = Convert.toInt(claims.get("id"));
        log.info("生成支付单：用户ID={}, 商品ID={}, 商品数量={}, 地址Id={}", userId, productId, quantity, addressId);
        if (productService.getProductById(productId)!=null){
            if(productService.getProductById(productId).getStock()>=quantity){
                PointDTO pointDTO = pointsService.getPoints(userId);
                if(pointDTO.getPointsBalance()>=quantity*productService.getProductById(productId).getPointsCost()){
                    OrderDTO order = payOrderService.createPayOrder(userId, productId,quantity, addressId);
                    String targetEmail = userService.getUser(userId).getEmail();
                    applicationContext.publishEvent(new PurchaseEvent(userId,targetEmail,"嘿嘿"));
                    return Result.success(order);
                }
                return Result.error("No Enough Points");
            }
            return Result.error("Out of Stock");
        }
        return Result.error("Invalid Product");


    }
}

