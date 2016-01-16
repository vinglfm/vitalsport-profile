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
import org.testng.reporters.Files;

import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.time.Month;

import static java.time.LocalDate.of;
import static java.time.Month.AUGUST;
import static java.time.Month.JULY;
import static java.time.Month.JUNE;

@WebAppConfiguration
@SpringApplicationConfiguration(classes = {ProfileApplication.class})
@TestExecutionListeners(inheritListeners = false, listeners = {DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class})
@IntegrationTest
public class BaseEndToEndTest extends AbstractTestNGSpringContextTests {

    protected final String encodedUserId = "dmluZ2xmbUBnbWFpbC5jb20=";
    protected final int year = 2013;
    protected final int otherYear = 2060;
    protected final int month1 = 6;
    protected final int month2 = 7;
    protected final int otherMonth = 8;
    protected final int day1 = 15;
    protected final int day2 = 23;
    protected final LocalDate localMeasurementDate1 = of(year, month1, day1);
    protected final String measurementDate1 = localMeasurementDate1.toString();
    protected final LocalDate localMeasurementDate2 = of(year, month2, day2);
    protected final String measurementDate2 = localMeasurementDate2.toString();
    protected final String invalidFormattedDate = "20130621";

    protected final String prepareJson(String path) {
        try {
            URL resource = getClass().getResource(path);
            File jsonFile = new File(resource.getPath());
            return Files.readFile(jsonFile).replaceAll("\\s", "");
        } catch (Exception exp) {
            throw new IllegalArgumentException(exp);
        }
    }


}
