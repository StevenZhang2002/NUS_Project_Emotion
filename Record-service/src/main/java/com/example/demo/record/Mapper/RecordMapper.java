package com.example.demo.record.Mapper;

import com.example.demo.common.DTO.IntensityDTO;
import com.example.demo.common.DTO.MoodHistoryDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@Mapper
public interface RecordMapper {

    List<MoodHistoryDTO>getMoodHistory(int type, int userId);

    List<IntensityDTO>getIntensity(int type, int userId);
}
