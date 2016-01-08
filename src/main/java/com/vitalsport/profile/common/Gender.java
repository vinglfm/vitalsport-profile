package com.vitalsport.profile.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Gender {
    MAN("M"),
    WOMAN("W"),
    HERCULES("H");

    private String label;

    Gender(String label) {
        this.label = label;
    }

    @JsonCreator
    public static Gender fromCode(String code){
        for(Gender gender : values()){
            if(gender.label.equals(code)){
                return gender;
            }
        }
        return HERCULES;
    }

    @JsonValue
    public String getLabel() {
        return label;
    }
}
