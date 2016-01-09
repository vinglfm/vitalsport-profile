package com.vitalsport.profile.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.vitalsport.profile.common.Gender;
import com.vitalsport.profile.common.LocalDatePersistenceConverter;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Collection;

@Data
@Entity
public class UserInfo {
    //TODO: add validation
    @Id
    private String email;

    @Enumerated(EnumType.ORDINAL)
    private Gender gender;

    @JsonFormat(pattern="yyyy-MM-dd")
    @Convert(converter = LocalDatePersistenceConverter.class)
    private LocalDate birthday;

    private String countryCode;

    private String cityId;

    @ElementCollection(targetClass = String.class)
    private Collection<String> fitnessClubs;
}
