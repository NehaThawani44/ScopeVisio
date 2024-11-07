package com.insurance.controller;

import com.insurance.dto.PremiumRequest;
import com.insurance.dto.PremiumResponse;
import com.insurance.entity.Applicant;
import com.insurance.entity.VehicleType;
import com.insurance.repository.ApplicantRepository;
import com.insurance.repository.VehicleTypeRepository;
import com.insurance.service.PremiumCalculationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ApplicantPremiumController {

    @Autowired
    private PremiumCalculationService premiumCalculationService;

    @Autowired
    private ApplicantRepository applicantRepository;

    @Autowired
    private VehicleTypeRepository vehicleTypeRepository;

    @PostMapping("/calculate-premium")
    public ResponseEntity<?> calculatePremium(@RequestBody PremiumRequest premiumRequest) {
        // Fetch VehicleType
        Optional<VehicleType> vehicleTypeOpt = vehicleTypeRepository.findByTypeName(premiumRequest.getVehicleType());
        if (vehicleTypeOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid vehicle type");
        }
        VehicleType vehicleType = vehicleTypeOpt.get();

        // Calculate Premium
        Double premiumAmount = premiumCalculationService.calculatePremium(
                premiumRequest.getMileage(),
                vehicleType.getTypeFactor(),
                premiumRequest.getFederalState()
        );

        // Save the applicant and premium details
        Applicant applicant = new Applicant();
        applicant.setMileage(premiumRequest.getMileage());
        applicant.setPostcode(premiumRequest.getPostcode());
        applicant.setVehicleType(vehicleTypeOpt.get());
        applicant.setCalculatedPremium(premiumAmount);
        applicantRepository.save(applicant);

        // Return the premium response
        return ResponseEntity.ok(new PremiumResponse(premiumAmount));
    }

    @GetMapping("/applicant/{id}")
    public ResponseEntity<?> getApplicantById(@PathVariable Long id) {
        // Fetch applicant by ID
        Optional<Applicant> applicantOpt = applicantRepository.findById(id);
        if (applicantOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(applicantOpt.get());
    }

    @DeleteMapping("/applicant/{id}")
    public ResponseEntity<?> deleteApplicantById(@PathVariable Long id) {
        // Delete applicant by ID
        if (applicantRepository.existsById(id)) {
            applicantRepository.deleteById(id);
            return ResponseEntity.ok("Applicant deleted successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}