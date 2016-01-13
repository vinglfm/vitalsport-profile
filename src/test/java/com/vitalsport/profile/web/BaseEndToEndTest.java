package com.vitalsport.profile.web;

import com.vitalsport.profile.ProfileApplication;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.testng.reporters.Files;

import java.io.File;
import java.io.IOException;
import java.net.URL;

@WebAppConfiguration
@SpringApplicationConfiguration(classes = {ProfileApplication.class})
@TestExecutionListeners(inheritListeners = false, listeners = {DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class})
@IntegrationTest
public class BaseEndToEndTest extends AbstractTestNGSpringContextTests {

    protected final String prepareJson(String path) {
        try {
            URL resource = getClass().getResource(path);
            File jsonFile = new File(resource.getPath());
            return Files.readFile(jsonFile);
        } catch (Exception exp) {
            throw new IllegalArgumentException(exp);
        }
    }
}
