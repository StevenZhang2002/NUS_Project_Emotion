package com.example.demo.Controller;


import com.example.demo.DTO.UserDTO;
import com.example.demo.Entity.User;
import com.example.demo.Service.FriendshipService;
import com.example.demo.Service.UserService;
import com.example.demo.Utils.Result;
import com.example.demo.Utils.ThreadLocalUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.View;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/friendship")
@Tag(name = "好友关系相关接口")
public class FriendshipController {
    @Autowired
    private FriendshipService friendshipService;
    @Autowired
    private UserService userService;
    @Autowired
    private View error;

    private int getUserByEmail(String email) {
        User user = userService.getUserByEmail(email);
        if (user != null) {
            return user.getUserId();
        }
        return -1;
    }

    @Operation(summary = "增加好友关系")
    @PostMapping("/BuildRelations")
    public Result BuildRelations(@RequestParam String email){
        Map<String, Object> claims = ThreadLocalUtil.get();
        Integer userId1 = (int)claims.get("id");
        Integer userId2 = getUserByEmail(email);
        if(userId1 != null && userService.getUser(userId2) != null && userId1 != userId2){
            if(friendshipService.getFriendshipByTwoUserId(userId1, userId2)==null){
                friendshipService.buildFriendship(userId1,userId2);
                return Result.success();
            }
            return Result.error("已经是好友");
        }
        return Result.error("非法用户ID");
    }

    // 查看好友列表功能
    @Operation(summary = "查看用户的好友列表")
    @GetMapping("/listFollowing")
    public Result listFollowing() {
        Map<String, Object> claims = ThreadLocalUtil.get();
        Integer userId = (int)claims.get("id");
            List<UserDTO> followingList = friendshipService.getFollowingList(userId);
            return Result.success(followingList);
    }
}
