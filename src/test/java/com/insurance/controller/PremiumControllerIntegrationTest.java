package com.insurance.controller;



import com.insurance.controller.PremiumController;
import com.insurance.dto.PremiumRequest;
import com.insurance.dto.PremiumResponse;
import com.insurance.entity.Applicant;
import com.insurance.entity.VehicleType;
import com.insurance.entity.Region;
import com.insurance.repository.ApplicantRepository;
import com.insurance.repository.RegionRepository;
import com.insurance.repository.VehicleTypeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class PremiumControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ApplicantRepository applicantRepository;

    @Autowired
    private VehicleTypeRepository vehicleTypeRepository;

    @Autowired
    private RegionRepository regionRepository;

    @BeforeEach
    void setUp() {
        // Optionally clear the repository before each test
        applicantRepository.deleteAll();
        vehicleTypeRepository.deleteAll();
        regionRepository.deleteAll();

        // Add sample data for Vehicle Types and Regions
        VehicleType vehicleType = new VehicleType("Car", 1.5);
        vehicleTypeRepository.save(vehicleType);

        Region region = new Region("California", 1.2);
        regionRepository.save(region);
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
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.premiumAmount").value(180.00)); // Example expected value
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
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Invalid vehicle type"));
    }
}

