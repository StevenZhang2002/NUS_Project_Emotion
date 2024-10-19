package com.example.demo.Controller;

import com.example.demo.Service.TransactionService;
import com.example.demo.Utils.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/transaction")
@Tag(name = "交易订单相关接口")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    // 创建积分交易记录
    @Operation(summary = "创建积分交易记录")
    @PostMapping("/create")
    public Result createTransaction(@RequestParam Integer userId,
                                    @RequestParam Integer changeAmount,
                                    @RequestParam String transactionType,
                                    @RequestParam String description) {
        log.info("创建交易记录：userId={}, changeAmount={}, transactionType={}", userId, changeAmount, transactionType);
        transactionService.createTransaction(userId, changeAmount, transactionType, description);
        return Result.success();
    }
}
