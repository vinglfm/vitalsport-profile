package com.vitalsport.profile.service;

import com.vitalsport.profile.model.InfoId;
import com.vitalsport.profile.model.BodyInfo;
import com.vitalsport.profile.repository.BodyInfoRepository;
import com.vitalsport.profile.service.info.BodyInfoService;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.time.Month;
import java.util.Collection;

import static java.time.LocalDate.*;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class BodyInfoServiceTest extends BaseServiceTest {

    private BodyInfoService bodyInfoService;

    private BodyInfoRepository mockBodyInfoRepository;

    @BeforeTest
    public void before() {
        mockBodyInfoRepository = mock(BodyInfoRepository.class);
        bodyInfoService = new BodyInfoService(mockBodyInfoRepository);
    }

    @Test
    public void bodyInfoIsSaved() {

        InfoId infoId = prepareInfoId(userId1, localDate1);
        BodyInfo bodyInfo = prepareBodyInfo(infoId);

        bodyInfoService.save(infoId, bodyInfo);

        verify(mockBodyInfoRepository, times(1)).save(bodyInfo);
    }

    @Test
    public void bodyInfoIsReturnedByInfoId() {
        InfoId infoId = prepareInfoId(userId1, localDate1);
        BodyInfo bodyInfo = prepareBodyInfo(infoId);

        when(mockBodyInfoRepository.findOne(infoId)).thenReturn(bodyInfo);
        BodyInfo actualResult = bodyInfoService.get(infoId);

        verify(mockBodyInfoRepository, times(1)).findOne(infoId);

        assertThat(actualResult).isEqualToComparingFieldByField(bodyInfo);
    }

    @Test
    public void bodyInfoIsDeletedByBodyId() {
        InfoId infoId = prepareInfoId(userId1, localDate1);

        bodyInfoService.delete(infoId);

        verify(mockBodyInfoRepository, times(1)).delete(infoId);
    }

    @Test
    public void measurementDatesIsReturnedByUserId() {
        Collection<String> expectedResult = asList(date1, date2);
        when(mockBodyInfoRepository.findMeasurementDates(userId1)).thenReturn(expectedResult);
        Collection<String> actualResult = bodyInfoService.getMeasurementDates(userId1);

        verify(mockBodyInfoRepository, times(1)).findMeasurementDates(userId1);
        assertThat(actualResult).containsAll(expectedResult);
    }

    @Test
    public void measurementYearsIsReturnedForUser() {
        Collection<Integer> expectedResult = asList(2013, 2015);
        when(mockBodyInfoRepository.findMeasurementYears(userId1)).thenReturn(expectedResult);
        Collection<Integer> actualResult = bodyInfoService.getMeasurementYears(userId1);

        verify(mockBodyInfoRepository, times(1)).findMeasurementDates(userId1);
        assertThat(actualResult).containsAll(expectedResult);
    }

    @Test
    public void measurementMonthsIsReturnedForUserByYear() {
        int year = 2015;
        LocalDate startDate = of(year, Month.JANUARY, 1);
        LocalDate endDate = of(year + 1, Month.JANUARY, 1);

        Collection<Integer> expectedResult = asList(10, 11);
        when(mockBodyInfoRepository.findMeasurementMonth(userId1, startDate, endDate)).thenReturn(expectedResult);
        Collection<Integer> actualResult = bodyInfoService.getMeasurementMonth(userId1, year);

        verify(mockBodyInfoRepository, times(1))
                .findMeasurementMonth(userId1, startDate, endDate);
        assertThat(actualResult).containsAll(expectedResult);
    }

    @Test
    public void measurementDaysIsReturnedForUserByYearAndMonth() {
        int year = 2015;
        int month = 10;
        LocalDate startDate = of(year, month, 1);
        LocalDate endDate = of(year, month + 1, 1);

        Collection<Integer> expectedResult = asList(10, 11);
        when(mockBodyInfoRepository.findMeasurementDays(userId1, startDate, endDate)).thenReturn(expectedResult);
        Collection<Integer> actualResult = bodyInfoService.getMeasurementDays(userId1, year, month);

        verify(mockBodyInfoRepository, times(1))
                .findMeasurementDays(userId1, startDate, endDate);
        assertThat(actualResult).containsAll(expectedResult);
    }

    private BodyInfo prepareBodyInfo(InfoId infoId) {
        BodyInfo bodyInfo = new BodyInfo();
        bodyInfo.setId(infoId);
        bodyInfo.setBiceps(37);
        bodyInfo.setChest(60);
        bodyInfo.setFatPercentage(18);
        return bodyInfo;
    }
}