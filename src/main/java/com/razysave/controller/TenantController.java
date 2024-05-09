package com.razysave.controller;

import com.razysave.dto.tenant.TenantDto;
import com.razysave.entity.tenant.Tenant;
import com.razysave.exception.BuildingNotFoundException;
import com.razysave.exception.TenantNotFoundException;
import com.razysave.service.property.TenantService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/tenant")
public class TenantController {
    @Autowired
    private TenantService tenantService;
    private static final Logger logger = LoggerFactory.getLogger(TenantController.class);

    @GetMapping("/unit/{unitId}")
    public ResponseEntity<Object> getTenants(@PathVariable Integer unitId) {
        try {
            logger.info("Enter getTenants(@PathVariable Integer unitId)");
        List<TenantDto> tenants = tenantService.getTenants(unitId);
            logger.info("Exit getTenants(@PathVariable Integer unitId)");
            return ResponseEntity.ok(tenants);
        } catch (Exception e) {
            logger.error("Exit getTenants(@PathVariable Integer unitId) with exceotion {}", e.getMessage());
            return ResponseEntity.ok(Collections.emptyList());
        }
    } @GetMapping("/property/{propertyId}")
    public ResponseEntity<Object> getTenantsByProperty(@PathVariable Integer propertyId) {
        try {
            logger.info("Enter getTenantsByProperty(@PathVariable Integer propertyId) Fetching Tenant list");
        List<TenantDto> tenants = tenantService.getTenantsByPropertyId(propertyId);
            logger.info("Fetched Tenant list successfully");
            return ResponseEntity.ok(tenants);
        } catch (Exception e) {
            logger.error("exit Tenant list is empty {}", e.getMessage());
            return ResponseEntity.ok(Collections.emptyList());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getTenantByName(@PathVariable Integer id) {
        try {
            logger.info("Enter getTenantByName(@PathVariable Integer id)  Fetching Tenant with id {}", id);
            Tenant tenant = tenantService.getTenantById(id);
            logger.info("Exit getTenantByName(@PathVariable Integer id)  Fetching Tenant with id {}", id);
            return ResponseEntity.ok(tenant);
        } catch (BuildingNotFoundException e) {
            logger.error("Exit getTenantByName(@PathVariable Integer id) {}", e.getMessage());
            return ResponseEntity.ok(Collections.emptyList());
        }
    }

    @PostMapping("/")
    public ResponseEntity<Object> addTenant(@RequestBody Tenant tenant) {
        logger.info("Enter addTenant(@RequestBody Tenant tenant)");
        tenantService.addTenant(tenant);
        logger.info("Exit addTenant(@RequestBody Tenant tenant)");
        return ResponseEntity.ok(tenant);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateTenant(@PathVariable Integer id, @RequestBody Tenant updatedTenant) {
        try {
            logger.info("Enter updateTenant(@PathVariable Integer id, @RequestBody Tenant updatedTenant)");
            Tenant tenant = tenantService.updateTenant(id, updatedTenant);
            logger.info("Exit updateTenant(@PathVariable Integer id, @RequestBody Tenant updatedTenant)");
            return ResponseEntity.ok(tenant);
        } catch (TenantNotFoundException e) {
            logger.error("Exit updateTenant(@PathVariable Integer id, @RequestBody Tenant updatedTenant) an TenantNotFoundException exception occurred, {}", e.getMessage());
            return ResponseEntity.noContent().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteTenant(@PathVariable Integer id) {
        try {
            logger.info("Enter deleteTenant(@PathVariable Integer id)");
            tenantService.deleteTenantById(id);
            logger.info("Exit deleteTenant(@PathVariable Integer id)");
            return ResponseEntity.noContent().build();
        } catch (BuildingNotFoundException e) {
            logger.error("Exit deleteTenant(@PathVariable Integer id) an TenantNotFoundException exception occurred, {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
