package com.razysave.service.serviceImpl.property;

import com.razysave.controller.PropertyController;
import com.razysave.dto.property.PropertyDto;
import com.razysave.entity.property.Building;
import com.razysave.entity.property.Property;
import com.razysave.exception.PropertyNotFoundException;
import com.razysave.repository.property.PropertyRepository;
import com.razysave.service.property.BuildingService;
import com.razysave.service.property.PropertyService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PropertyServiceImpl implements PropertyService {
    @Autowired
    private PropertyRepository propertyRepository;
    @Autowired
    private BuildingService buildingService;
    private ModelMapper modelMapper = new ModelMapper();
    private static final Logger logger = LoggerFactory.getLogger(PropertyServiceImpl.class);

    public List<PropertyDto> getProperties() {
        logger.info("Enter getProperties() Fetching Property list");
        List<Property> properties = propertyRepository.findAll();
        if(properties.isEmpty()) {
            logger.info("Exit getProperties() exception thrown");
            throw new PropertyNotFoundException("No Property Found");
        }
        else {
            logger.info("Exit getProperties() Fetching Property list");
            return properties.stream()
                    .map(this::mapToDto)
                    .collect(Collectors.toList());
        }
    }

    public PropertyDto getPropertyById(Integer id) {
        logger.info("Enter getPropertyById(Integer id)");
        Optional<Property> propertyOptional = propertyRepository.findById(id);
        if (propertyOptional.isPresent()) {
            PropertyDto propertyDto = modelMapper.map(propertyOptional.get(), PropertyDto.class);
            logger.info("Exit getPropertyById(Integer id)");
            return propertyDto;
        } else {
            logger.info("Exit getPropertyById(Integer id) exception thrown");
            throw new PropertyNotFoundException("Property not found with id: " + id);
        }
    }


    public Property addProperty(Property property) {
        logger.info("Enter and exit addProperty(Property property)");
       return propertyRepository.save(property);
    }

    public Property updateProperty(Integer id, Property updatedproperty) {
        logger.info("Enter updateProperty(Integer id, Property updatedproperty)");
        Optional<Property> exisitingPropertyOptional = propertyRepository.findById(id);
        if (exisitingPropertyOptional.isPresent()) {
            Property exisitingProperty = exisitingPropertyOptional.get();
            if (updatedproperty.getName() != null) {
                exisitingProperty.setName(updatedproperty.getName());
            }
            if (updatedproperty.getBuildingCount() != null) {
                exisitingProperty.setBuildingCount(updatedproperty.getBuildingCount());
            }
            if (updatedproperty.getUnitCount() != null) {
                exisitingProperty.setUnitCount(updatedproperty.getUnitCount());
            }
            if (updatedproperty.getSize() != null) {
                exisitingProperty.setSize(updatedproperty.getSize());
            }
            if (updatedproperty.getOccupancy() != null) {
                exisitingProperty.setOccupancy(updatedproperty.getOccupancy());
            }
            if (updatedproperty.getOwner() != null) {
                exisitingProperty.setOwner(updatedproperty.getOwner());
            }
            if (updatedproperty.getPhone() != null) {
                exisitingProperty.setPhone(updatedproperty.getPhone());
            }
            if (updatedproperty.getEmail() != null) {
                exisitingProperty.setEmail(updatedproperty.getEmail());
            }
            if (updatedproperty.getBuilding() != null) {
                if (exisitingProperty.getBuilding() != null)
                    exisitingProperty.getBuilding().addAll(updatedproperty.getBuilding());
                else exisitingProperty.setBuilding(updatedproperty.getBuilding());
                exisitingProperty.setBuildingCount(exisitingProperty.getBuilding().size());
            }
            if (updatedproperty.getTenantCount() != null) {
                exisitingProperty.setTenantCount(updatedproperty.getTenantCount());
            }
            logger.info("Exit updateProperty(Integer id, Property updatedproperty)");
            return propertyRepository.save(exisitingProperty);
        } else {
            logger.info("Exit updateProperty(Integer id, Property updatedproperty) exception thrown");
            throw new PropertyNotFoundException("Property not found with id : " + id);
        }
    }

    public void deletePropertyById(Integer id) {
        logger.info("Enter deletePropertyById(Integer id)");
        Optional<Property> propertyOptional = propertyRepository.findById(id);
        if (propertyOptional.isPresent()) {
            Property property = propertyOptional.get();
            List<Building> buildings = property.getBuilding();
            if(buildings!=null) {
                for (Building building : buildings) {
                    buildingService.deleteBuildingById(building.getId());
                }
            }
            logger.info("Exit deletePropertyById(Integer id)");
            propertyRepository.deleteById(id);
        } else {
            logger.info("Exit deletePropertyById(Integer id) exceotion thrown");
            throw new PropertyNotFoundException("Property Not found with id :" + id);
        }
    }
    private PropertyDto mapToDto(Property property) {
        PropertyDto dto = modelMapper.map(property, PropertyDto.class);
        return dto;
    }
}


