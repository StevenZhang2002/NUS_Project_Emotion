package com.example.demo.Controller;


import cn.hutool.core.convert.Convert;
import cn.hutool.json.JSON;
import com.example.demo.DTO.MoodHistoryDTO;
import com.example.demo.Entity.Record;
import com.example.demo.Service.PointsService;
import com.example.demo.Service.RecordService;
import com.example.demo.Utils.Result;
import com.example.demo.Utils.ThreadLocalUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


/**
 *  function： 监听 接受python处理回来的结果
 *
 *  add record: 加分
 *
 *
 */

@RestController
@RequestMapping("/Record")
@Tag(name = "日志记录相关接口")
public class RecordController {
    @Autowired
    RecordService recordService;

    @Autowired
    private PointsService pointsService;

    @Operation(summary = "记录笔记")
    @PostMapping("/add")
    public Result addRecord(@Validated @RequestBody Record record) {
        Map<String, Object> claims = ThreadLocalUtil.get();
        Integer userId = (int)claims.get("id");
        record.setUserId(userId);
        recordService.addRecord(record);
        pointsService.addPoints(userId);
        return Result.success();
    }

    @Operation(summary = "获取心情历史记录")
    @GetMapping("/History")
    public Result getMoodHistory(@RequestParam int queryPeriod){
//        1：当天；2：本周；3：本月。
        Map<String, Object> claims = ThreadLocalUtil.get();
        Integer userId = Convert.toInt(claims.get("id"));
        List<MoodHistoryDTO> result = recordService.getMoodHistory(queryPeriod,userId);
        return Result.success(result);
    }

    @Operation(summary = "心情活跃度统计")
    @GetMapping("/Intensity")
    public Result getIntensity(@RequestParam int queryPeriod){
//        1: 本周；2：本月
        Map<String, Object> claims = ThreadLocalUtil.get();
        int userId = (int)claims.get("id");
        return Result.success(recordService.getRecordIntensity(queryPeriod,userId));
    }


    @Operation(summary = "获取最新记录")
    @GetMapping("/latest")
    public Result getLatestRecord(){
        Map<String, Object> claims = ThreadLocalUtil.get();
        int userId = (int)claims.get("id");
        Record record = recordService.getLatestRecord(userId);
        return Result.success(record);
    }
}
