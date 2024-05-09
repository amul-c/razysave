package com.razysave.controller;

import com.razysave.dto.unit.UnitInfoDto;
import com.razysave.dto.unit.UnitListDto;
import com.razysave.entity.property.Unit;
import com.razysave.exception.UnitNotFoundException;
import com.razysave.service.property.UnitService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/unit")
public class UnitController {
    private static final Logger logger = LoggerFactory.getLogger(UnitController.class);
    @Autowired
    private UnitService unitService;

    @GetMapping("/building/{buildingId}/list")
    public ResponseEntity<List<UnitListDto>> getUnits(@PathVariable Integer buildingId) {
        try {
            logger.info("Enter getUnits(@PathVariable Integer buildingId) Fetching Unit list");
        List<UnitListDto> units = unitService.getUnits(buildingId);
            logger.info("Exit getUnits(@PathVariable Integer buildingId)Unit list is empty");
            return ResponseEntity.ok(units);
        } catch (Exception e) {
            logger.error("Exit getUnits(@PathVariable Integer buildingId)Unit list is empty {}", e.getMessage());
            return ResponseEntity.ok(Collections.emptyList());
        }
    }

    @GetMapping("/property/{propertyId}/list")
    public ResponseEntity<List<UnitListDto>> getUnitByPropertyId(@PathVariable Integer propertyId) {
        try {
            logger.info("Enter getUnits(@PathVariable Integer buildingId) Fetching Unit list");
        List<UnitListDto> units = unitService.getUnitsByProperty(propertyId);
            logger.info("Exit getUnits(@PathVariable Integer buildingId)");
            return ResponseEntity.ok(units);
        } catch (Exception e) {
            logger.error("Exit getUnits(@PathVariable Integer buildingId) {}", e.getMessage());
            return ResponseEntity.ok(Collections.emptyList());
        }
    }

    @GetMapping("/info/{id}")
    public ResponseEntity<UnitInfoDto> getUnitById(@PathVariable Integer id) {
        try {
            logger.info("Enter getUnitById(@PathVariable Integer id) with id {}", id);
            UnitInfoDto unit = unitService.getUnitById(id);
            logger.info("Exit getUnitById(@PathVariable Integer id)");
            return ResponseEntity.ok(unit);
        } catch (UnitNotFoundException e) {
            logger.error("Exit getUnitById(@PathVariable Integer id) {}", e.getMessage());
            return ResponseEntity.noContent().build();
        }
    }

    @PostMapping("/")
    public ResponseEntity<Unit> addUnits(@RequestBody Unit unit) {
        logger.info("Enter addUnits(@RequestBody Unit unit)");
        unitService.addUnit(unit);
        logger.info("Exit addUnits(@RequestBody Unit unit)");
        return ResponseEntity.ok(unit);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateUnit(@PathVariable Integer id, @RequestBody Unit updatedUnit) {
        try {
            logger.info("Enter updateUnit(@PathVariable Integer id, @RequestBody Unit updatedUnit)");
            Unit unit = unitService.updateUnit(id, updatedUnit);
            logger.info("Exit updateUnit(@PathVariable Integer id, @RequestBody Unit updatedUnit)");
            return ResponseEntity.ok(unit);
        } catch (UnitNotFoundException e) {
            logger.error("Exit updateUnit(@PathVariable Integer id, @RequestBody Unit updatedUnit) {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUnit(@PathVariable Integer id) {
        try {
            logger.info("Enter deleteUnit(@PathVariable Integer id) {}", id);
            unitService.deleteUnitById(id);
            logger.info("Exit deleteUnit(@PathVariable Integer id)");
            return ResponseEntity.noContent().build();
        } catch (UnitNotFoundException e) {
            logger.error("Exit deleteUnit(@PathVariable Integer id) an UnitNotFoundException exception occurred, {}", e.getMessage());
            return ResponseEntity.ok().build();
        }
    }
}

