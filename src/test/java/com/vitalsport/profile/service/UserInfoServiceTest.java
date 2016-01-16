package com.vitalsport.profile.service;

import com.vitalsport.profile.common.LengthMeasurementUnit;
import com.vitalsport.profile.common.WeightMeasurementUnit;
import com.vitalsport.profile.model.Measurements;
import com.vitalsport.profile.model.UserInfo;
import com.vitalsport.profile.repository.MeasurementRepository;
import com.vitalsport.profile.repository.UserInfoRepository;
import com.vitalsport.profile.service.info.UserInfoService;
import com.vitalsport.profile.service.measurements.InfoMeasurementsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static com.vitalsport.profile.common.LengthMeasurementUnit.CENTIMETER;
import static com.vitalsport.profile.common.WeightMeasurementUnit.KILOGRAM;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

public class UserInfoServiceTest extends BaseServiceTest {

    private String lengthDefault = CENTIMETER.getLabel();
    private String weightDefault = KILOGRAM.getLabel();

    private UserInfoService userInfoService;

    private UserInfoRepository mockUserInfoRepository;

    private MeasurementRepository mockMeasurementRepository;

    @BeforeTest
    public void before() {
        mockUserInfoRepository = mock(UserInfoRepository.class);
        mockMeasurementRepository = mock(MeasurementRepository.class);

        userInfoService = new UserInfoService(mockUserInfoRepository,
                new InfoMeasurementsService(mockMeasurementRepository, lengthDefault, weightDefault));
    }

    @Test
    public void userInfoIsSaved() {

        UserInfo userInfo = prepareUser(userId1);
        Measurements measurements = prepareMeasurements(userId1);

        userInfoService.save(userId1, userInfo);

        verify(mockUserInfoRepository, times(1)).save(userInfo);
        verify(mockMeasurementRepository, times(1)).save(measurements);
    }

    @Test
    public void userInfoIsReturnedByUserId() {
        UserInfo userInfo = prepareUser(userId1);

        when(mockUserInfoRepository.findOne(userId1)).thenReturn(userInfo);
        UserInfo actualResult = userInfoService.get(userId1);

        verify(mockUserInfoRepository, times(1)).findOne(userId1);
        assertThat(actualResult).isEqualToComparingFieldByField(userInfo);
    }

    @Test
    public void userInfoIsDeletedByStrengthId() {

        userInfoService.delete(userId1);

        verify(mockUserInfoRepository, times(1)).delete(userId1);
    }

    private UserInfo prepareUser(String userId) {
        UserInfo userInfo = new UserInfo();
        userInfo.setEmail(userId);
        userInfo.setCountryCode("UA");
        return userInfo;
    }

    private Measurements prepareMeasurements(String userId) {
        Measurements measurements = new Measurements();
        measurements.setUserId(userId);
        measurements.setLength(LengthMeasurementUnit.fromCode(lengthDefault));
        measurements.setWeight(WeightMeasurementUnit.fromCode(weightDefault));
        return measurements;
    }
}
