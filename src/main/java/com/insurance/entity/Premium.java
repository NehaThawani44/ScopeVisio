package com.insurance.entity;

import jakarta.persistence.*;

@Entity
public class Premium {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Premium() {
    }

    private Double premiumAmount;
    private Double mileageFactor;
    private Double vehicleTypeFactor;
    private Double regionFactor;

    @OneToOne
    @JoinColumn(name = "applicant_id")
    private Applicant applicant;

    // Getters and Setters
}
