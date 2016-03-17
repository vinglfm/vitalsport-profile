package com.vitalsport.profile.service.info;

import com.vitalsport.profile.model.InfoId;
import com.vitalsport.profile.model.BodyInfo;
import com.vitalsport.profile.repository.BodyInfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collection;

import static java.time.LocalDate.of;
import static java.time.Month.JANUARY;

@Slf4j
@Service
public class BodyInfoService implements InfoService<InfoId, BodyInfo>,
        LatestMeasurementProvider<String, BodyInfo>, DateService {

    //TODO: move to properties
    private static final int MIN_YEAR = 1990;
    private BodyInfoRepository bodyInfoRepository;

    @Autowired
    public BodyInfoService(BodyInfoRepository bodyInfoRepository) {
        this.bodyInfoRepository = bodyInfoRepository;
    }

    //TODO: do rollback
    @Override
    public void save(InfoId id, BodyInfo bodyInfo) {
        if (id == null) {
            throw new IllegalArgumentException("id couldn't be null");
        }

        if (bodyInfo == null) {
            throw new IllegalArgumentException("bodyInfo couldn't be null");
        }

        bodyInfo.setId(id);

        log.debug("Saving bodyInfo for id = {}, body = {}", id, bodyInfo);
        bodyInfoRepository.save(bodyInfo);
    }

    @Override
    public BodyInfo get(InfoId id) {
        if (id == null) {
            throw new IllegalArgumentException("id couldn't be null");
        }

        log.debug("Retrieving bodyInfo for id = {}", id);

        return bodyInfoRepository.findOne(id);
    }

    @Override
    public BodyInfo getLatest(String id) {
        if (id == null) {
            throw new IllegalArgumentException("id couldn't be null");
        }

        log.debug("Retrieving latest bodyInfo for id = {}", id);

        return bodyInfoRepository.findTopByIdUserIdOrderByIdDateDesc(id);
    }

    @Override
    public Collection<String> getMeasurementDates(String userId) {
        if (userId == null) {
            throw new IllegalArgumentException("id couldn't be null");
        }

        log.debug("Finding all measurement dates for userId = {}", userId);

        return bodyInfoRepository.findMeasurementDates(userId);
    }

    @Override
    public Collection<Integer> getMeasurementYears(String userId) {
        if (userId == null) {
            throw new IllegalArgumentException("id couldn't be null");
        }

        log.debug("Finding all measurement years for userId = {}", userId);

        return bodyInfoRepository.findMeasurementYears(userId);
    }

    @Override
    public Collection<Integer> getMeasurementMonth(String userId, int year) {
        if (userId == null) {
            throw new IllegalArgumentException("id couldn't be null");
        }

        if (year < MIN_YEAR) {
            throw new IllegalArgumentException("year couldn't be less then " + MIN_YEAR);
        }

        log.debug("Finding all measurement month for id = {}, year = {}", userId, year);

        return bodyInfoRepository.findMeasurementMonth(userId,
                of(year, JANUARY, 1), of(year + 1, JANUARY, 1));
    }

    @Override
    public Collection<Integer> getMeasurementDays(String userId, int year, int month) {
        if (userId == null) {
            throw new IllegalArgumentException("id couldn't be null");
        }

        if (year < MIN_YEAR) {
            throw new IllegalArgumentException("year couldn't be less then " + MIN_YEAR);
        }

        log.debug("Finding all measurement days for id = {}, year = {}, month = {}", userId, year, month);

        LocalDate startDate = of(year, month, 1);
        LocalDate endDate = startDate.plusMonths(1);
        return bodyInfoRepository.findMeasurementDays(userId, startDate, endDate);
    }

    @Override
    public void delete(InfoId id) {
        if (id == null) {
            throw new IllegalArgumentException("id couldn't be null");
        }

        log.debug("Deleting bodyInfo for id = {}", id);

        bodyInfoRepository.delete(id);
    }
}
