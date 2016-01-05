package com.vitalsport.profile.model;

import com.vitalsport.profile.common.MeasurementLengthUnit;
import com.vitalsport.profile.common.MeasurementWeightUnit;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

import javax.persistence.Entity;

@Data
@Entity
public class Measurements {
    @Id
    private String userId;

    @Enumerated(EnumType.STRING)
    private MeasurementWeightUnit weight;
    @Enumerated(EnumType.STRING)
    private MeasurementLengthUnit length;
}
