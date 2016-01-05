package com.vitalsport.profile.service.info;

import com.vitalsport.profile.model.InfoId;
import com.vitalsport.profile.model.BodyInfo;
import com.vitalsport.profile.repository.BodyInfoRepository;
import com.vitalsport.profile.service.measurements.InfoMeasurementsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class BodyInfoService implements InfoService<InfoId, BodyInfo> {

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

        log.info("Saving bodyInfo for id = %s, body = %s", id, bodyInfo);
        bodyInfoRepository.save(bodyInfo);
        infoMeasurementsService.init(id.getUserId());
    }

    @Override
    public BodyInfo get(InfoId id) {
        if(id == null) {
            throw new IllegalArgumentException("id couldn't be null");
        }

        log.info("Retrieving bodyInfo for id = %s", id);

        return bodyInfoRepository.findOne(id);
    }

    @Override
    public void delete(InfoId id) {
        if(id == null) {
            throw new IllegalArgumentException("id couldn't be null");
        }

        log.info("Deleting bodyInfo for id = %s", id);

        bodyInfoRepository.delete(id);
    }
}
