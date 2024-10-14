package com.example.demo.Controller;


import com.example.demo.Entity.Friendship;
import com.example.demo.Service.FriendshipService;
import com.example.demo.Service.UserService;
import com.example.demo.Utils.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/friendship")
@Tag(name = "好友关系相关接口")
public class FriendshipController {
    @Autowired
    private FriendshipService friendshipService;
    @Autowired
    private UserService userService;

    @Operation(summary = "增加好友关系")
    @PostMapping("/BuildRelations")
    public Result BuildRelations(@RequestParam int userId1,@RequestParam int userId2){
        if(userService.getUser(userId1)!=null&&userService.getUser(userId2)!=null){
            if(friendshipService.getFriendshipByTwoUserId(userId1, userId2)==null){
                friendshipService.buildFriendship(userId1,userId2);
                return Result.success();
            }
            return Result.error("Relation Already Exist");
        }
        return Result.error("Invalid User ID");
    }

}
