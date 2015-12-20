package com.vitalsport.profile.service;

import com.vitalsport.profile.model.MeasurementId;
import com.vitalsport.profile.model.StrengthInfo;
import com.vitalsport.profile.repository.InfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class StrengthInfoService implements MeasurementInfoService<MeasurementId, StrengthInfo> {

    private InfoRepository<MeasurementId, StrengthInfo> strengthInfoRepository;

    @Autowired
    public StrengthInfoService(InfoRepository<MeasurementId, StrengthInfo> strengthInfoRepository) {
        this.strengthInfoRepository = strengthInfoRepository;
    }

    @Override
    public void save(MeasurementId id, StrengthInfo strengthInfo) {
        if (id == null) {
            throw new IllegalArgumentException("id couldn't be null");
        }

        if (strengthInfo == null) {
            throw new IllegalArgumentException("strengthInfo couldn't be null");
        }

        log.info("Saving strengthInfo for id = %s, strength = %s", id, strengthInfo);

        strengthInfo.setId(id);
        strengthInfoRepository.save(strengthInfo);
    }

    @Override
    public StrengthInfo get(MeasurementId id) {
        if (id == null) {
            throw new IllegalArgumentException("id couldn't be null");
        }

        log.info("Retrieving strengthInfo for id = %s", id);

        return strengthInfoRepository.findOne(id);
    }

    @Override
    public void delete(MeasurementId id) {
        if (id == null) {
            throw new IllegalArgumentException("id couldn't be null");
        }

        log.info("Deleting strengthInfo for id = %s", id);

        strengthInfoRepository.delete(id);
    }
}
