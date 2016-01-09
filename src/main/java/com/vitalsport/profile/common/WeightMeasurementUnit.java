package com.vitalsport.profile.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum WeightMeasurementUnit {

    KILOGRAM("kg"),
    POUND ("lb");

    private String label;

    WeightMeasurementUnit(String label){
        this.label = label;
    }

    @JsonCreator
    public static WeightMeasurementUnit fromCode(String code){
        for(WeightMeasurementUnit measurementUnit : values()){
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
