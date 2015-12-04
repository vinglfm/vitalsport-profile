package com.vitalsport.profile.service;

import com.vitalsport.profile.model.BodyInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Slf4j
@Service
public class BasicBodyInfoService implements BodyInfoService {

    @Override
    public void saveBodyInfo(String userId, BodyInfo bodyInfo) {
        log.info("Saving bodyInfo for userId = %s, body = %s", userId, bodyInfo);
    }

    @Override
    public void updateBodyInfo(String userId, LocalDate measurementDate) {
        log.info("Updating bodyInfo for userId = %s, measurementDate = %s", userId, measurementDate);
    }

    @Override
    public BodyInfo getBodyInfo(String userId, LocalDate measurementDate) {
        log.info("Retrieving bodyInfo for userId = %s, measurementDate = %s", userId, measurementDate);
        return null;
    }

    @Override
    public void deleteBodyInfo(String userId, LocalDate measurementDate) {
        log.info("Deleting bodyInfo for userId = %s, measurementDate = %s", userId, measurementDate);
    }
}
