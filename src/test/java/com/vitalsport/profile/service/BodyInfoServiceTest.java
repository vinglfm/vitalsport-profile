package com.vitalsport.profile.service;

import com.vitalsport.profile.configuration.ServiceTestConfiguration;
import com.vitalsport.profile.model.MeasurementId;
import com.vitalsport.profile.model.BodyInfo;
import com.vitalsport.profile.repository.MeasurementInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {ServiceTestConfiguration.class})
public class BodyInfoServiceTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private BodyInfoService basicBodyInfoService;

    @Autowired
    private MeasurementInfoRepository mockMeasurementInfoRepository;

    @Test
    public void bodyInfoIsSaved() {

        MeasurementId measurementId = mock(MeasurementId.class);
        BodyInfo bodyInfo = mock(BodyInfo.class);

        basicBodyInfoService.save(measurementId, bodyInfo);

        verify(mockMeasurementInfoRepository, times(1)).save(bodyInfo);
    }

    @Test
    public void bodyInfoIsReturnedByBodyId() {
        MeasurementId measurementId = mock(MeasurementId.class);
        BodyInfo bodyInfo = mock(BodyInfo.class);

        when(mockMeasurementInfoRepository.findOne(measurementId)).thenReturn(bodyInfo);
        BodyInfo actualResult = basicBodyInfoService.get(measurementId);

        verify(mockMeasurementInfoRepository, times(1)).findOne(measurementId);
        assertThat(actualResult).isEqualToComparingFieldByField(bodyInfo);
    }

    @Test
    public void bodyInfoIsDeletedByBodyId() {
        MeasurementId measurementId = mock(MeasurementId.class);

        basicBodyInfoService.delete(measurementId);

        verify(mockMeasurementInfoRepository, times(1)).delete(measurementId);
    }

}