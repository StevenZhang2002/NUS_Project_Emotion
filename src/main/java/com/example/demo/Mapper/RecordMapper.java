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



    @Insert("INSERT INTO record(userId, Title, Content,BehavioralGuidance, ComfortLanguage, TopEmotion,Mood) VALUES(#{userId}, #{title}, #{content},#{behavioralGuidance}, #{comfortLanguage},#{topEmotion},#{mood})")
    public void addRecordAll(Record record);


    @Select("SELECT createdAt AS date, COUNT(*) AS post_count FROM record WHERE userId=#{userId} AND date>=start AND date<=end  ")
    public List<RecordIntensityDTO>getRecordIntensityByPeriod(int userId, Timestamp start, Timestamp end);


//    <select id="getRecordIntensity" resultType="com.example.demo.DTO.RecordIntensityDTO">
//        SELECT DATE(createdAt) AS date, COUNT(*) AS post_count
//        FROM record
//        <where>
//        userId = #{userId}
//        <if test="type==1">
//        AND WEEK(createdAt) = WEEK(CURDATE()) AND YEAR(createdAt) = YEAR(CURDATE())
//        </if>
//        <if test="type==2">
//            AND MONTH(createdAt) = MONTH(CURDATE()) AND YEAR(createdAt) = YEAR(CURDATE())
//        </if>
//        </where>
//        GROUP BY DATE(createdAt) ORDER BY DATE(createdAt);
//    </select>



}
