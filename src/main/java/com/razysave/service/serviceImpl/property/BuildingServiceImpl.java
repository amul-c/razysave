package com.razysave.service.serviceImpl.property;

import com.razysave.dto.BuildingListDto;
import com.razysave.entity.property.Building;
import com.razysave.entity.property.Property;
import com.razysave.entity.property.Unit;
import com.razysave.exception.BuildingNotFoundException;
import com.razysave.repository.device.DeviceRepository;
import com.razysave.repository.property.BuildingRepository;
import com.razysave.repository.property.PropertyRepository;
import com.razysave.repository.property.UnitRepository;
import com.razysave.service.property.BuildingService;
import com.razysave.service.property.UnitService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BuildingServiceImpl implements BuildingService {
    private static final Logger logger = LoggerFactory.getLogger(BuildingServiceImpl.class);
    @Autowired
    private BuildingRepository buildingRepository;
    @Autowired
    private UnitRepository unitRepository;
    @Autowired
    private PropertyRepository propertyRepository;
    @Autowired
    private DeviceRepository deviceRepository;
    @Autowired
    private UnitService unitService;
    private ModelMapper modelMapper = new ModelMapper();

    public List<BuildingListDto> getBuildings(Integer propertyId) {
        logger.info("Enter getBuildings()  method");
        List<Building> buildings = buildingRepository.findByPropertyId(propertyId);
        if (buildings.isEmpty())
            throw new BuildingNotFoundException("Buildings not found");
        else {
            logger.info("End of getBuildings()  method");
            return buildings.stream()
                    .map(this::mapToDto)
                    .collect(Collectors.toList());
        }
    }

    public Building getBuildingById(Integer id) {
        logger.info("Enter getBuildingById(Integer id)  method");
        Optional<Building> buildingOptional = buildingRepository.findById(id);
        if (buildingOptional.isPresent()) {
            logger.info("End of getBuildingById(Integer id)  method");
            return buildingOptional.get();
        } else {
            logger.info("End of getBuildingById(Integer id)  method with Exception");
            throw new BuildingNotFoundException("Building not found with id: " + id);
        }
    }

    public Building addBuilding(Building building) {
       // building.setId(9);
        return buildingRepository.save(building);
    }

    public Building updateBuilding(Integer id, Building updatedBuilding) {
        logger.info("Enter updateBuilding(Integer id, Building updatedBuilding) method");
        Optional<Building> exisitingBuildingOptional = buildingRepository.findById(id);
        if (exisitingBuildingOptional.isPresent()) {
            Building exisitingBuilding = exisitingBuildingOptional.get();
            if (updatedBuilding.getName() != null) {
                exisitingBuilding.setName(updatedBuilding.getName());
            }
            if (updatedBuilding.getUnits() != null) {
                if (exisitingBuilding.getUnits() != null)
                    exisitingBuilding.getUnits().addAll(updatedBuilding.getUnits());
                else exisitingBuilding.setUnits(updatedBuilding.getUnits());
            }
            logger.info("End of updateBuilding(Integer id, Building updatedBuilding) method");
            return buildingRepository.save(exisitingBuilding);
        } else {
            logger.info("End of updateBuilding(Integer id, Building updatedBuilding) method with Exception");
            throw new BuildingNotFoundException("Building not found with id: " + id);
        }
    }

    public void deleteBuildingById(Integer id) {
        logger.info("Enter deleteBuildingById(Integer id)  method");
        Optional<Building> buildingOptional = buildingRepository.findById(id);
        if (buildingOptional.isPresent()) {
            Building building = buildingOptional.get();
            List<Unit> units = building.getUnits();
            Integer propertyId = building.getPropertyId();
            Optional<Property> optionalProperty = propertyRepository.findById(propertyId);
            if (optionalProperty.isPresent()) {
                Property property = optionalProperty.get();
                property.setUnitCount(1);
                property.setUnitCount(property.getUnitCount() - building.getUnitCount());
                List<Building> updatedBuildings = property.getBuilding().stream()
                        .filter(b -> !b.getId().equals(building.getId()))
                        .collect(Collectors.toList());
                property.setBuilding(updatedBuildings);
                property.setBuildingCount(updatedBuildings.size());
                propertyRepository.save(property);
            }
            for (Unit unit : units) {
                unitService.deleteUnitById(unit.getId());
            }
            logger.info("Exit deleteBuildingById(Integer id) method");
            buildingRepository.deleteById(id);
        } else {
            logger.info("Enter deleteBuildingById(Integer id)  method with exception");
            throw new BuildingNotFoundException("Building not found with id: " + id);
        }
    }

    private BuildingListDto mapToDto(Building building) {
        BuildingListDto dto = modelMapper.map(building, BuildingListDto.class);
        dto.setUnitCount(building.getUnitCount());
        dto.setDeviceCount(building.getDeviceCount());
        return dto;
    }
}
