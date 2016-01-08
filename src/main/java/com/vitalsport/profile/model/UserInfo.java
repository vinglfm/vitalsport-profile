package com.vitalsport.profile.model;

import com.vitalsport.profile.common.Gender;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Collection;

@Data
@Entity
public class UserInfo {
    @Id
    private String email;

    @Enumerated(EnumType.ORDINAL)
    private Gender gender;

    private LocalDate birthday;

    private String countryCode;

    private String cityId;

    @ElementCollection(targetClass = String.class)
    private Collection<String> fitnessClubs;
}
