package com.insurance.util;

import com.insurance.entity.Region;
import com.opencsv.CSVReader;

import com.insurance.repository.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import java.util.logging.Logger;

import java.io.InputStreamReader;

@Component
public class CsvDataLoader implements CommandLineRunner {

    @Autowired
    private RegionRepository regionRepository;

    private static final Logger logger = Logger.getLogger(CsvDataLoader.class.getName());

    @Override
    public void run(String... args) throws Exception {
        // Using ClassPathResource to load the file from src/main/resources/data/postcodes.csv
        ClassPathResource resource = new ClassPathResource("data/postcodes.csv");

        try (CSVReader reader = new CSVReader(new InputStreamReader(resource.getInputStream()))) {
            String[] line;
            reader.readNext(); // Skip header

            while ((line = reader.readNext()) != null) {
                try {
                    // Mapping relevant fields from the CSV file to the Region entity

                    // Extracting federal state from the CSV - assuming it's in column index 2 (REGION1)
                    String federalState = line[2];  // "Baden-Württemberg"

                    // Extracting region name from the CSV - assuming it's in column index 7 (ORT)
                    String regionName = line[7];  // "Bad Krozingen" or "Hartheim"

                    // Assign a region factor - use a default value since it's not in the CSV
                    double regionFactor = 1.2; // This value can be adjusted as needed

                    // Creating and populating the Region entity
                    Region region = new Region();
                    region.setFederalState(federalState);
                    region.setRegionName(regionName);
                    region.setRegionFactor(regionFactor);

                    // Save the region entity to the repository
                    regionRepository.save(region);

                    logger.info("Saved region - Federal State: " + federalState + ", Region Name: " + regionName);

                } catch (Exception e) {
                    // Handle any unexpected issues for individual records, log and continue
                    logger.severe("Error processing CSV line: " + String.join(",", line) + " - Error: " + e.getMessage());
                }
            }
        } catch (Exception e) {
            logger.severe("Error reading CSV file: " + e.getMessage());
        }
    }

    // Helper method to check if a string is numeric
    private boolean isNumeric(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        try {
            Double.parseDouble(str);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
}
