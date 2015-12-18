package com.vitalsport.profile.repository;

import com.vitalsport.profile.model.MeasurementId;
import com.vitalsport.profile.model.BodyInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeasurementInfoRepository<T> extends JpaRepository<T, MeasurementId> {
}
