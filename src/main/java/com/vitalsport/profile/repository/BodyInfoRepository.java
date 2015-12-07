package com.vitalsport.profile.repository;

import com.vitalsport.profile.model.BodyId;
import com.vitalsport.profile.model.BodyInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BodyInfoRepository extends JpaRepository<BodyInfo, BodyId> {
}
