package com.vitalsport.profile.service;

import com.vitalsport.profile.model.Measurements;
import com.vitalsport.profile.repository.MeasurementRepository;
import com.vitalsport.profile.service.measurements.InfoMeasurementsService;
import com.vitalsport.profile.service.measurements.MeasurementsService;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static com.vitalsport.profile.common.LengthMeasurementUnit.CENTIMETER;
import static com.vitalsport.profile.common.LengthMeasurementUnit.INCH;
import static com.vitalsport.profile.common.WeightMeasurementUnit.KILOGRAM;
import static com.vitalsport.profile.common.WeightMeasurementUnit.POUND;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class MeasurementServiceTest extends BaseServiceTest {

    private String lengthDefault = CENTIMETER.getLabel();
    private String weightDefault = KILOGRAM.getLabel();

    private MeasurementRepository mockMeasurementRepository;

    private MeasurementsService measurementsService;

    @BeforeTest
    public void before() {
        mockMeasurementRepository = mock(MeasurementRepository.class);
        measurementsService = new InfoMeasurementsService(mockMeasurementRepository, lengthDefault, weightDefault);
    }

    @Test
    public void measurementsInfoIsSaved() {

        Measurements measurements = prepareMeasurements(userId1);

        measurementsService.update(userId1, measurements);

        verify(mockMeasurementRepository, times(1)).save(measurements);
    }

    @Test
    public void measurementsInfoIsReturnedByUserId() {
        Measurements measurements = prepareMeasurements(userId1);

        when(mockMeasurementRepository.findOne(userId1)).thenReturn(measurements);
        Measurements actualResult = measurementsService.get(userId1);

        verify(mockMeasurementRepository, times(1)).findOne(userId1);
        assertThat(actualResult).isEqualToComparingFieldByField(measurements);
    }

    private Measurements prepareMeasurements(String userId) {
        Measurements measurements = new Measurements();
        measurements.setUserId(userId);
        measurements.setLength(INCH);
        measurements.setWeight(POUND);
        return measurements;
    }

}
