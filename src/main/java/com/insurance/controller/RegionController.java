package com.insurance.controller;

import com.insurance.entity.Region;
import com.insurance.repository.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/regions")
public class RegionController {

    @Autowired
    private RegionRepository regionRepository;

    // Get all regions
    @GetMapping
    public ResponseEntity<List<Region>> getAllRegions() {
        List<Region> regions = regionRepository.findAll();
        if (regions.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(regions);
    }

    // Get region by ID
    @GetMapping("/{id}")
    public ResponseEntity<Region> getRegionById(@PathVariable Long id) {
        Optional<Region> regionOpt = regionRepository.findById(id);
        return regionOpt.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Get region by federal state
    @GetMapping("/state/{federalState}")
    public ResponseEntity<List<Region>> getRegionByFederalState(@PathVariable String federalState) {
        List<Region> regions = regionRepository.findByFederalState(federalState);
        if (regions.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(regions);
    }

    // Get region by name
    @GetMapping("/name/{regionName}")
    public ResponseEntity<List<Region>> getRegionByName(@PathVariable String regionName) {
        List<Region> regions = regionRepository.findByRegionName(regionName);
        if (regions.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(regions);
    }
}
