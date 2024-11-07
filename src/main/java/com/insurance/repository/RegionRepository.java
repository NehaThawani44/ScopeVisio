package com.insurance.repository;

import com.insurance.entity.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RegionRepository extends JpaRepository<Region, Long> {
    // Finds a list of regions by the federal state (e.g., "California")
    List<Region> findByFederalState(String federalState);

    // Finds a list of regions by the region name (e.g., "Los Angeles")
    List<Region> findByRegionName(String regionName);
    //Optional<Region> findByFederalState(String federalState);
}
