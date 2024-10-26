package com.example.demo.Service;

import com.example.demo.DTO.MoodHistoryDTO;
import com.example.demo.DTO.RecordIntensityDTO;
import com.example.demo.Entity.Record;

import java.sql.Timestamp;
import java.util.List;

public interface RecordService {

    public List<MoodHistoryDTO> getMoodHistory(int type,int userId);

    public List<RecordIntensityDTO>getRecordIntensityByPeriod(int userId, Timestamp start, Timestamp end);

    public void addRecord(Record record);

    public List<RecordIntensityDTO> getRecordIntensity(int type, int userId);

    public Record getLatestRecord(int userId);

    public List<MoodHistoryDTO> getMoodHistoryTimeRange(int userId, Timestamp start, Timestamp end);
}
