package com.vitalsport.profile.common;

import org.springframework.util.StringUtils;

import java.time.LocalDate;

import static java.util.Base64.getUrlDecoder;
import static java.util.Base64.getUrlEncoder;

public class CommonUtils {

    public static String encode(String param) {
        return getUrlEncoder().encodeToString(param.getBytes());
    }

    public static String decode(String param) {
        return new String(getUrlDecoder().decode(param));
    }

    public static LocalDate getMeasurementDate(String measurementDate) {
        return StringUtils.isEmpty(measurementDate) ? LocalDate.now() :
                LocalDate.parse(measurementDate);
    }
}
