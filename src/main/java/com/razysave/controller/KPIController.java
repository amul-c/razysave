package com.razysave.controller;

import com.razysave.dto.kpi.KPIDto;
import com.razysave.entity.kpi.KPI;
import com.razysave.exception.DeviceNotFoundException;
import com.razysave.exception.KPINotFoundException;
import com.razysave.response.ResponseHandler;
import com.razysave.service.kpi.KPIService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/kpi")
public class KPIController {
    private static final Logger logger = LoggerFactory.getLogger(KPIController.class);
    @Autowired
    private KPIService kpiService;

    @GetMapping("/property/{propertyId}")
    public ResponseEntity<List<KPIDto>> getKPI(@PathVariable Integer propertyId) {
        try {
            logger.info("Enter getKPI(@PathVariable Integer propertyId) Fetching KPI list");
            List<KPIDto> kpis = kpiService.getKPI(propertyId);
            logger.info("Exit getKPI(@PathVariable Integer propertyId) Fetched KPI list successfully");
            return ResponseEntity.ok(kpis);
        } catch (KPINotFoundException e) {
            logger.error("Exit getKPI(@PathVariable Integer propertyId) an KPINotFoundException exception occurred {},", e.getMessage());
            return ResponseEntity.ok(Collections.emptyList());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<KPIDto> getKPIById(@PathVariable Integer id) {
        try {
            logger.info("Enter getKPIById(@PathVariable Integer id)  Fetching Device with {}", id);
            KPIDto kpiDto = kpiService.getKPIById(id);
            logger.info("Exit getKPIById(@PathVariable Integer id)  Fetched Device with {}", id);
            return ResponseEntity.ok(kpiDto);
        } catch (DeviceNotFoundException e) {
            logger.error("An KPINotFoundException exception occurred, {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/")
    public ResponseEntity<Object> addKPI(@RequestBody KPI kpi) {
        logger.info("Enter getKPIById(@PathVariable Integer id)");
        kpiService.addKPI(kpi);
        logger.info("Exit getKPIById(@PathVariable Integer id)");
        return ResponseEntity.ok(kpi);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateKPI(@PathVariable Integer id, @RequestBody KPI kpi) {
        try {
            logger.info("Enter updateKPI(@PathVariable Integer id, @RequestBody KPI kpi) with Updating KPI with {}", id);
           KPI kpiUpdated= kpiService.updateKPIById(id, kpi);
            logger.info("Exit updateKPI(@PathVariable Integer id, @RequestBody KPI kpi)Updated KPI with {}", id);
            return ResponseEntity.ok(kpiUpdated);
        } catch (DeviceNotFoundException e) {
            logger.error("Exit updateKPI(@PathVariable Integer id, @RequestBody KPI kpi) an KPINotFoundException exception occurred, {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteKPI(@PathVariable Integer id) {
        try {
            logger.info("Enter deleteKPI(@PathVariable Integer id) Deleting KPI with {}", id);
            kpiService.deleteKPIById(id);
            logger.info("Exit deleteKPI(@PathVariable Integer id) Deleting KPI with {}", id);
            return ResponseHandler.generateResponse("deleted succesfully", HttpStatus.CREATED, id);
        } catch (DeviceNotFoundException e) {
            logger.error("Exit deleteKPI(@PathVariable Integer id) an KPINotFoundException exception occurred, {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
