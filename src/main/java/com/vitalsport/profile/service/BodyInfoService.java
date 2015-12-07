package com.vitalsport.profile.service;

import com.vitalsport.profile.model.BodyId;
import com.vitalsport.profile.model.BodyInfo;

import java.time.LocalDate;

public interface BodyInfoService {
    void saveBodyInfo(BodyId id, BodyInfo bodyInfo);

    BodyInfo getBodyInfo(BodyId id);

    void deleteBodyInfo(BodyId id);
}
