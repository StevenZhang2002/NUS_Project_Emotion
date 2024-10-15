package com.example.demo;

import com.example.demo.Entity.Post;
import com.example.demo.Service.PostService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PostTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private PostService postService;

    @Autowired
    private ObjectMapper objectMapper;

    // 测试成功发布朋友圈内容
    @Test
    public void testAddPost_Success() throws Exception {
        // 创建一个模拟的 Post 对象
        Post post = new Post();
        post.setUserId(1);
        post.setRecordId(4);
        post.setCreatedAt(new java.sql.Timestamp(System.currentTimeMillis()));

        // 模拟 service 行为
        mockMvc.perform(MockMvcRequestBuilders.post("/post/release")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJjbGFpbXMiOnsiaWQiOjUsImVtYWlsIjoiMTQ2ODc0MDMzMUBxcS5jb20ifSwiZXhwIjoxNzI5MDIzMjQ4fQ.OPmAOtW2k-k93G_inZOXxBkq8libNBUKJc4RfwP4B7I")
                        .content(objectMapper.writeValueAsString(post)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0)) // 假设成功返回的code为0
                .andExpect(jsonPath("$.message").value("Success"));
    }

    // 测试发布失败的情况，例如缺少必要的字段
    @Test
    public void testAddPost_Failure_MissingUserId() throws Exception {
        // 创建一个模拟的 Post 对象，故意不设置 userId
        Post post = new Post();
        post.setRecordId(4);
        post.setCreatedAt(new java.sql.Timestamp(System.currentTimeMillis()));

        // 模拟 POST 请求
        mockMvc.perform(MockMvcRequestBuilders.post("/post/release")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJjbGFpbXMiOnsiaWQiOjUsImVtYWlsIjoiMTQ2ODc0MDMzMUBxcS5jb20ifSwiZXhwIjoxNzI5MDIzMjQ4fQ.OPmAOtW2k-k93G_inZOXxBkq8libNBUKJc4RfwP4B7I")
                        .content(objectMapper.writeValueAsString(post)))
                .andExpect(status().isBadRequest()) // 假设缺少字段时返回 400 错误
                .andExpect(jsonPath("$.code").value(1)) // 假设失败返回的code为1
                .andExpect(jsonPath("$.message").value("UserId is required"));
    }

    // 测试分页查询朋友圈内容
    @Test
    public void testPagePosts_Success() throws Exception {
        // 模拟分页查询
        mockMvc.perform(MockMvcRequestBuilders.get("/post/check")
                        .param("page", "1")
                        .param("pageSize", "10")
                        .header("Authorization", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJjbGFpbXMiOnsiaWQiOjUsImVtYWlsIjoiMTQ2ODc0MDMzMUBxcS5jb20ifSwiZXhwIjoxNzI5MDIzMjQ4fQ.OPmAOtW2k-k93G_inZOXxBkq8libNBUKJc4RfwP4B7I"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0)) // 假设分页成功返回的code为0
                .andExpect(jsonPath("$.data").exists()) // 检查数据是否存在
                .andExpect(jsonPath("$.data.total").value(8)); // 假设返回的总数为目前数据量（小于100）大于100时设为100
    }
}
