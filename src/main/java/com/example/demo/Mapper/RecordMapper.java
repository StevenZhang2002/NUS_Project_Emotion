package com.example.demo.Mapper;

import com.example.demo.DTO.MoodHistoryDTO;
import com.example.demo.DTO.RecordIntensityDTO;
import com.example.demo.Entity.Record;
import org.apache.http.conn.util.PublicSuffixList;
import org.apache.ibatis.annotations.*;
import java.util.List;


@Mapper
public interface RecordMapper {

    @Insert("INSERT INTO record(userId, Content) VALUES(#{userId}, #{content})")
    @Options(useGeneratedKeys = true, keyProperty = "recordId")
    public void addRecord(Record record);

    List<RecordIntensityDTO>getRecordIntensity(int type,int userId);

    List<MoodHistoryDTO>getMoodHistory(int type, int userId);

    @Select("SELECT * FROM record WHERE userId = #{userId} ORDER BY createdAt DESC LIMIT 1")
    Record getLatestRecord(int userId);


    @Update("UPDATE record SET Mood = #{jsonData}, TopEmotion = #{topEmotion}, ComfortLanguage = #{comfortLanguage}, BehavioralGuidance = #{behavioralGuidance} WHERE recordId = #{recordId}")
    void setIntensity(String jsonData, String topEmotion,String comfortLanguage,String behavioralGuidance,int recordId);

}
