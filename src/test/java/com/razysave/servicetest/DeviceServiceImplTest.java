package com.razysave.servicetest;

import com.razysave.dto.device.ActiveDeviceDto;
import com.razysave.dto.device.DeviceListDto;
import com.razysave.dto.device.InstalledDevices;
import com.razysave.dto.device.OfflineDeviceDto;
import com.razysave.entity.devices.Device;
import com.razysave.entity.property.Building;
import com.razysave.entity.property.Property;
import com.razysave.entity.property.Unit;
import com.razysave.exception.DeviceNotFoundException;
import com.razysave.repository.device.DeviceRepository;
import com.razysave.repository.property.BuildingRepository;
import com.razysave.repository.property.UnitRepository;
import com.razysave.service.property.BuildingService;
import com.razysave.service.property.UnitService;
import com.razysave.service.serviceImpl.devices.DeviceServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@SpringBootTest
public class DeviceServiceImplTest {
    @InjectMocks
    private DeviceServiceImpl deviceService;
    @Mock
    private DeviceRepository deviceRepository;
    @Mock
    private UnitRepository unitRepository;
    @Mock
    private BuildingRepository buildingRepository;
    @Mock
    private BuildingService buildingService;

    @Mock
    private UnitService unitService;
    @Test
    public void getDeviceSuccessTest() {
        Property property = new Property();
        property.setId(200);
        Device device = new Device();
        device.setName("Test1");
        Unit unit=new Unit();
        unit.setId(200);
        unit.setBuildingId(200);
        device.setUnitId(200);
        device.setId(200);
        Building building =new Building();
        building.setId(200);
        building.setName("beldon");
        device.setName("Fire Alarm");
        device.setPropertyId(property.getId());
        when(deviceRepository.findByNameAndPropertyId(device.getName(),device.getPropertyId()))
                .thenReturn(Stream.of(device).collect(Collectors.toList()));
        when(unitRepository.findById(device.getUnitId())).thenReturn(Optional.of(unit));
        when(buildingRepository.findById(device.getUnitId())).thenReturn(Optional.of(building));
        List<DeviceListDto> deviceListDtos = deviceService.getDevices("Fire Alarm", device.getPropertyId());
        Assertions.assertTrue(deviceListDtos.size() > 0);
    }

    @Test
    public void getDevicesOnAlertSuccessTest() {
        Property property = new Property();
        property.setId(200);
        Unit unit = new Unit();
        unit.setId(200);
        Device device = new Device();
        device.setUnitId(unit.getId());
        device.setId(200);
        device.setPropertyId(property.getId());
        device.setStatus("alert");
        when(deviceRepository.findByStatusAndPropertyId(device.getStatus(),device.getPropertyId())).thenReturn(Stream.of(device)
                .collect(Collectors.toList()));
        when(unitRepository.findById(device.getUnitId())).thenReturn(Optional.of(unit));
        List<ActiveDeviceDto> deviceDtos = deviceService.getDevicesOnAlert(device.getPropertyId());
        Assertions.assertTrue(deviceDtos.size() > 0);
    }

    @Test
    public void getDeviceByIdSuccessTest() {
        Device device = new Device();
        device.setUnitId(1);
        device.setId(2000);
        when(deviceRepository.findById(device.getId())).thenReturn(Optional.of(device));
        DeviceListDto deviceGet = deviceService.getDeviceById(device.getId());
        Assertions.assertEquals(device.getId(), deviceGet.getId());
        Assertions.assertNotNull(deviceGet);
    }

    @Test
    public void getDeviceByIdFailerTest() {
        when(deviceRepository.findById(200)).thenReturn(Optional.empty());
        assertThrows(DeviceNotFoundException.class, () -> deviceService.getDeviceById(200));
    }

