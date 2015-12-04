package com.vitalsport.profile.common;

import org.springframework.util.StringUtils;

import java.time.LocalDate;

public class CommonUtils {

    public static LocalDate getMeasurementDate(String measurementDate) {
        return StringUtils.isEmpty(measurementDate) ? LocalDate.now() :
                LocalDate.parse(measurementDate);
    }
}
