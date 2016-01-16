package com.vitalsport.profile.service.info;

import com.vitalsport.profile.model.UserInfo;
import com.vitalsport.profile.repository.UserInfoRepository;
import com.vitalsport.profile.service.measurements.InfoMeasurementsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserInfoService implements InfoService<String, UserInfo> {

    private UserInfoRepository infoRepository;

    private InfoMeasurementsService infoMeasurementsService;

    @Autowired
    public UserInfoService(UserInfoRepository infoRepository, InfoMeasurementsService infoMeasurementsService) {
        this.infoRepository = infoRepository;
        this.infoMeasurementsService = infoMeasurementsService;
    }

    @Override
    public void save(String id, UserInfo userInfo) {
        if(id == null) {
            throw new IllegalArgumentException("id couldn't be null");
        }

        if(userInfo == null) {
            throw new IllegalArgumentException("userInfo couldn't be null");
        }

        log.info("Saving userInfo for id = %s, userInfo = %s", id, userInfo);

        userInfo.setEmail(id);

        infoRepository.save(userInfo);
        infoMeasurementsService.init(id);
    }

    @Override
    public UserInfo get(String id) {
        if(id == null) {
            throw new IllegalArgumentException("id couldn't be null");
        }

        log.info("Retrieving userInfo for id = %s", id);
        return infoRepository.findOne(id);
    }

    @Override
    public void delete(String id) {
        log.info("Deleting userInfo for id = %s", id);
        infoRepository.delete(id);
    }
}
