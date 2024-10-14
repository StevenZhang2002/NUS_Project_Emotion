package com.example.demo.friendship.Controller;

import com.example.demo.common.Entity.User;
import com.example.demo.friendship.Service.FriendshipService;
import com.example.demo.user.Service.UserService;
import com.example.demo.common.Utils.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/friendship")
@Tag(name = "好友关系相关接口")
@RequiredArgsConstructor
public class FriendshipController {

    private final FriendshipService friendshipService;

    private final UserService userService;

    @Operation(summary = "增加好友关系")
    @PostMapping("/BuildRelations")
    public Result BuildRelations(@RequestParam int userId1,@RequestParam int userId2){

        User user1 = userService.getUser(userId1);
        User user2 = userService.getUser(userId2);

        if(user1 != null && user2 != null){
            if(friendshipService.getFriendshipByTwoUserId(userId1, userId2) == null){
                friendshipService.buildFriendship(userId1,userId2);
                return Result.success();
            }
            return Result.error("Relation Already Exist");
        }
        return Result.error("Invalid User ID");
    }

}
