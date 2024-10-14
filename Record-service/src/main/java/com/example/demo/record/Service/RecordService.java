package com.example.demo.record.Service;

import com.example.demo.common.DTO.IntensityDTO;
import com.example.demo.common.DTO.MoodHistoryDTO;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

public interface RecordService {

    public List<MoodHistoryDTO> getMoodHistory(int type,int userId);

    public List<IntensityDTO>getIntensity(int type, int userId);
}
