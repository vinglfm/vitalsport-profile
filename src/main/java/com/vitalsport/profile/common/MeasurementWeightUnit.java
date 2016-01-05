package com.vitalsport.profile.common;

import com.fasterxml.jackson.annotation.JsonCreator;

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

    @Override
    public String toString() {
        return label;
    }
}
