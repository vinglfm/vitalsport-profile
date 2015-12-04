package com.vitalsport.profile.service;

import com.vitalsport.profile.model.BodyInfo;

import java.time.LocalDate;

public interface BodyInfoService {
    void saveBodyInfo(String userId, BodyInfo bodyInfo);

    void updateBodyInfo(String userId, LocalDate measurementDate);

    BodyInfo getBodyInfo(String userId, LocalDate measurementDate);

    void deleteBodyInfo(String userId, LocalDate measurementDate);
}
