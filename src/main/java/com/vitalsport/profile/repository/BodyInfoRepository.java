package com.vitalsport.profile.repository;

import com.vitalsport.profile.model.BodyInfo;
import com.vitalsport.profile.model.InfoId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BodyInfoRepository extends JpaRepository<BodyInfo, InfoId> {
}
