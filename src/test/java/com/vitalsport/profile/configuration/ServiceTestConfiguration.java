package com.vitalsport.profile.configuration;

import com.vitalsport.profile.repository.MeasurementInfoRepository;
import com.vitalsport.profile.service.BodyInfoService;
import com.vitalsport.profile.service.StrengthInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import static org.mockito.Mockito.mock;

@Configuration
@ComponentScan(basePackages = "com.vitalsport.profile.service")
public class ServiceTestConfiguration {

    @Bean
    public MeasurementInfoRepository mockBodyInfoRepository() {
        return mock(MeasurementInfoRepository.class);
    }

    @Bean
    @Autowired
    public BodyInfoService basicBodyInfoService(MeasurementInfoRepository mockMeasurementInfoRepository) {
        return new BodyInfoService(mockMeasurementInfoRepository);
    }

    @Bean
    @Autowired
    public StrengthInfoService basicStrengthInfoService(MeasurementInfoRepository mockMeasurementInfoRepository) {
        return new StrengthInfoService(mockMeasurementInfoRepository);
    }
}
