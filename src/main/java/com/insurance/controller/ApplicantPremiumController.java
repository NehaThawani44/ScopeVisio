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

    @PostMapping("/calculate-premium")
    public ResponseEntity<?> calculatePremium(@RequestBody PremiumRequest premiumRequest) {
        try {
            PremiumResponse response = premiumCalculationService.processPremiumRequest(premiumRequest);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping("/applicant/{id}")
    public ResponseEntity<?> getApplicantById(@PathVariable Long id) {
        Optional<Applicant> applicant = premiumCalculationService.getApplicantById(id);
        return applicant.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/applicant/{id}")
    public ResponseEntity<?> deleteApplicantById(@PathVariable Long id) {
        boolean deleted = premiumCalculationService.deleteApplicantById(id);
        if (deleted) {
            return ResponseEntity.ok("Applicant deleted successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}