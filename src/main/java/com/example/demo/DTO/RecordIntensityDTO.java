package com.example.demo.DTO;

import lombok.Data;

import java.sql.Date;

@Data
public class RecordIntensityDTO {
    Date recordDate;
    int postCount;

}