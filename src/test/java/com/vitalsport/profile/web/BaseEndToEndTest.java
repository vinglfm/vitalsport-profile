package com.vitalsport.profile.web;

import com.vitalsport.profile.ProfileApplication;
import com.vitalsport.profile.repository.BodyInfoRepository;
import org.aspectj.lang.annotation.After;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
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

    protected final String encodedUserId = "dmluZ2xmbUBnbWFpbC5jb20=";
    protected final String measurementDate = "2013-06-21";
    protected final String otherMeasurementDate = "2013-07-25";
    protected final String invalidFormattedDate = "20130621";


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
