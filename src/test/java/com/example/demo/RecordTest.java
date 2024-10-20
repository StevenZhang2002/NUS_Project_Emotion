package com.example.demo;

import com.example.demo.DTO.MoodHistoryDTO;
import com.example.demo.DTO.IntensityDTO;
import com.example.demo.Service.RecordService;
import com.example.demo.Utils.ThreadLocalUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class RecordTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private RecordService recordService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        // 模拟 ThreadLocal 中的用户数据
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", 5); // 模拟用户ID
        ThreadLocalUtil.set(claims);
    }

    @Test
    public void testGetMoodHistory_Success() throws Exception {
        // 模拟一个带有实际数据的 MoodHistoryDTO
        MoodHistoryDTO mockMood = new MoodHistoryDTO();
        mockMood.setUserId(5);
        mockMood.setRecordId(5);
        mockMood.setMood(5); // 设置心情值
        mockMood.setCreatedAt(new Timestamp(System.currentTimeMillis())); // 设置当前时间戳

        List<MoodHistoryDTO> mockMoodHistory = List.of(mockMood);
        when(recordService.getMoodHistory(3, 5)).thenReturn(mockMoodHistory);

//        List<MoodHistoryDTO> result = recordService.getMoodHistory(3, 4);
//        System.out.println(result); // 打印结果，检查是否为空

        // 模拟 GET 请求
        mockMvc.perform(MockMvcRequestBuilders.get("/Record/History")
                        .param("queryPeriod", "3")
//                        .param("userId","4")
//                        .param("recordId","4")
                        .header("Authorization", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJjbGFpbXMiOnsiaWQiOjUsImVtYWlsIjoiMTQ2ODc0MDMzMUBxcS5jb20ifSwiZXhwIjoxNzI5NDczNTU0fQ.Nmo4JTME8nbUcDttYDwZ2TKGOG2auQGyCcLCZqXdUkw"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0)) // 假设成功的 code 为 0
                .andExpect(jsonPath("$.data").isArray()) // 检查 data 是否是数组
                .andExpect(jsonPath("$.data[0].userId").value(5)) // 检查 userId 是否正确
                .andExpect(jsonPath("$.data[0].mood").value(5)) // 检查 mood 值
                .andExpect(jsonPath("$.data[0].createdAt").exists()); // 检查 createdAt 是否存在
    }

    @Test
    public void testGetIntensity_Success() throws Exception {
        // 模拟一个带有实际数据的 IntensityDTO
        IntensityDTO mockIntensity = new IntensityDTO();
        mockIntensity.setDate(new java.sql.Date(System.currentTimeMillis())); // 设置当前日期
        mockIntensity.setPostCount(1); // 设置 post 数量

        List<IntensityDTO> mockIntensityList = List.of(mockIntensity);
        when(recordService.getIntensity(3, 5)).thenReturn(mockIntensityList);

        // 模拟 GET 请求
        mockMvc.perform(MockMvcRequestBuilders.get("/Record/Intensity/")
                        .param("queryPeriod", "3")
                        .header("Authorization", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJjbGFpbXMiOnsiaWQiOjUsImVtYWlsIjoiMTQ2ODc0MDMzMUBxcS5jb20ifSwiZXhwIjoxNzI5NDczNTU0fQ.Nmo4JTME8nbUcDttYDwZ2TKGOG2auQGyCcLCZqXdUkw"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0)) // 假设成功的 code 为 0
                .andExpect(jsonPath("$.data").isArray()) // 检查 data 是否是数组
                .andExpect(jsonPath("$.data[0].date").exists()) // 检查 date 是否存在
                .andExpect(jsonPath("$.data[0].postCount").value(1)); // 检查 postCount 是否正确
    }
}
