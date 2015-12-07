package com.vitalsport.profile.model;

import lombok.Data;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Data
@Entity
public class BodyInfo {

    @EmbeddedId
    private BodyId id;

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
