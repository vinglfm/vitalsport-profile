package com.vitalsport.profile.common;

import org.testng.annotations.Test;

import java.sql.Date;
import java.time.LocalDate;
import java.time.Month;

import static org.assertj.core.api.Assertions.assertThat;

public class LocalDatePersistenceConverterTest {

    private LocalDatePersistenceConverter converter = new LocalDatePersistenceConverter();

    @Test
    public void localeDateIsConvertedToDatabaseWrapper() throws Exception {
        int expectedYear = 2015;
        Month expectedMonth = Month.APRIL;
        int expectedDayOfMonth = 15;
        LocalDate localDate = LocalDate.of(expectedYear, expectedMonth, expectedDayOfMonth);

        Date actualDate = converter.convertToDatabaseColumn(localDate);

        assertThat(actualDate).hasYear(expectedYear);
        assertThat(actualDate).hasMonth(expectedMonth.getValue());
        assertThat(actualDate).hasDayOfMonth(expectedDayOfMonth);
    }

    @Test
    public void dateIsConvertedToEntityAttribute() throws Exception {
        int expectedYear = 2015;
        Month expectedMonth = Month.APRIL;
        int expectedDayOfMonth = 15;
        // a lot of fun with old API
        Date date = new Date(expectedYear - 1900, expectedMonth.getValue() - 1, expectedDayOfMonth);

        LocalDate actualLocalDate = converter.convertToEntityAttribute(date);

        assertThat(actualLocalDate.getYear()).isEqualTo(expectedYear);
        assertThat(actualLocalDate.getMonth()).isEqualTo(expectedMonth);
        assertThat(actualLocalDate.getDayOfMonth()).isEqualTo(expectedDayOfMonth);
    }
}