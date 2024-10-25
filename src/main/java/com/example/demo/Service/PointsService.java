package com.example.demo.Service;

import com.example.demo.DTO.PointDTO;

public interface PointsService {
    public void initiateScore(int userId);

    public void addPoints(int userId);

    public PointDTO getPoints(int userId);



}
