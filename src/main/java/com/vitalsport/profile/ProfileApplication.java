package com.vitalsport.profile;

import com.vitalsport.profile.config.PersistenceConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication(exclude = PersistenceConfiguration.class)
public class ProfileApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProfileApplication.class, args);
    }
}