    @Test
    public void updateDevice() {
        Integer deviceId = 123;
        Integer unitId = 456;
        Integer buildingId = 789;
        Device updatedDevice = new Device();
        updatedDevice.setId(deviceId);
        updatedDevice.setUnitId(unitId);
        updatedDevice.setName("updated");
        Device existingDevice = new Device();
        existingDevice.setId(deviceId);
        existingDevice.setUnitId(unitId);
        existingDevice.setName("exisiting");

        Unit unit = new Unit();
        unit.setId(unitId);
        unit.setBuildingId(buildingId);
        unit.setDeviceList(List.of(existingDevice));

        Building building = new Building();
        building.setId(buildingId);
        building.setDevices(List.of(existingDevice));

        when(deviceRepository.findById(deviceId)).thenReturn(Optional.of(existingDevice));
        when(deviceRepository.save(updatedDevice)).thenReturn(updatedDevice);
        when(unitRepository.findById(unitId)).thenReturn(Optional.of(unit));
        when(buildingRepository.findById(buildingId)).thenReturn(Optional.of(building));
        Device result=deviceService.updateDevice(deviceId, updatedDevice);
        Assertions.assertEquals("updated", result.getName());
        verify(deviceRepository, times(1)).findById(deviceId);
        verify(deviceRepository, times(1)).save(updatedDevice);
        verify(unitRepository, times(1)).findById(unitId);
        verify(buildingRepository, times(1)).findById(buildingId);
        verify(buildingService, times(1)).updateBuilding(eq(buildingId), any(Building.class));
        verify(unitService, times(1)).updateUnit(eq(unitId), any(Unit.class));
    }

    @Test
    public void deleteBuildingTest() {
        Device device = new Device();
        device.setId(200);
        when(deviceRepository.findById(device.getId())).thenReturn(Optional.of(device));
        deviceService.deleteDeviceById(device.getId());
        verify(deviceRepository, times(1)).deleteById(eq(device.getId()));
    }

    @Test
    public void testDeleteDeviceById_DeviceNotFound() {
        Integer deviceId = 1;
        when(deviceRepository.findById(deviceId)).thenReturn(Optional.empty());
        assertThrows(DeviceNotFoundException.class, () -> deviceService.deleteDeviceById(deviceId));
        verify(deviceRepository, times(1)).findById(deviceId);
        verify(deviceRepository, never()).deleteById(deviceId); // Ensure deleteById is not called
    }

    @Test
    public void getOfflineDevicesSuccessTest() {
        Property property=new Property();
        property.setId(900);
        Building building=new Building();
        building.setId(200);
        building.setPropertyId(property.getId());
        Unit unit = new Unit();
        unit.setId(200);
        unit.setBuildingId(building.getId());
        Device device = new Device();
        device.setId(200);
        device.setUnitId(unit.getId());
        device.setConnection("offline");
        device.setPropertyId(property.getId());
        when(deviceRepository.findByPropertyIdAndConnection(device.getPropertyId(), device.getConnection())).thenReturn(Stream.of(device)
                .collect(Collectors.toList()));
        when(unitRepository.findById(device.getUnitId())).thenReturn(Optional.of(unit));
        List<OfflineDeviceDto> deviceDtos = deviceService.getOfflineDevices(property.getId());
        Assertions.assertEquals(1,deviceDtos.size());
    }
    @Test
    public void getInstallDevicesTest() {
        Property property=new Property();
        property.setId(200);
        Building building=new Building();
        building.setId(200);
        building.setPropertyId(200);
        Unit unit = new Unit();
        unit.setId(200);
        unit.setBuildingId(200);
        List<Device> deviceList=new ArrayList<>();
        Device device1 = new Device();
        device1.setId(200);
        device1.setUnitId(200);
        device1.setName("temprature");
        device1.setPropertyId(property.getId());
        Device device2 = new Device();
        device2.setId(200);
        device2.setUnitId(200);
        device2.setPropertyId(property.getId());
        device2.setName("Humidity");
        deviceList.add(device1);
        deviceList.add(device2);
        unit.setDeviceList(deviceList);
        when(deviceRepository.findByPropertyId(device1.getPropertyId())).thenReturn(deviceList);
        InstalledDevices installedDevices = deviceService.getInstalledDevices(property.getId());
        Assertions.assertEquals(2,installedDevices.getDeviceCount().size());
    }
}
