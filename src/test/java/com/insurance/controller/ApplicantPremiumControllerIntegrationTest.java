/*
package com.insurance.controller;



import com.insurance.dto.PremiumRequest;
import com.insurance.entity.VehicleType;
import com.insurance.entity.Region;
import com.insurance.repository.ApplicantRepository;
import com.insurance.repository.RegionRepository;
import com.insurance.repository.VehicleTypeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class ApplicantPremiumControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ApplicantRepository applicantRepository;

    @Autowired
    private VehicleTypeRepository vehicleTypeRepository;

    @Autowired
    private RegionRepository regionRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeAll
    static void setUpStaticData(@Autowired VehicleTypeRepository vehicleTypeRepository,
                                @Autowired RegionRepository regionRepository) {
        // Clear repositories and insert static data only once
        vehicleTypeRepository.deleteAll();
        regionRepository.deleteAll();

        VehicleType vehicleType = new VehicleType("Car", 1.5);
        vehicleTypeRepository.save(vehicleType);

        Region region = new Region("California", 1.2);
        regionRepository.save(region);
    }

    @BeforeEach
    void clearApplicants() {
        // Clear only the applicant repository before each test to keep other setup intact
        applicantRepository.deleteAll();
    }

    @Test
    void testCalculatePremium() throws Exception {
        // Given
        PremiumRequest request = new PremiumRequest();
        request.setMileage(12000);
        request.setVehicleType("Car");
        request.setFederalState("California");

        // When & Then
        mockMvc.perform(post("/api/calculate-premium")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.premiumAmount").value(180.00)); // Adjust expected value as per calculation logic
    }

    @Test
    void testCalculatePremiumWithInvalidVehicleType() throws Exception {
        // Given
        PremiumRequest request = new PremiumRequest();
        request.setMileage(12000);
        request.setVehicleType("Truck"); // Invalid type
        request.setFederalState("California");

        // When & Then
        mockMvc.perform(post("/api/calculate-premium")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Invalid vehicle type"))
                .andExpect(jsonPath("$.error").value("Invalid vehicle type"));
    }

    @Test
    void testCalculatePremiumWithInvalidState() throws Exception {
        // Given
        PremiumRequest request = new PremiumRequest();
        request.setMileage(12000);
        request.setVehicleType("Car");
        request.setFederalState("UnknownState"); // Invalid state

        // When & Then
        mockMvc.perform(post("/api/calculate-premium")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Invalid federal state"))
                .andExpect(jsonPath("$.error").value("Invalid federal state"));
    }

    @Test
    void testCalculatePremiumWithMissingMileage() throws Exception {
        // Given
        PremiumRequest request = new PremiumRequest();
        request.setVehicleType("Car");
        request.setFederalState("California");

        // When & Then
        mockMvc.perform(post("/api/calculate-premium")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Mileage is required"))
                .andExpect(jsonPath("$.error").value("Mileage is required"));
    }
}

*/
