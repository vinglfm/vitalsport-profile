package com.vitalsport.profile.model;

import lombok.Data;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Data
@Entity
public class StrengthInfo {

    @EmbeddedId
    private InfoId id;

    private int benchPress;
    private int lift;
    private int squat;
}
