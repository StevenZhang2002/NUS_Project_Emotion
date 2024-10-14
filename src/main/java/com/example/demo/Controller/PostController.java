package com.example.demo.Controller;

import com.example.demo.Entity.Post;
import com.example.demo.Service.PostService;
import com.example.demo.Utils.Result;
import com.example.demo.Entity.PageBean;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/post")
@Tag(name = "朋友圈相关接口")
public class PostController {

    @Autowired
    private PostService postService;

    //发布朋友圈内容
    @Operation(summary = "发布朋友圈内容")
    @PostMapping("/release")
    public Result addPost(@RequestBody Post post) {
        log.info("发布朋友圈内容：{}", post);
        postService.addPost(post);
        return Result.success();
    }

    //分页查询朋友圈内容
    @Operation(summary = "分页查询朋友圈内容")
    @GetMapping("/check")
    public Result page(@RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer pageSize) {
        //记录日志
        log.info("分页查询，参数：{},{}", page, pageSize);
        //调用业务层分页查询功能
        PageBean pageBean = postService.page(page, pageSize);
        //响应
        return Result.success(pageBean);
    }
}


