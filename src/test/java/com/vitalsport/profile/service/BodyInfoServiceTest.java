package com.vitalsport.profile.service;

import com.vitalsport.profile.model.InfoId;
import com.vitalsport.profile.model.BodyInfo;
import com.vitalsport.profile.model.Measurements;
import com.vitalsport.profile.repository.BodyInfoRepository;
import com.vitalsport.profile.repository.MeasurementRepository;
import com.vitalsport.profile.service.info.BodyInfoService;
import com.vitalsport.profile.service.measurements.InfoMeasurementsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class BodyInfoServiceTest extends BaseServiceTest {

    @Autowired
    private BodyInfoService bodyInfoService;

    @Autowired
    private BodyInfoRepository mockBodyInfoRepository;

    @Autowired
    private MeasurementRepository mockMeasurementRepository;

    @BeforeTest
    public void before() {
        mockMeasurementRepository = mock(MeasurementRepository.class);
        mockBodyInfoRepository = mock(BodyInfoRepository.class);
        bodyInfoService = new BodyInfoService(mockBodyInfoRepository,
                new InfoMeasurementsService(mockMeasurementRepository));
    }

    @Test
    public void bodyInfoIsSaved() {

        InfoId infoId = prepareInfoId(localDate, userId);
        BodyInfo bodyInfo = prepareBodyInfo(infoId);

        bodyInfoService.save(infoId, bodyInfo);

        verify(mockBodyInfoRepository, times(1)).save(bodyInfo);
        //TODO: test saving concrete measurements
        verify(mockMeasurementRepository, times(1)).save(any(Measurements.class));
    }

    @Test
    public void bodyInfoIsReturnedByInfoId() {
        InfoId infoId = prepareInfoId(localDate, userId);
        BodyInfo bodyInfo = prepareBodyInfo(infoId);

        when(mockBodyInfoRepository.findOne(infoId)).thenReturn(bodyInfo);
        BodyInfo actualResult = bodyInfoService.get(infoId);

        verify(mockBodyInfoRepository, times(1)).findOne(infoId);

        assertThat(actualResult).isEqualToComparingFieldByField(bodyInfo);
    }

    @Test
    public void bodyInfoIsDeletedByBodyId() {
        InfoId infoId = prepareInfoId(localDate, userId);

        bodyInfoService.delete(infoId);

        verify(mockBodyInfoRepository, times(1)).delete(infoId);
    }

    private BodyInfo prepareBodyInfo(InfoId infoId) {
        BodyInfo bodyInfo = new BodyInfo();
        bodyInfo.setId(infoId);
        bodyInfo.setBiceps(37);
        bodyInfo.setChest(60);
        bodyInfo.setFatPercentage(18);
        return bodyInfo;
    }
}