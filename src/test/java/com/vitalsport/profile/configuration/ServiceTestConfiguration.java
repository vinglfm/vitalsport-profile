package com.vitalsport.profile.configuration;

import com.vitalsport.profile.repository.InfoRepository;
import com.vitalsport.profile.service.BodyInfoService;
import com.vitalsport.profile.service.StrengthInfoService;
import com.vitalsport.profile.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import static org.mockito.Mockito.mock;

@Configuration
@ComponentScan(basePackages = "com.vitalsport.profile.service")
public class ServiceTestConfiguration {

    @Bean
    public InfoRepository mockBodyInfoRepository() {
        return mock(InfoRepository.class);
    }

    @Bean
    @Autowired
    public BodyInfoService basicBodyInfoService(InfoRepository mockInfoRepository) {
        return new BodyInfoService(mockInfoRepository);
    }

    @Bean
    @Autowired
    public StrengthInfoService basicStrengthInfoService(InfoRepository mockInfoRepository) {
        return new StrengthInfoService(mockInfoRepository);
    }

    @Bean
    @Autowired
    public UserInfoService basicUserInfoService(InfoRepository mockInfoRepository) {
        return new UserInfoService(mockInfoRepository);
    }
}
