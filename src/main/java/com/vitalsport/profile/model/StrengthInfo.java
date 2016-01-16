package com.vitalsport.profile.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Data
@Entity
@JsonIgnoreProperties({"id"})
public class StrengthInfo {

    @EmbeddedId
    private InfoId id;

    private int benchPress;
    private int lift;
    private int squat;
}
