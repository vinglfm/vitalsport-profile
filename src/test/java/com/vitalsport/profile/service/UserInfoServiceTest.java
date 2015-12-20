package com.vitalsport.profile.service;

import com.vitalsport.profile.configuration.ServiceTestConfiguration;
import com.vitalsport.profile.model.UserInfo;
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
public class UserInfoServiceTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private InfoRepository mockInfoRepository;

    @Test
    public void userInfoIsSaved() {

        String id = "email";
        UserInfo userInfo = mock(UserInfo.class);

        userInfoService.save(id, userInfo);

        verify(mockInfoRepository, times(1)).save(userInfo);
    }

    @Test
    public void userInfoIsReturnedByStrengthId() {
        String id = "email";
        UserInfo userInfo = mock(UserInfo.class);

        when(mockInfoRepository.findOne(id)).thenReturn(userInfo);
        UserInfo actualResult = userInfoService.get(id);

        verify(mockInfoRepository, times(1)).findOne(id);
        assertThat(actualResult).isEqualToComparingFieldByField(userInfo);
    }

    @Test
    public void userInfoIsDeletedByStrengthId() {
        String id = "email";

        userInfoService.delete(id);

        verify(mockInfoRepository, times(1)).delete(id);
    }
}
