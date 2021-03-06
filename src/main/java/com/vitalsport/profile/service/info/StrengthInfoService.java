package com.vitalsport.profile.service.info;

import com.vitalsport.profile.model.InfoId;
import com.vitalsport.profile.model.StrengthInfo;
import com.vitalsport.profile.repository.StrengthInfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class StrengthInfoService implements InfoService<InfoId, StrengthInfo>,
        LatestMeasurementProvider<String, StrengthInfo> {

    private StrengthInfoRepository strengthInfoRepository;

    @Autowired
    public StrengthInfoService(StrengthInfoRepository strengthInfoRepository) {
        this.strengthInfoRepository = strengthInfoRepository;
    }

    @Override
    public void save(InfoId id, StrengthInfo strengthInfo) {
        if (id == null) {
            throw new IllegalArgumentException("id couldn't be null");
        }

        if (strengthInfo == null) {
            throw new IllegalArgumentException("strengthInfo couldn't be null");
        }

        log.info("Saving strengthInfo for id = {}, strength = {}", id, strengthInfo);

        strengthInfo.setId(id);
        strengthInfoRepository.save(strengthInfo);
    }

    @Override
    public StrengthInfo get(InfoId id) {
        if (id == null) {
            throw new IllegalArgumentException("id couldn't be null");
        }

        log.info("Retrieving strengthInfo for id = {}", id);

        return strengthInfoRepository.findOne(id);
    }

    @Override
    public void delete(InfoId id) {
        if (id == null) {
            throw new IllegalArgumentException("id couldn't be null");
        }

        log.info("Deleting strengthInfo for id = {}", id);

        strengthInfoRepository.delete(id);
    }

    @Override
    public StrengthInfo getLatest(String id) {
        if (id == null) {
            throw new IllegalArgumentException("id couldn't be null");
        }

        log.debug("Retrieving latest strengthInfo for id = {}", id);

        return strengthInfoRepository.findTopByIdUserIdOrderByIdDateDesc(id);
    }
}
