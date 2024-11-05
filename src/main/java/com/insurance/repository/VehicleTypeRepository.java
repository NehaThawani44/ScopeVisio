package com.insurance.repository;

import com.insurance.entity.VehicleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VehicleTypeRepository extends JpaRepository<VehicleType, Long> {
        Optional<VehicleType> findByTypeName(String typeName);
    }

