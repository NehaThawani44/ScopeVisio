package com.insurance.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Region {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String federalState;
    private Double regionFactor;
    private String regionName;

    public Region() {
    }

    public Region(String federalState, double regionFactor) {
        this.federalState = federalState;
        this.regionFactor = regionFactor;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFederalState() {
        return federalState;
    }

    public void setFederalState(String federalState) {
        this.federalState = federalState;
    }

    public Double getRegionFactor() {
        return regionFactor;
    }

    public void setRegionFactor(Double regionFactor) {
        this.regionFactor = regionFactor;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }
}

