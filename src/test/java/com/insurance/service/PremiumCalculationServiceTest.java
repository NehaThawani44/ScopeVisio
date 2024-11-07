package com.insurance.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PremiumCalculationServiceTest {
    @InjectMocks
    private PremiumCalculationService premiumCalculationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void calculatePremium_validInput_returnsCorrectPremium() {
        Double premium = premiumCalculationService.calculatePremium(15000, 1.5, "Berlin");
        assertEquals(27000.0, premium);
    }

    @Test
    void calculatePremium_invalidMileage_throwsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                premiumCalculationService.calculatePremium(-5000, 1.5, "Berlin")
        );
        assertEquals("Mileage cannot be null or negative", exception.getMessage());
    }

    @Test
    void calculatePremium_invalidVehicleTypeFactor_throwsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                premiumCalculationService.calculatePremium(15000, -1.0, "Berlin")
        );
        assertEquals("Vehicle type factor must be a positive number", exception.getMessage());
    }

    @Test
    void calculatePremium_nullFederalState_throwsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                premiumCalculationService.calculatePremium(15000, 1.5, null)
        );
        assertEquals("Federal state cannot be null or empty", exception.getMessage());
    }

    @Test
    void calculatePremium_unrecognizedFederalState_usesDefaultFactor() {
        Double premium = premiumCalculationService.calculatePremium(10000, 1.2, "UnknownState");
        assertEquals(12000.0, premium); // Uses default region factor of 1.0
    }
}
