package com.example.demo.Service;

import com.example.demo.DTO.IntensityDTO;
import com.example.demo.DTO.MoodHistoryDTO;
import com.example.demo.Entity.Record;
import java.util.List;

public interface RecordService {

    public List<MoodHistoryDTO> getMoodHistory(int type,int userId);

//    public List<IntensityDTO>getIntensity(IntensityDTO intensityDTO);

    public void addRecord(Record record);
}
