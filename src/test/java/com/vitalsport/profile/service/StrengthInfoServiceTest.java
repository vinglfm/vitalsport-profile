package com.vitalsport.profile.service;

import com.vitalsport.profile.model.BodyInfo;
import com.vitalsport.profile.model.InfoId;
import com.vitalsport.profile.model.StrengthInfo;
import com.vitalsport.profile.repository.StrengthInfoRepository;
import com.vitalsport.profile.service.info.StrengthInfoService;
import org.assertj.core.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

public class StrengthInfoServiceTest extends BaseServiceTest {

    private StrengthInfoService strengthInfoService;

    private StrengthInfoRepository mockStrengthInfoRepository;

    @BeforeTest
    public void before() {
        mockStrengthInfoRepository = mock(StrengthInfoRepository.class);
        strengthInfoService = new StrengthInfoService(mockStrengthInfoRepository);
    }

    @Test
    public void strengthInfoIsSaved() {
        InfoId infoId = prepareInfoId(userId1, localDate1);
        StrengthInfo strengthInfo = prepareStrengthInfo(infoId);

        strengthInfoService.save(infoId, strengthInfo);

        verify(mockStrengthInfoRepository, times(1)).save(strengthInfo);
    }

    @Test
    public void strengthInfoIsReturnedByInfoId() {
        InfoId infoId = prepareInfoId(userId1, localDate1);
        StrengthInfo strengthInfo = prepareStrengthInfo(infoId);

        when(mockStrengthInfoRepository.findOne(infoId)).thenReturn(strengthInfo);
        StrengthInfo actualResult = strengthInfoService.get(infoId);

        verify(mockStrengthInfoRepository, times(1)).findOne(infoId);
        assertThat(actualResult).isEqualToComparingFieldByField(strengthInfo);
    }

    @Test
    public void latestBodyInfoIsReturnedByUserId() {
        prepareStrengthInfo(prepareInfoId(userId1, localDate1));
        StrengthInfo expectedResult = prepareStrengthInfo(prepareInfoId(userId1, localDate2));

        when(mockStrengthInfoRepository.findTopByIdUserIdOrderByIdDateDesc(userId1)).thenReturn(expectedResult);
        StrengthInfo actualResult = strengthInfoService.getLatest(userId1);

        verify(mockStrengthInfoRepository, times(1)).findTopByIdUserIdOrderByIdDateDesc(userId1);
        Assertions.assertThat(actualResult).isEqualToComparingFieldByField(expectedResult);
    }

    @Test
    public void strengthInfoIsDeletedByStrengthId() {
        InfoId infoId = prepareInfoId(userId1, localDate1);

        strengthInfoService.delete(infoId);

        verify(mockStrengthInfoRepository, times(1)).delete(infoId);
    }

    private StrengthInfo prepareStrengthInfo(InfoId infoId) {
        StrengthInfo strengthInfo = new StrengthInfo();
        strengthInfo.setId(infoId);
        strengthInfo.setBenchPress(100);
        strengthInfo.setLift(90);
        strengthInfo.setSquat(60);
        return strengthInfo;
    }

}