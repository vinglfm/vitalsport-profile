package com.vitalsport.profile.common;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum MeasurementLengthUnit {

    CENTIMETER ("cm"),
    INCH("in");

    private String label;

    MeasurementLengthUnit(String label){
        this.label = label;
    }

    @JsonCreator
    public static MeasurementLengthUnit fromCode(String code){
        for(MeasurementLengthUnit measurementUnit : values()){
            if(measurementUnit.label.equals(code)){
                return measurementUnit;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return label;
    }
}
