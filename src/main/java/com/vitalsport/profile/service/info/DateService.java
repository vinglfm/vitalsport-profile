package com.vitalsport.profile.service.info;

import java.util.Collection;

public interface DateService {
    Collection<String> getMeasurementDates(String userId);

    Collection<Integer> getMeasurementYears(String userId);

    Collection<Integer> getMeasurementMonth(String userId, int year);

    Collection<Integer> getMeasurementDays(String userId, int year, int month);
}
