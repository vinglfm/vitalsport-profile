package com.vitalsport.profile.repository;

import com.vitalsport.profile.model.Measurements;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeasurementRepository extends JpaRepository<Measurements, String> {
}
