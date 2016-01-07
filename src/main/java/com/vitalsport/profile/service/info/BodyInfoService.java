package com.vitalsport.profile.service.info;

import com.vitalsport.profile.model.InfoId;
import com.vitalsport.profile.model.BodyInfo;
import com.vitalsport.profile.repository.BodyInfoRepository;
import com.vitalsport.profile.service.measurements.InfoMeasurementsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

import static java.time.LocalDate.of;
import static java.time.Month.JANUARY;

@Slf4j
@Service
public class BodyInfoService implements InfoService<InfoId, BodyInfo>, DateService {

    public static final int MIN_YEAR = 1990;
    private BodyInfoRepository bodyInfoRepository;

    private InfoMeasurementsService infoMeasurementsService;

    @Autowired
    public BodyInfoService(BodyInfoRepository bodyInfoRepository, InfoMeasurementsService infoMeasurementsService) {
        this.bodyInfoRepository = bodyInfoRepository;
        this.infoMeasurementsService = infoMeasurementsService;
    }

    //TODO: do rollback
    @Override
    public void save(InfoId id, BodyInfo bodyInfo) {
        if(id == null) {
            throw new IllegalArgumentException("id couldn't be null");
        }

        if(bodyInfo == null) {
            throw new IllegalArgumentException("bodyInfo couldn't be null");
        }

        bodyInfo.setId(id);

        log.info("Saving bodyInfo for id = {}, body = {}", id, bodyInfo);
        bodyInfoRepository.save(bodyInfo);
        infoMeasurementsService.init(id.getUserId());
    }

    @Override
    public BodyInfo get(InfoId id) {
        if(id == null) {
            throw new IllegalArgumentException("id couldn't be null");
        }

        log.info("Retrieving bodyInfo for id = {}", id);

        return bodyInfoRepository.findOne(id);
    }

    @Override
    public void delete(InfoId id) {
        if(id == null) {
            throw new IllegalArgumentException("id couldn't be null");
        }

        log.info("Deleting bodyInfo for id = {}", id);

        bodyInfoRepository.delete(id);
    }

    @Override
    public Collection<String> getMeasurementDates(String userId) {
        if(userId == null) {
            throw new IllegalArgumentException("id couldn't be null");
        }

        log.info("Finding all measurement dates for userId = {}", userId);

        return bodyInfoRepository.findMeasurementDates(userId);
    }

    public Collection<Integer> getMeasurementYears(String userId) {
        if(userId == null) {
            throw new IllegalArgumentException("id couldn't be null");
        }

        log.info("Finding all measurement years for userId = {}", userId);

        return bodyInfoRepository.findMeasurementYears(userId);
    }

    public Collection<Integer> getMeasurementMonth(String userId, int year) {
        if(userId == null) {
            throw new IllegalArgumentException("id couldn't be null");
        }

        if(year < MIN_YEAR) {
            throw new IllegalArgumentException("year couldn't be less then " + MIN_YEAR);
        }

        log.info("Finding all measurement month for id = {}, year = {}", userId, year);

        return bodyInfoRepository.findMeasurementMonth(userId,
                of(year, JANUARY, 1), of(year + 1, JANUARY, 1));
    }

    public Collection<Integer> getMeasurementDays(String userId, int year, int month) {
        if(userId == null) {
            throw new IllegalArgumentException("id couldn't be null");
        }

        if(year < MIN_YEAR) {
            throw new IllegalArgumentException("year couldn't be less then " + MIN_YEAR);
        }

        log.info("Finding all measurement days for id = {}, year = {}, month = {}", userId, year, month);

        return bodyInfoRepository.findMeasurementDays(userId, of(year, month, 1), of(year + 1, month, 1));
    }
}
