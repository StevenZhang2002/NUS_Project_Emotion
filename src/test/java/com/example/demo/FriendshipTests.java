package com.example.demo;

import com.example.demo.Controller.FriendshipController;
import com.example.demo.DTO.UserDTO;
import com.example.demo.Entity.Friendship;
import com.example.demo.Entity.User;
import com.example.demo.Service.FriendshipService;
import com.example.demo.Service.UserService;
import com.example.demo.Utils.Result;
import com.example.demo.Utils.ThreadLocalUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
public class FriendshipTests {

    private MockMvc mockMvc;

    @Mock
    private FriendshipService friendshipService;

    @Mock
    private UserService userService;

    @InjectMocks
    private FriendshipController friendshipController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(friendshipController).build();
        // 模拟 ThreadLocalUtil.get() 返回的用户ID
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", 1);
        ThreadLocalUtil.set(claims);
    }

    @Test
    public void testBuildRelations_Success() throws Exception {
        // 模拟userService返回有效用户
        User user = new User();
        user.setUserId(2);
        when(userService.getUserByEmail("friend@example.com")).thenReturn(user);
        when(userService.getUser(2)).thenReturn(user);
        when(friendshipService.getFriendshipByTwoUserId(1, 2)).thenReturn(null);

        mockMvc.perform(post("/friendship/BuildRelations")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("email", "friend@example.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.message").value("Success"));
    }

    @Test
    public void testBuildRelations_ExistingFriendship() throws Exception {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", 1);
        ThreadLocalUtil.set(claims);

        User user = new User();
        user.setUserId(2);
        when(userService.getUserByEmail("friend@example.com")).thenReturn(user);
        when(userService.getUser(2)).thenReturn(user);

        Friendship existingFriendship = new Friendship();
        existingFriendship.setUserId1(1);
        existingFriendship.setUserId2(2);
        when(friendshipService.getFriendshipByTwoUserId(1, 2)).thenReturn(existingFriendship);

        mockMvc.perform(post("/friendship/BuildRelations")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("email", "friend@example.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1))
                .andExpect(jsonPath("$.message").value("已经是好友"));
    }


    @Test
    public void testBuildRelations_InvalidUserId() throws Exception {
        when(userService.getUserByEmail("invalid@example.com")).thenReturn(null);

        mockMvc.perform(post("/friendship/BuildRelations")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("email", "invalid@example.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1))
                .andExpect(jsonPath("$.message").value("非法用户ID"));
    }

    @Test
    public void testListFollowing() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("friend");
        userDTO.setEmail("friend@example.com");
        when(friendshipService.getFollowingList(1)).thenReturn(Collections.singletonList(userDTO));

        mockMvc.perform(get("/friendship/listFollowing"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.data[0].username").value("friend"))
                .andExpect(jsonPath("$.data[0].email").value("friend@example.com"));
    }
}
