package com.vitalsport.profile.service;

import com.vitalsport.profile.model.MeasurementId;
import com.vitalsport.profile.model.StrengthInfo;
import com.vitalsport.profile.repository.MeasurementInfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class StrengthInfoService implements MeasurementInfoService<StrengthInfo> {

    private MeasurementInfoRepository<StrengthInfo> strengthInfoRepository;

    @Autowired
    public StrengthInfoService(MeasurementInfoRepository<StrengthInfo> strengthInfoRepository) {
        this.strengthInfoRepository = strengthInfoRepository;
    }

    @Override
    public void save(MeasurementId id, StrengthInfo bodyInfo) {
        if (id == null) {
            throw new IllegalArgumentException("id couldn't be null");
        }

        if (bodyInfo == null) {
            throw new IllegalArgumentException("bodyInfo couldn't be null");
        }

        log.info("Saving bodyInfo for id = %s, body = %s", id, bodyInfo);

        bodyInfo.setId(id);
        strengthInfoRepository.save(bodyInfo);
    }

    @Override
    public StrengthInfo get(MeasurementId id) {
        if (id == null) {
            throw new IllegalArgumentException("id couldn't be null");
        }

        log.info("Retrieving bodyInfo for id = %s", id);

        return strengthInfoRepository.findOne(id);
    }

    @Override
    public void delete(MeasurementId id) {
        if (id == null) {
            throw new IllegalArgumentException("id couldn't be null");
        }

        log.info("Deleting bodyInfo for id = %s", id);

        strengthInfoRepository.delete(id);
    }
}
