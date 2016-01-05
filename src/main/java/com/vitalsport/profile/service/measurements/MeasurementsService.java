package com.vitalsport.profile.service.measurements;

import com.vitalsport.profile.model.Measurements;

public interface MeasurementsService {

    void init(String id);

    void update(String id, Measurements measurements);

    Measurements get(String id);
}
