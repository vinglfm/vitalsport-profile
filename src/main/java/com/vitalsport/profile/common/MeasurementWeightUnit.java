package com.vitalsport.profile.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum MeasurementWeightUnit {

    POUND ("lb"),
    KILOGRAM("kg");

    private String label;

    MeasurementWeightUnit (String label){
        this.label = label;
    }

    @JsonCreator
    public static MeasurementWeightUnit fromCode(String code){
        for(MeasurementWeightUnit measurementUnit : values()){
            if(measurementUnit.label.equals(code)){
                return measurementUnit;
            }
        }
        return null;
    }

    @JsonValue
    public String getLabel() {
        return label;
    }
}
