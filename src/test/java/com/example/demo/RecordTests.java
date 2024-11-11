package com.example.demo;

import cn.hutool.json.JSONUtil;
import com.example.demo.Controller.RecordController;
import com.example.demo.DTO.MoodHistoryDTO;
import com.example.demo.DTO.RecordIntensityDTO;
import com.example.demo.DTO.RecordLatestDTO;
import com.example.demo.Entity.Record;
import com.example.demo.Service.PointsService;
import com.example.demo.Service.RecordService;
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

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
public class RecordTests {

    private MockMvc mockMvc;

    @Mock
    private RecordService recordService;

    @Mock
    private PointsService pointsService;

    @InjectMocks
    private RecordController recordController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(recordController).build();
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", 1);
        ThreadLocalUtil.set(claims);
    }

    @Test
    public void testAddRecord_Success() throws Exception {
        Record record = new Record();
        record.setTitle("Sample Title"); // 添加 title 字段以满足验证要求
        record.setContent("Test Content");
        doNothing().when(recordService).addRecord(any(Record.class));

        mockMvc.perform(post("/Record/add")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("title", "Sample Title") // 确保提供 title 参数
                        .param("content", "Test Content"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.message").value("Success"));
    }


    @Test
    public void testGetLatestRecord() throws Exception {
        Record record = new Record();
        record.setContent("Latest content");
        when(recordService.getLatestRecord(anyInt())).thenReturn(record);

        mockMvc.perform(get("/Record/latest"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.data.content").value("Latest content"));
    }
}
