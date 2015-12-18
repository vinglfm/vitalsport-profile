package com.vitalsport.profile.model;

import lombok.Data;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Data
@Entity
public class StrengthInfo {

    @EmbeddedId
    private MeasurementId id;

    private String benchPress;
    private String lift;
    private String squat;
//    private String weightMeasurement;
}
