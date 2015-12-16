package com.vitalsport.profile.configuration;

import com.vitalsport.profile.repository.BodyInfoRepository;
import com.vitalsport.profile.service.BasicBodyInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import static org.mockito.Mockito.mock;

@Configuration
@ComponentScan(basePackages = "com.vitalsport.profile.service")
public class ServiceTestConfiguration {

    @Bean
    public BodyInfoRepository mockBodyInfoRepository() {
        return mock(BodyInfoRepository.class);
    }

    @Bean
    @Autowired
    public BasicBodyInfoService basicBodyInfoService(BodyInfoRepository mockBodyInfoRepository) {
        return new BasicBodyInfoService(mockBodyInfoRepository);
    }
}
