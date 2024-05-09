package com.razysave.serviceTest;

import com.razysave.dto.device.ActiveDeviceDto;
import com.razysave.dto.device.DeviceListDto;
import com.razysave.dto.device.InstalledDevices;
import com.razysave.dto.device.OfflineDeviceDto;
import com.razysave.entity.devices.Device;
import com.razysave.entity.property.Building;
import com.razysave.entity.property.Property;
import com.razysave.entity.property.Unit;
import com.razysave.exception.DeviceNotFoundException;
import com.razysave.service.devices.DeviceService;
import com.razysave.service.property.BuildingService;
import com.razysave.service.property.PropertyService;
import com.razysave.service.property.UnitService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class DeviceServiceImplTest {
    @Autowired
    private DeviceService deviceService;
    @Autowired
    private PropertyService propertyService;
    @Autowired
    private BuildingService buildingService;
    @Autowired
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
        buildingService.addBuilding(building);
        unitService.addUnit(unit);
        device.setName("Fire Alarm");
        device.setPropertyId(property.getId());
        Device deviceSave = deviceService.addDevice(device);
        List<DeviceListDto> deviceListDtos = deviceService.getDevices("Fire Alarm", device.getPropertyId());
        Assertions.assertTrue(deviceListDtos.size() > 0);
    }

    @Test
    public void getDevicesOnAlertSuccessTest() {
        Property property = new Property();
        property.setId(200);
        Device device = new Device();
        device.setUnitId(1);
        device.setId(200);
        device.setPropertyId(200);
        device.setStatus("alert");
        Device deviceSave = deviceService.addDevice(device);
        List<ActiveDeviceDto> deviceDtos = deviceService.getDevicesOnAlert(deviceSave.getPropertyId());
        Assertions.assertTrue(deviceDtos.size() > 0);
    }

    @Test
    public void getDeviceByIdSuccessTest() {
        Device device = new Device();
        device.setUnitId(1);
        device.setId(2000);
        Device deviceSaved = deviceService.addDevice(device);
        DeviceListDto deviceGet = deviceService.getDeviceById(deviceSaved.getId());
        Assertions.assertEquals(deviceSaved.getId(), deviceGet.getId());
        Assertions.assertNotNull(deviceGet);
    }

    @Test
    public void getDeviceByIdFailerTest() {
        Assertions.assertThrows(DeviceNotFoundException.class, () -> deviceService.getDeviceById(200));
    }

    @Test
    public void updateDevice() {
        Device device = new Device();
        device.setName("test1");
        Device savedDevice = deviceService.addDevice(device);
        savedDevice.setName("Test2");
        Device updatedDevice = deviceService.updateDevice(savedDevice.getId(), savedDevice);
        Assertions.assertEquals("Test2", updatedDevice.getName());
        Assertions.assertNotNull(updatedDevice);
    }

    @Test
    public void deleteBuildingTest() {
        Device device = new Device();
        device.setId(200);
        deviceService.addDevice(device);
        deviceService.deleteDeviceById(device.getId());
        Assertions.assertThrows(DeviceNotFoundException.class, () -> deviceService.getDeviceById(device.getId()));
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
        propertyService.addProperty(property);
        buildingService.addBuilding(building);
        unitService.addUnit(unit);
        deviceService.addDevice(device);
        List<OfflineDeviceDto> deviceDtos = deviceService.getOfflineDevices(property.getId());
        Assertions.assertTrue(deviceDtos.size() > 0);
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
        propertyService.addProperty(property);
        buildingService.addBuilding(building);
        unitService.addUnit(unit);
        deviceService.addDevice(device1);
        deviceService.addDevice(device2);
        InstalledDevices installedDevices = deviceService.getInstalledDevices(200);
        Assertions.assertTrue(installedDevices.getDeviceCount().size() > 0);
    }
}
