package com.vitalsport.profile.service;

import com.vitalsport.profile.configuration.ServiceTestConfiguration;
import com.vitalsport.profile.model.BodyId;
import com.vitalsport.profile.model.BodyInfo;
import com.vitalsport.profile.repository.BodyInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.testng.Assert.*;

@ContextConfiguration(classes = {ServiceTestConfiguration.class})
public class BasicBodyInfoServiceTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private BasicBodyInfoService basicBodyInfoService;

    @Autowired
    private BodyInfoRepository mockBodyInfoRepository;

    @Test
    public void bodyInfoIsSaved() {

        BodyId bodyId = mock(BodyId.class);
        BodyInfo bodyInfo = mock(BodyInfo.class);

        basicBodyInfoService.saveBodyInfo(bodyId, bodyInfo);

        verify(mockBodyInfoRepository, times(1)).save(bodyInfo);
    }

    @Test
    public void bodyInfoIsReturnedByBodyId() {
        BodyId bodyId = mock(BodyId.class);
        BodyInfo bodyInfo = mock(BodyInfo.class);

        when(mockBodyInfoRepository.findOne(bodyId)).thenReturn(bodyInfo);
        BodyInfo actualResult = basicBodyInfoService.getBodyInfo(bodyId);

        verify(mockBodyInfoRepository, times(1)).findOne(bodyId);
        assertThat(actualResult).isEqualToComparingFieldByField(bodyInfo);
    }

    @Test
    public void bodyInfoIsDeletedByBodyId() {
        BodyId bodyId = mock(BodyId.class);

        basicBodyInfoService.deleteBodyInfo(bodyId);

        verify(mockBodyInfoRepository, times(1)).delete(bodyId);
    }

}