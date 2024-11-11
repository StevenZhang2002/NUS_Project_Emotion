package com.example.demo;

import com.example.demo.Controller.PostController;
import com.example.demo.Entity.PageBean;
import com.example.demo.Entity.Post;
import com.example.demo.Service.PostService;
import com.example.demo.Utils.Result;
import com.example.demo.Utils.ThreadLocalUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PostTests {

    private MockMvc mockMvc;

    @Mock
    private PostService postService;

    @InjectMocks
    private PostController postController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(postController).build();

        // 模拟 ThreadLocalUtil.get() 返回的用户ID
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", 1);
        ThreadLocalUtil.set(claims);
    }

    @Test
    public void testAddPost() throws Exception {
        Post post = new Post();
        post.setContent("Test content");
        doNothing().when(postService).addPost(post);

        mockMvc.perform(post("/post/release")
                        .param("content", "Test content")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0));
    }

    @Test
    public void testPage() throws Exception {
        int page = 1;
        int pageSize = 10;
        PageBean pageBean = new PageBean(100L, null);
        when(postService.page(page, pageSize)).thenReturn(pageBean);

        mockMvc.perform(get("/post/check")
                        .param("page", String.valueOf(page))
                        .param("pageSize", String.valueOf(pageSize))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0));
    }
}
