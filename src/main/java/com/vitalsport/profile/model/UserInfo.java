package com.vitalsport.profile.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class UserInfo {
    @Id
    private String email;

    private String sex;
    private String birthday;

    private String countryCode;

    private String cityId;

//    private List<String> fitnessClubId;
}
