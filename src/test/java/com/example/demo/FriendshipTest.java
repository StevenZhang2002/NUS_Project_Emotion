package com.example.demo;

import com.example.demo.Entity.Friendship;
import com.example.demo.Entity.User;
import com.example.demo.Service.FriendshipService;
import com.example.demo.Service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class FriendshipTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private FriendshipService friendshipService;

    @Mock
    private UserService userService;

    @Test
    public void testBuildRelations_ValidUserIds() throws Exception {
        // 模拟 userService 返回有效用户
        when(userService.getUser(1)).thenReturn(new User());
        when(userService.getUser(2)).thenReturn(new User());

        // 模拟 friendshipService 返回 null，表示没有现有的好友关系
        when(friendshipService.getFriendshipByTwoUserId(1, 2)).thenReturn(null);

        // 模拟 POST 请求
        mockMvc.perform(MockMvcRequestBuilders.post("/friendship/BuildRelations")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .header("Authorization", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJjbGFpbXMiOnsiaWQiOjUsImVtYWlsIjoiMTQ2ODc0MDMzMUBxcS5jb20ifSwiZXhwIjoxNzI5MDIzMjQ4fQ.OPmAOtW2k-k93G_inZOXxBkq8libNBUKJc4RfwP4B7I")
                        .param("userId1", "1")
                        .param("userId2", "3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.message").value("Success"));
    }

    @Test
    public void testBuildRelations_InvalidUserIds() throws Exception {
        // 模拟 userService 返回 null，表示用户 ID 无效
        when(userService.getUser(1)).thenReturn(null);

        // 模拟 POST 请求
        mockMvc.perform(MockMvcRequestBuilders.post("/friendship/BuildRelations")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .header("Authorization", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJjbGFpbXMiOnsiaWQiOjUsImVtYWlsIjoiMTQ2ODc0MDMzMUBxcS5jb20ifSwiZXhwIjoxNzI5MDIzMjQ4fQ.OPmAOtW2k-k93G_inZOXxBkq8libNBUKJc4RfwP4B7I")
                        .param("userId1", "0")
                        .param("userId2", "2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1))
                .andExpect(jsonPath("$.message").value("Invalid User ID"));
    }

    @Test
    public void testExistingFriendship() throws Exception {
        // 模拟 userService 返回有效用户
        when(userService.getUser(1)).thenReturn(new User());
        when(userService.getUser(2)).thenReturn(new User());

        // 模拟已经存在的好友关系
        Friendship existingFriendship = new Friendship();
        existingFriendship.setUserId1(1);
        existingFriendship.setUserId2(2);
        when(friendshipService.getFriendshipByTwoUserId(1, 2)).thenReturn(existingFriendship);

        // 模拟 POST 请求
        mockMvc.perform(MockMvcRequestBuilders.post("/friendship/BuildRelations")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .header("Authorization", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJjbGFpbXMiOnsiaWQiOjUsImVtYWlsIjoiMTQ2ODc0MDMzMUBxcS5jb20ifSwiZXhwIjoxNzI5MDIzMjQ4fQ.OPmAOtW2k-k93G_inZOXxBkq8libNBUKJc4RfwP4B7I")
                        .param("userId1", "1")
                        .param("userId2", "2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1)) // 根据业务逻辑，关系已存在时应该返回错误码
                .andExpect(jsonPath("$.message").value("Relation Already Exist"));
    }

}
