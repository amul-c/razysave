package com.razysave.controller;

import com.razysave.dto.property.PropertyDto;
import com.razysave.entity.property.Property;
import com.razysave.exception.BuildingNotFoundException;
import com.razysave.exception.PropertyNotFoundException;
import com.razysave.service.property.PropertyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/property")
public class PropertyController {
    private static final Logger logger = LoggerFactory.getLogger(PropertyController.class);
    @Autowired
    private PropertyService propertyService;

    @GetMapping
    public ResponseEntity<List<PropertyDto>> getProperties() {
        logger.info("Enter getProperties() Fetching Property list");
        try {
        List<PropertyDto> properties = propertyService.getProperties();
            logger.info("Exit getProperties() Fetched Property list");
            return ResponseEntity.ok(properties);
        } catch(PropertyNotFoundException e) {
            logger.error("Exit getProperties() exception {}",e.getMessage());
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<PropertyDto> getPropertyById(@PathVariable Integer id) {
        try {
            logger.info("Enter getPropertyById(@PathVariable Integer id) Fetching Property with id {}", id);
            PropertyDto property = propertyService.getPropertyById(id);
            logger.info("Exit getPropertyById(@PathVariable Integer id) Property with id {} fetched successfully", id);
            return ResponseEntity.ok(property);
        } catch (PropertyNotFoundException e) {
            logger.error("Exit getPropertyById(@PathVariable Integer id) an PropertyNotFoundException exception occurred, {}", e.getMessage());
            return ResponseEntity.noContent().build();
        }
    }

    @PostMapping("/")
    public ResponseEntity<Object> addProperties(@RequestBody Property property) {
        Property propertySaved=propertyService.addProperty(property);
        return ResponseEntity.ok(propertySaved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Property> updateProperty(@PathVariable Integer id, @RequestBody Property partialProperty) {
        try {
            logger.info("Enter updateProperty(@PathVariable Integer id, @RequestBody Property partialProperty)");
            Property property = propertyService.updateProperty(id, partialProperty);
            logger.info("Exit updateProperty(@PathVariable Integer id, @RequestBody Property partialProperty)");
            return ResponseEntity.ok(property);
        } catch (BuildingNotFoundException e) {
            logger.error("Exit updateProperty(@PathVariable Integer id, @RequestBody Property partialProperty), {}", e.getMessage());
            return ResponseEntity.noContent().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteProperty(@PathVariable Integer id) {
        try {
            logger.info("Enter deleteProperty(@PathVariable Integer id)  Deleting Property with id {}", id);
            propertyService.deletePropertyById(id);
            logger.info("Exit deleteProperty(@PathVariable Integer id)  Property with id {} fetched successfully", id);
            return ResponseEntity.noContent().build();
        } catch (BuildingNotFoundException e) {
            logger.error("Exit deleteProperty(@PathVariable Integer id)  an PropertyNotFoundException exception occurred, {}", e.getMessage());
            return ResponseEntity.noContent().build();
        }
    }

}