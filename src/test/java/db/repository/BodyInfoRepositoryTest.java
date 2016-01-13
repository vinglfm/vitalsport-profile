package db.repository;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.vitalsport.profile.repository.BodyInfoRepository;
import db.configuration.TestConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import static java.time.LocalDate.of;
import static java.time.Month.*;
import static org.assertj.core.api.Assertions.assertThat;

@ContextConfiguration(classes = {TestConfiguration.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class, DbUnitTestExecutionListener.class})
@DatabaseSetup("/bodyInfoData.xml")
public class BodyInfoRepositoryTest extends AbstractTestNGSpringContextTests {

    private final String userId1 = "first@gmail.com";
    private final String userId2 = "second@gmail.com";

    @Autowired
    private BodyInfoRepository bodyInfoRepository;

    @Test
    public void findMeasurementDates() {

        assertThat(bodyInfoRepository.findMeasurementDates(userId1)).extractingResultOf("toString")
                .containsExactly("2013-08-15", "2014-05-06", "2014-05-08", "2015-09-25", "2015-10-19");

        assertThat(bodyInfoRepository.findMeasurementDates(userId2)).extractingResultOf("toString").
                containsExactly("2013-09-12", "2014-05-09", "2015-01-01");
    }

    @Test
    public void findMeasurementYears() {

        assertThat(bodyInfoRepository.findMeasurementYears(userId1)).extractingResultOf("toString")
                .containsExactly("2013", "2014", "2015");

        assertThat(bodyInfoRepository.findMeasurementYears(userId2)).extractingResultOf("toString").
                containsExactly("2013", "2014", "2015");
    }

    @Test
    public void findMeasurementMonthes() {

        assertThat(bodyInfoRepository.findMeasurementMonth(userId1, of(2014, JANUARY, 1),
                of(2015, JANUARY, 1))).extractingResultOf("toString").containsExactly("5");
        assertThat(bodyInfoRepository.findMeasurementMonth(userId1, of(2015, JANUARY, 1),
                of(2016, JANUARY, 1))).extractingResultOf("toString").containsExactly("9", "10");
        assertThat(bodyInfoRepository.findMeasurementMonth(userId2, of(2015, JANUARY, 1),
                of(2016, JANUARY, 1))).extractingResultOf("toString").containsExactly("1");
    }

    @Test
    public void findMeasurementDays() {

        assertThat(bodyInfoRepository.findMeasurementDays(userId1, of(2014, MAY, 1),
                of(2014, JUNE, 1))).extractingResultOf("toString").containsExactly("6", "8");

        assertThat(bodyInfoRepository.findMeasurementDays(userId2, of(2015, JANUARY, 1),
                of(2015, FEBRUARY, 1))).extractingResultOf("toString").containsExactly("1");
    }
}
