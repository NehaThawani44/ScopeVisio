package com.insurance.service;


import com.insurance.dto.PremiumRequest;
import com.insurance.dto.PremiumResponse;
import com.insurance.entity.Applicant;
import com.insurance.entity.Region;
import com.insurance.entity.VehicleType;
import com.insurance.repository.ApplicantRepository;
import com.insurance.repository.RegionRepository;
import com.insurance.repository.VehicleTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;


@Service
public class PremiumCalculationService {

    private final ApplicantRepository applicantRepository;
    private final VehicleTypeRepository vehicleTypeRepository;

    // Store region factors in a Map for better maintainability
    private static final Map<String, Double> REGION_FACTORS = Map.of(
            "berlin", 1.2,
            "bavaria", 1.3,
            "saxony", 1.1
    );

    public PremiumCalculationService(ApplicantRepository applicantRepository, VehicleTypeRepository vehicleTypeRepository) {
        this.applicantRepository = applicantRepository;
        this.vehicleTypeRepository = vehicleTypeRepository;
    }

    /**
     * Calculates premium based on mileage, vehicle type factor, and federal state.
     *
     * @param mileage           the mileage of the vehicle
     * @param vehicleTypeFactor the factor for the vehicle type
     * @param federalState      the federal state
     * @return calculated premium
     */
    public Double calculatePremium(Integer mileage, Double vehicleTypeFactor, String federalState) {
        validateInput("Mileage", mileage, val -> val != null && val >= 0);
        validateInput("Vehicle Type Factor", vehicleTypeFactor, val -> val != null && val > 0);
        validateInput("Federal State", federalState, val -> val != null && !val.isEmpty());

        double regionFactor = REGION_FACTORS.getOrDefault(federalState.toLowerCase(), 1.0);
        return mileage * vehicleTypeFactor * regionFactor;
    }

    /**
     * Processes a premium calculation request and persists the applicant data.
     *
     * @param premiumRequest the premium request object
     * @return the premium response containing the calculated premium
     */
    public PremiumResponse processPremiumRequest(PremiumRequest premiumRequest) {
        VehicleType vehicleType = vehicleTypeRepository.findByTypeName(premiumRequest.getVehicleType())
                .orElseThrow(() -> new IllegalArgumentException("Invalid vehicle type: " + premiumRequest.getVehicleType()));

        Double premiumAmount = calculatePremium(
                premiumRequest.getMileage(),
                vehicleType.getTypeFactor(),
                premiumRequest.getFederalState()
        );

        saveApplicant(premiumRequest, vehicleType, premiumAmount);
        return new PremiumResponse(premiumAmount);
    }

    /**
     * Retrieves an applicant by ID.
     *
     * @param id the applicant ID
     * @return an Optional containing the applicant, if found
     */
    public Optional<Applicant> getApplicantById(Long id) {
        return applicantRepository.findById(id);
    }

    /**
     * Deletes an applicant by ID.
     *
     * @param id the applicant ID
     * @return true if the applicant was deleted, false otherwise
     */
    public boolean deleteApplicantById(Long id) {
        if (applicantRepository.existsById(id)) {
            applicantRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Helper method to validate input values
    private <T> void validateInput(String fieldName, T value, java.util.function.Predicate<T> condition) {
        if (!condition.test(value)) {
            throw new IllegalArgumentException(fieldName + " must be a positive number " + value);
        }
    }

    // Helper method to save applicant data
    private void saveApplicant(PremiumRequest premiumRequest, VehicleType vehicleType, Double premiumAmount) {
        Applicant applicant = new Applicant();
        applicant.setMileage(premiumRequest.getMileage());
        applicant.setPostcode(premiumRequest.getPostcode());
        applicant.setVehicleType(vehicleType);
        applicant.setCalculatedPremium(premiumAmount);
        applicantRepository.save(applicant);
    }
}

