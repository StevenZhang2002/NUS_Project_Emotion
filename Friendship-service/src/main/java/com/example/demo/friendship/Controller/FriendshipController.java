package com.example.demo.friendship.Controller;

import com.example.demo.client.UserClient;
import com.example.demo.friendship.Service.FriendshipService;
import com.example.demo.common.Utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/friendship")
@Api(tags = "好友关系相关接口")
@RequiredArgsConstructor
public class FriendshipController {

    private final FriendshipService friendshipService;

    private final UserClient userClient;

    @ApiOperation("增加好友关系")
    @PostMapping("/BuildRelations")
    public Result BuildRelations(@RequestParam int userId1,@RequestParam int userId2){
        if(userClient.getUser(userId1)!=null&&userClient.getUser(userId2)!=null){
            if(friendshipService.getFriendshipByTwoUserId(userId1, userId2)==null){
                friendshipService.buildFriendship(userId1,userId2);
                return Result.success();
            }
            return Result.error("Relation Already Exist");
        }
        return Result.error("Invalid User ID");
    }

}
