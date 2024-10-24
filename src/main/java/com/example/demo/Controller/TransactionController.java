package com.example.demo.Controller;

import com.example.demo.Entity.PageBean;
import com.example.demo.Service.TransactionService;
import com.example.demo.Utils.Result;
import com.example.demo.Utils.ThreadLocalUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/transaction")
@Tag(name = "交易订单相关接口")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @Operation(summary = "获取积分交易记录")
    @GetMapping("/history")
    public Result getTransactionHistory(@RequestParam(defaultValue = "1") Integer page,
                                        @RequestParam(defaultValue = "10") Integer pageSize){
        Map<String, Object>claims = ThreadLocalUtil.get();
        int userId = (int)claims.get("id");
        PageBean pageBean = transactionService.page(userId,page, pageSize);
        //响应
        return Result.success(pageBean);

    }
}
