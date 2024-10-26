package com.example.demo.Mapper;

import com.example.demo.DTO.MoodHistoryDTO;
import com.example.demo.DTO.RecordIntensityDTO;
import com.example.demo.Entity.Record;
import org.apache.http.conn.util.PublicSuffixList;
import org.apache.ibatis.annotations.*;

import java.sql.Timestamp;
import java.util.List;


@Mapper
public interface RecordMapper {

    @Insert("INSERT INTO record(userId, Title, Content) VALUES(#{userId}, #{title}, #{content})")
    @Options(useGeneratedKeys = true, keyProperty = "recordId")
    public void addRecord(Record record);

    List<RecordIntensityDTO>getRecordIntensity(int type,int userId);

    List<MoodHistoryDTO>getMoodHistory(int type, int userId);

    @Select("SELECT * FROM record WHERE userId = #{userId} ORDER BY createdAt DESC LIMIT 1")
    Record getLatestRecord(int userId);


    @Update("UPDATE record SET Mood = #{jsonData}, TopEmotion = #{topEmotion}, ComfortLanguage = #{comfortLanguage}, BehavioralGuidance = #{behavioralGuidance} WHERE recordId = #{recordId}")
    void setIntensity(String jsonData, String topEmotion,String comfortLanguage,String behavioralGuidance,int recordId);


    @Select("SELECT userId,recordId,Mood,Content,createdAt FROM record where userId = #{userId} AND createdAt>=#{start} AND createdAt<=#{end} ORDER BY createdAt DESC")
    List<MoodHistoryDTO>getHistoryByTimePeriod(int userId, Timestamp start, Timestamp end);

//
//    <select id="getMoodHistory" resultType="com.example.demo.DTO.MoodHistoryDTO">
//    select userId,recordId,Mood,Content,createdAt from record
//        <where>
//            <if test="type==1">
//    DATE(createdAt) = CURDATE()
//            </if>
//            <if test="type==2">
//    WEEK(createdAt) = WEEK(CURDATE()) AND YEAR(createdAt) = YEAR(CURDATE())
//            </if>
//            <if test="type==3">
//    MONTH(createdAt) = MONTH(CURDATE()) AND YEAR(createdAt) = YEAR(CURDATE())
//            </if>
//    AND userId = #{userId} ORDER BY createdAt ASC;
//        </where>
//    </select>

}
