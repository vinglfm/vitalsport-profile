package com.vitalsport.profile.service;

import com.vitalsport.profile.configuration.ServiceTestConfiguration;
import com.vitalsport.profile.model.MeasurementId;
import com.vitalsport.profile.model.StrengthInfo;
import com.vitalsport.profile.repository.InfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ContextConfiguration(classes = {ServiceTestConfiguration.class})
public class StrengthInfoServiceTest  extends AbstractTestNGSpringContextTests {
    @Autowired
    private StrengthInfoService strengthInfoService;

    @Autowired
    private InfoRepository mockInfoRepository;

    @Test
    public void strengthInfoIsSaved() {

        MeasurementId measurementId = mock(MeasurementId.class);
        StrengthInfo strengthInfo = mock(StrengthInfo.class);

        strengthInfoService.save(measurementId, strengthInfo);

        verify(mockInfoRepository, times(1)).save(strengthInfo);
    }

    @Test
    public void strengthInfoIsReturnedByStrengthId() {
        MeasurementId measurementId = mock(MeasurementId.class);
        StrengthInfo strengthInfo = mock(StrengthInfo.class);

        when(mockInfoRepository.findOne(measurementId)).thenReturn(strengthInfo);
        StrengthInfo actualResult = strengthInfoService.get(measurementId);

        verify(mockInfoRepository, times(1)).findOne(measurementId);
        assertThat(actualResult).isEqualToComparingFieldByField(strengthInfo);
    }

    @Test
    public void strengthInfoIsDeletedByStrengthId() {
        MeasurementId measurementId = mock(MeasurementId.class);

        strengthInfoService.delete(measurementId);

        verify(mockInfoRepository, times(1)).delete(measurementId);
    }
}