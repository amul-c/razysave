package com.razysave.controller;

import com.razysave.dto.InsuranceIndexDto;
import com.razysave.entity.insurance.InsuranceIndex;
import com.razysave.exception.DeviceNotFoundException;
import com.razysave.exception.InsuranceIndexNotFoundException;
import com.razysave.response.ResponseHandler;
import com.razysave.service.insurance.InsuranceIndexService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
@RestController
@RequestMapping("/insurance")
public class InsuranceController {
    private static final Logger logger = LoggerFactory.getLogger(InsuranceController.class);
    @Autowired
    private InsuranceIndexService insuranceIndexService;

    @GetMapping("/property/{propertyId}")
    public ResponseEntity<List<InsuranceIndexDto>> getInsuranceIndex(@PathVariable Integer propertyId) {
        try {
            logger.info("Enter getInsuranceIndex(@PathVariable Integer propertyId)  Fetching Insurance");
            List<InsuranceIndexDto> insuranceIndex = insuranceIndexService.getInsuranceIndex(propertyId);
            logger.info("Exit getInsuranceIndex(@PathVariable Integer propertyId) Fetched insurance index list successfully");
            return ResponseEntity.ok(insuranceIndex);
        } catch (InsuranceIndexNotFoundException e) {
            logger.error("Exit getInsuranceIndex(@PathVariable Integer propertyId) InsuranceIndexNotFoundException exception occurred {},", e.getMessage());
            return ResponseEntity.ok(Collections.emptyList());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<InsuranceIndexDto> getInsuranceIndexById(@PathVariable Integer id) {
        try {
            logger.info("Enter getInsuranceIndexById(@PathVariable Integer id) Fetching Device with {}",id);
            InsuranceIndexDto insuranceIndexDto = insuranceIndexService.getInsuranceIndexById(id);
            logger.info("Exit getInsuranceIndexById(@PathVariable Integer id) Fetching Device with {}",id);
            return ResponseEntity.ok(insuranceIndexDto);
        } catch (InsuranceIndexNotFoundException e) {
            logger.error("Exit getInsuranceIndexById(@PathVariable Integer id) an InsuranceIndexNotFoundException exception occurred, {}", e.getMessage());
            return ResponseEntity.noContent().build();
        }
    }

    @PostMapping
    public ResponseEntity<Object> addInsuranceIndex(@RequestBody InsuranceIndex insuranceIndex) {
        logger.info("Enter addInsuranceIndex(@RequestBody InsuranceIndex insuranceIndex)");
        insuranceIndexService.addInsuranceIndex(insuranceIndex);
        logger.info("Exit addInsuranceIndex(@RequestBody InsuranceIndex insuranceIndex)");
        return ResponseEntity.ok(insuranceIndex);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InsuranceIndex> updateInsuranceIndex(@PathVariable Integer id, @RequestBody InsuranceIndex insuranceIndex) {
        try {
            logger.info("Enter updateInsuranceIndex(@PathVariable Integer id, @RequestBody InsuranceIndex insuranceIndex) Updating insuranceIndex with {}",id);
            InsuranceIndex insuranceIndexUpdated= insuranceIndexService.updateInsuranceIndexById(id, insuranceIndex);
            logger.info("Enter updateInsuranceIndex(@PathVariable Integer id, @RequestBody InsuranceIndex insuranceIndex) Updated insuranceIndex with {}",id);
            return ResponseEntity.ok(insuranceIndexUpdated);
        } catch (DeviceNotFoundException e) {
            logger.error("Exit updateInsuranceIndex(@PathVariable Integer id, @RequestBody InsuranceIndex insuranceIndex) an KPINotFoundException exception occurred, {}", e.getMessage());
            return ResponseEntity.noContent().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteInsuranceIndex(@PathVariable Integer id) {
        try {
            logger.info("Enter deleteInsuranceIndex(@PathVariable Integer id) Deleting insuranceIndex with {}",id);
            insuranceIndexService.deleteInsuranceIndexById(id);
            logger.info("Exit deleteInsuranceIndex(@PathVariable Integer id) Deleted insuranceIndex with {}",id);
            return ResponseHandler.generateResponse("deleted succesfully", HttpStatus.CREATED, id);
        } catch (InsuranceIndexNotFoundException e) {
            logger.error("Exit deleteInsuranceIndex(@PathVariable Integer id) an InsuranceIndexNotFoundException exception occurred, {}", e.getMessage());
            return ResponseEntity.noContent().build();
        }
    }

}
