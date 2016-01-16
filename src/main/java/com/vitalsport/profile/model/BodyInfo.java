package com.vitalsport.profile.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Data
@Entity
@JsonIgnoreProperties({"id"})
public class BodyInfo {

    @EmbeddedId
    private InfoId id;

    private double neck;
    private double chest;
    private double waist;

    private double biceps;
    private double forearm;
    private double wrist;

    private double hip;
    private double thigh;
    private double gastrocnemius;
    private double ankle;

    private double height;
    private double weight;
    private int fatPercentage;
}
