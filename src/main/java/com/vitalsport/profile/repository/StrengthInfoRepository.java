package com.vitalsport.profile.repository;

import com.vitalsport.profile.model.InfoId;
import com.vitalsport.profile.model.StrengthInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StrengthInfoRepository extends JpaRepository<StrengthInfo, InfoId> {
}
