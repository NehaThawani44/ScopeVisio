package com.insurance.service;


import com.insurance.entity.Region;
import com.insurance.entity.VehicleType;
import com.insurance.repository.RegionRepository;
import com.insurance.repository.VehicleTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class PremiumCalculationService {

    @Autowired
    private VehicleTypeRepository vehicleTypeRepository;

    @Autowired
    private RegionRepository regionRepository;

    // Method to calculate premium based on mileage, vehicleTypeFactor, and federalState
    public Double calculatePremium(Integer mileage, Double vehicleTypeFactor, String federalState) {
        // Validate input parameters (basic validation)
        if (mileage == null || mileage < 0) {
            throw new IllegalArgumentException("Mileage cannot be null or negative");
        }
        if (vehicleTypeFactor == null || vehicleTypeFactor <= 0) {
            throw new IllegalArgumentException("Vehicle type factor must be a positive number");
        }
        if (federalState == null || federalState.isEmpty()) {
            throw new IllegalArgumentException("Federal state cannot be null or empty");
        }

        // Retrieve the region factor based on the federalState
        Double regionFactor = getRegionFactor(federalState);

        // Calculate the premium using mileage, vehicleTypeFactor, and regionFactor
        // Example formula: Premium = mileage * vehicleTypeFactor * regionFactor
        return mileage * vehicleTypeFactor * regionFactor;
    }
    private Double getRegionFactor(String federalState) {
        switch (federalState.toLowerCase()) {
            case "berlin":
                return 1.2;
            case "bavaria":
                return 1.3;
            case "saxony":
                return 1.1;
            // Add more cases for other states as needed
            default:
                return 1.0; // Default factor if federal state is unrecognized
        }
    }
    private double getMileageFactor(Integer mileage) {
        if (mileage <= 5000) return 0.5;
        else if (mileage <= 10000) return 1.0;
        else if (mileage <= 20000) return 1.5;
        else return 2.0;
    }
}
