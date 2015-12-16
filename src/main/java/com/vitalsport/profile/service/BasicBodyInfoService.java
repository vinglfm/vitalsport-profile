package com.vitalsport.profile.service;

import com.vitalsport.profile.model.BodyId;
import com.vitalsport.profile.model.BodyInfo;
import com.vitalsport.profile.repository.BodyInfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class BasicBodyInfoService implements BodyInfoService {

    private BodyInfoRepository bodyInfoRepository;

    @Autowired
    public BasicBodyInfoService(BodyInfoRepository bodyInfoRepository) {
        this.bodyInfoRepository = bodyInfoRepository;
    }

    @Override
    public void saveBodyInfo(BodyId id, BodyInfo bodyInfo) {
        log.info("Saving bodyInfo for id = %s, body = %s", id, bodyInfo);
        bodyInfo.setId(id);
        bodyInfoRepository.save(bodyInfo);
    }

    @Override
    public BodyInfo getBodyInfo(BodyId id) {
        log.info("Retrieving bodyInfo for id = %s", id);
        return bodyInfoRepository.findOne(id);
    }

    @Override
    public void deleteBodyInfo(BodyId id) {
        log.info("Deleting bodyInfo for id = %s", id);
        bodyInfoRepository.delete(id);
    }
}
