package com.vitalsport.profile.repository;

import com.vitalsport.profile.model.BodyInfo;
import com.vitalsport.profile.model.InfoId;
import com.vitalsport.profile.model.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInfoRepository extends JpaRepository<UserInfo, String> {
}
