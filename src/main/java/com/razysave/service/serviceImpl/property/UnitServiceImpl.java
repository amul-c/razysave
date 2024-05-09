package com.razysave.service.serviceImpl.property;

import com.razysave.dto.device.DeviceListDto;
import com.razysave.dto.unit.UnitInfoDto;
import com.razysave.dto.unit.UnitListDto;
import com.razysave.entity.devices.Device;
import com.razysave.entity.property.Building;
import com.razysave.entity.property.Unit;
import com.razysave.entity.tenant.Tenant;
import com.razysave.exception.UnitNotFoundException;
import com.razysave.repository.property.BuildingRepository;
import com.razysave.repository.property.UnitRepository;
import com.razysave.service.devices.DeviceService;
import com.razysave.service.property.TenantService;
import com.razysave.service.property.UnitService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UnitServiceImpl implements UnitService {
    @Autowired
    private UnitRepository unitRepository;
    @Autowired
    private BuildingRepository buildingRepository;
    @Autowired
    private DeviceService deviceService;
    @Autowired
    private TenantService tenantService;
    private ModelMapper modelMapper = new ModelMapper();
    private static final Logger logger = LoggerFactory.getLogger(UnitServiceImpl.class);

    public List<UnitListDto> getUnits(Integer buildingId) {
        logger.info("Enter getUnits(Integer buildingId) ");
        List<Unit> units = unitRepository.findByBuildingId(buildingId);
        if (units.isEmpty()) {
            logger.info("Exit getUnits(Integer buildingId) with buildingId {}", buildingId);
            throw new UnitNotFoundException("No unit found");
        }
        return units.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }
    public List<UnitListDto> getUnitsByProperty(Integer propertyId)
    {
        logger.info("Enter getUnitsByProperty(Integer propertyId) with propertyId {}", propertyId);
        List<Unit> units = unitRepository.findByPropertyId(propertyId);
        if (units.isEmpty()) {
            logger.info("Exit  getUnitsByProperty(Integer propertyId) with propertyId {}", propertyId);
            throw new UnitNotFoundException("No unit found");
        }
        logger.info("Exit getUnitsByProperty(Integer propertyId)");
        return units.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }
    @Override
    public UnitInfoDto getUnitById(Integer id) {
        logger.info("Enter getUnitById(Integer id)");
        Optional<Unit> unitOptional = unitRepository.findById(id);
        if (unitOptional.isPresent()) {
            Unit unit = unitOptional.get();
            Tenant tenant = unit.getTenant();

            UnitInfoDto unitInfoDto = modelMapper.map(unit, UnitInfoDto.class);
            if (tenant != null) {
                unitInfoDto.setTenantName(tenant.getName());
                unitInfoDto.setTenantEmail(tenant.getEmail());
                unitInfoDto.setTenantPhone(tenant.getPhone());
                unitInfoDto.setStatus("Occupied");
            } else
                unitInfoDto.setStatus("unoccupied");
            unitInfoDto.setDeviceCount(unitInfoDto.countDevicesByName());
            logger.info("Exit getUnitById(Integer id)");
            return unitInfoDto;
        } else {
            logger.info("Exit getUnitById(Integer id) exception thrown");
            throw new UnitNotFoundException("Unit not found with Id : " + id);
        }
    }

    public Unit addUnit(Unit unit) {
        logger.info("Enter and Exit addUnit(Unit unit)");
        return unitRepository.save(unit);
    }

    public Unit updateUnit(Integer id, Unit updatedUnit) {
        logger.info("Enter updateUnit(Integer id, Unit updatedUnit)");
        Optional<Unit> exisitingUnitOptional = unitRepository.findById(id);
        if (exisitingUnitOptional.isPresent()) {
            Unit existingUnit = exisitingUnitOptional.get();
            if (updatedUnit.getName() != null) {
                existingUnit.setName(updatedUnit.getName());
            }
            if (updatedUnit.getTenant() != null) {
                existingUnit.setTenant(updatedUnit.getTenant());
                existingUnit.setOccupied(true);
            }
            if (updatedUnit.getDeviceList() != null) {
                if (existingUnit.getDeviceList() != null)
                    existingUnit.getDeviceList().addAll(updatedUnit.getDeviceList());
                else existingUnit.setDeviceList(updatedUnit.getDeviceList());
            }
            logger.info("Exit updateUnit(Integer id, Unit updatedUnit)");
            return unitRepository.save(existingUnit);
        } else {
            logger.info("Exit updateUnit(Integer id, Unit updatedUnit) exception thrown");
            throw new RuntimeException("Unit not found with Id : " + updatedUnit.getId());
        }
    }

    public void deleteUnitById(Integer id) {
        logger.info("Enter deleteUnitById(Integer id)");
        Optional<Unit> unitOptional = unitRepository.findById(id);
        if (unitOptional.isPresent()) {
            Unit unit = unitOptional.get();
            Integer buildingId = unit.getBuildingId();
            Optional<Building> buildingOptional = buildingRepository.findById(buildingId);
            if (buildingOptional.isPresent()) {
                Building building = buildingOptional.get();
                List<Unit> units = building.getUnits().stream()
                        .filter(b -> !b.getId().equals(unit.getId()))
                        .collect(Collectors.toList());
                building.setUnits(units);
                buildingRepository.save(building);
            }
            List<Device> devicesToUpdate = new ArrayList<>();
           Tenant tenant = unit.getTenant();
            if (tenant != null) {
                tenantService.deleteTenantById(tenant.getId());
            }
            List<Device> devices = unit.getDeviceList();
            if(devices!=null)
            for (Device device : devices) {
                deviceService.deleteDeviceById(device.getId());
            }
            logger.info("Exit deleteUnitById(Integer id)");
            unitRepository.deleteById(id);
        } else {
            throw new UnitNotFoundException("Unit Not found with id :" + id);
        }
    }

    private UnitListDto mapToDto(Unit unit) {
        UnitListDto dto = modelMapper.map(unit, UnitListDto.class);
        List<DeviceListDto> devices = dto.getDeviceList();
        Tenant tenant = unit.getTenant();
        if (tenant != null) {
            dto.setTenantName(tenant.getName());
        }
        return dto;
    }

}