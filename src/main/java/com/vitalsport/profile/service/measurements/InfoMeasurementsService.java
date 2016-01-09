package com.vitalsport.profile.service.measurements;

import com.vitalsport.profile.common.LengthMeasurementUnit;
import com.vitalsport.profile.common.WeightMeasurementUnit;
import com.vitalsport.profile.model.Measurements;
import com.vitalsport.profile.repository.MeasurementRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class InfoMeasurementsService implements MeasurementsService {

    private MeasurementRepository measurementRepository;

    private LengthMeasurementUnit lengthUnit;
    private WeightMeasurementUnit weightUnit;


    @Autowired
    public InfoMeasurementsService(MeasurementRepository measurementRepository,
                                   @Value("${profile.measurement.length}") String lengthDefault,
                                   @Value("${profile.measurement.weight}") String weightDefault) {
        this.measurementRepository = measurementRepository;
        this.lengthUnit = LengthMeasurementUnit.fromCode(lengthDefault);
        this.weightUnit = WeightMeasurementUnit.fromCode(weightDefault);
    }

    @Override
    public void init(String id) {
        if (id == null) {
            throw new IllegalArgumentException("id couldn't be null");
        }

        Measurements measurements = new Measurements();
        measurements.setUserId(id);
        measurements.setLength(lengthUnit);
        measurements.setWeight(weightUnit);

        log.info("Saving measurements for id = {}, body = {}", id, measurements);

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

        log.info("Saving measurements for id = {}, body = {}", id, measurements);
        measurementRepository.save(measurements);
    }

    @Override
    public Measurements get(String id) {
        if (id == null) {
            throw new IllegalArgumentException("id couldn't be null");
        }

        log.info("Retrieving measurements for id = {}", id);
        return measurementRepository.findOne(id);
    }
}
