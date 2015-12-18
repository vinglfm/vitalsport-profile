package com.vitalsport.profile.service;

import com.vitalsport.profile.model.MeasurementId;
import com.vitalsport.profile.model.BodyInfo;

public interface MeasurementInfoService<T> {
    void save(MeasurementId id, T bodyInfo);

    T get(MeasurementId id);

    void delete(MeasurementId id);
}
