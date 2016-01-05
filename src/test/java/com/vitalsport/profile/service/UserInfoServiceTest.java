package com.vitalsport.profile.service;

import com.vitalsport.profile.model.UserInfo;
import com.vitalsport.profile.repository.UserInfoRepository;
import com.vitalsport.profile.service.info.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

public class UserInfoServiceTest extends BaseServiceTest {

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private UserInfoRepository mockUserInfoRepository;

    @BeforeTest
    public void before() {
        mockUserInfoRepository = mock(UserInfoRepository.class);
        userInfoService = new UserInfoService(mockUserInfoRepository);
    }

    @Test
    public void userInfoIsSaved() {

        UserInfo userInfo = prepareUser(userId);

        userInfoService.save(userId, userInfo);

        verify(mockUserInfoRepository, times(1)).save(userInfo);
    }

    @Test
    public void userInfoIsReturnedByUserId() {
        UserInfo userInfo = prepareUser(userId);

        when(mockUserInfoRepository.findOne(userId)).thenReturn(userInfo);
        UserInfo actualResult = userInfoService.get(userId);

        verify(mockUserInfoRepository, times(1)).findOne(userId);
        assertThat(actualResult).isEqualToComparingFieldByField(userInfo);
    }

    @Test
    public void userInfoIsDeletedByStrengthId() {

        userInfoService.delete(userId);

        verify(mockUserInfoRepository, times(1)).delete(userId);
    }

    private UserInfo prepareUser(String userId) {
        UserInfo userInfo = new UserInfo();
        userInfo.setEmail(userId);
        userInfo.setCountryCode("UA");
        return userInfo;
    }
}
