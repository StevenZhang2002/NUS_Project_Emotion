package com.example.demo.Service;

import com.example.demo.DTO.MoodHistoryDTO;
import com.example.demo.DTO.RecordIntensityDTO;
import com.example.demo.Entity.Record;
import java.util.List;

public interface RecordService {

    public List<MoodHistoryDTO> getMoodHistory(int type,int userId);

    public void addRecord(Record record);

    public List<RecordIntensityDTO> getRecordIntensity(int type, int userId);

    public Record getLatestRecord(int userId);
}
