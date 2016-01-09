package com.vitalsport.profile.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum LengthMeasurementUnit {

    CENTIMETER ("cm"),
    INCH("in");

    private String label;

    LengthMeasurementUnit(String label){
        this.label = label;
    }

    @JsonCreator
    public static LengthMeasurementUnit fromCode(String code){
        for(LengthMeasurementUnit measurementUnit : values()){
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
