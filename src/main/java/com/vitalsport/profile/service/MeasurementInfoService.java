package com.vitalsport.profile.service;

import com.vitalsport.profile.model.MeasurementId;
import com.vitalsport.profile.model.BodyInfo;

public interface MeasurementInfoService<K, D> {
    void save(K id, D bodyInfo);

    D get(K id);

    void delete(K id);
}
