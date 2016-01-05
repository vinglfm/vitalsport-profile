package com.vitalsport.profile.service.measurements;

import com.vitalsport.profile.common.MeasurementLengthUnit;
import com.vitalsport.profile.common.MeasurementWeightUnit;
import com.vitalsport.profile.model.Measurements;
import com.vitalsport.profile.repository.MeasurementRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class InfoMeasurementsService implements MeasurementsService {

    private MeasurementRepository measurementRepository;

    @Autowired
    public InfoMeasurementsService(MeasurementRepository measurementRepository) {
        this.measurementRepository = measurementRepository;
    }

    @Override
    public void init(String id) {
        if (id == null) {
            throw new IllegalArgumentException("id couldn't be null");
        }

        Measurements measurements = new Measurements();
        measurements.setUserId(id);
        //TODO: move to properties
        measurements.setLength(MeasurementLengthUnit.CENTIMETER);
        measurements.setWeight(MeasurementWeightUnit.KILOGRAM);

        log.info("Saving measurements for id = %s, body = %s", id, measurements);

        measurementRepository.save(measurements);
    }

    @Override
    public void update(String id, Measurements measurements) {
        if (id == null) {
            throw new IllegalArgumentException("id couldn't be null");
        }

        if (measurements == null) {
            throw new IllegalArgumentException("measurements couldn't be null");
        }

        measurements.setUserId(id);

        log.info("Saving measurements for id = %s, body = %s", id, measurements);
        //TODO: need to update here
        measurementRepository.save(measurements);
    }

    @Override
    public Measurements get(String id) {
        if (id == null) {
            throw new IllegalArgumentException("id couldn't be null");
        }

        log.info("Retrieving measurements for id = %s", id);
        return measurementRepository.findOne(id);
    }
}
