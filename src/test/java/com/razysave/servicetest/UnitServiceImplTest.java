package com.razysave.servicetest;

import com.razysave.dto.unit.UnitInfoDto;
import com.razysave.dto.unit.UnitListDto;
import com.razysave.entity.devices.Device;
import com.razysave.entity.property.Building;
import com.razysave.entity.property.Property;
import com.razysave.entity.property.Unit;
import com.razysave.entity.tenant.Tenant;
import com.razysave.exception.UnitNotFoundException;
import com.razysave.repository.property.BuildingRepository;
import com.razysave.repository.property.PropertyRepository;
import com.razysave.repository.property.UnitRepository;
import com.razysave.service.devices.DeviceService;
import com.razysave.service.property.BuildingService;
import com.razysave.service.property.TenantService;
import com.razysave.service.serviceImpl.property.UnitServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UnitServiceImplTest {

    @Mock
    private UnitRepository unitRepository;
    @Mock
    private BuildingRepository buildingRepository;
   @InjectMocks
    private UnitServiceImpl unitService;
    @Mock
    private BuildingService buildingService;
    @Mock
    private PropertyRepository propertyRepository;

    @Mock
    private TenantService tenantService;

    @Mock
    private DeviceService deviceService;

    @Test
    public void getUnitsSuccessTest() {
        Unit unit = new Unit();
        unit.setName("Test1");
        unit.setId(200);
        unit.setBuildingId(200);
        when(unitRepository.findByBuildingId(unit.getBuildingId()))
                .thenReturn(Stream.of(unit).collect(Collectors.toList()));
        List<UnitListDto> unitListDtos = unitService.getUnits(unit.getBuildingId());
        Assertions.assertTrue(unitListDtos.size() > 0);
    }

    @Test
    public void updateUnit() {
        Unit unit = new Unit();
        unit.setId(200);
        unit.setName("test1");
        Unit updateUnit = new Unit();
        updateUnit.setName("Test2");
        updateUnit.setId(200);
        Building building = new Building();
        building.setId(1);
        building.setUnits(List.of(unit));
        when(unitRepository.findById(unit.getId())).thenReturn(Optional.of(unit));
        when(buildingRepository.findById(unit.getBuildingId())).thenReturn(Optional.of(building));
        when(unitRepository.save(updateUnit)).thenReturn(updateUnit);
        Unit result = unitService.updateUnit(updateUnit.getId(), updateUnit);
        Assertions.assertNotNull(result);
        Assertions.assertEquals("Test2", result.getName());
        verify(unitRepository, times(1)).findById(unit.getId());
        verify(unitRepository, times(1)).save(updateUnit);
    }

    @Test
    public void getUnitByIdSuccessTest() {
        Unit unit = new Unit();
        unit.setId(200);
        when(unitRepository.findById(unit.getId())).thenReturn(Optional.of(unit));
        UnitInfoDto unitGet = unitService.getUnitById(unit.getId());
        Assertions.assertEquals(unitGet.getId(), unitGet.getId());
        Assertions.assertNotNull(unitGet);
    }

    @Test
    public void getUnitByIdFailerTest() {
        when(unitRepository.findById(300)).thenReturn(Optional.empty());
        Assertions.assertThrows(UnitNotFoundException.class, () -> unitService.getUnitById(900));
    }

    @Test
    public void deleteUnitTest() {
        Integer unitId = 1;
        Integer buildingId = 1;
        Integer propertyId = 1;
        Integer tenantId = 1;
        Integer deviceId = 1;
        Unit unit = new Unit();
        unit.setId(unitId);
        unit.setBuildingId(buildingId);
        unit.setPropertyId(propertyId);
        Tenant tenant=new Tenant();
        tenant.setId(tenantId);
        tenant.setName("ralph");
        unit.setTenant(tenant);
        Device device=new Device();
        device.setId(deviceId);
        unit.setDeviceList(List.of(device));
        Building building = new Building();
        building.setId(buildingId);
        building.setUnits(List.of(unit));
        Property property = new Property();
        property.setId(propertyId);
        property.setUnitCount(1);
        when(unitRepository.findById(unitId)).thenReturn(Optional.of(unit));
        when(buildingRepository.findById(buildingId)).thenReturn(Optional.of(building));
        when(propertyRepository.findById(propertyId)).thenReturn(Optional.of(property));
        unitService.deleteUnitById(unitId);
        verify(unitRepository, times(1)).findById(unitId);
        verify(buildingRepository, times(1)).findById(buildingId);
        verify(buildingService, times(1)).updateBuilding(eq(buildingId), any(Building.class));
        verify(propertyRepository, times(1)).findById(propertyId);
        verify(propertyRepository, times(1)).save(property);
        verify(tenantService, times(1)).deleteTenantById(tenantId);
        verify(deviceService, times(1)).deleteDeviceById(deviceId);
        verify(unitRepository, times(1)).deleteById(unitId);}
}
