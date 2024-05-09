package com.razysave.service.serviceImpl.devices;

import com.razysave.dto.device.ActiveDeviceDto;
import com.razysave.dto.device.DeviceListDto;
import com.razysave.dto.device.InstalledDevices;
import com.razysave.dto.device.OfflineDeviceDto;
import com.razysave.entity.devices.Device;
import com.razysave.entity.property.Building;
import com.razysave.entity.property.Unit;
import com.razysave.exception.BuildingNotFoundException;
import com.razysave.exception.DeviceNotFoundException;
import com.razysave.exception.UnitNotFoundException;
import com.razysave.repository.device.DeviceRepository;
import com.razysave.repository.property.BuildingRepository;
import com.razysave.repository.property.PropertyRepository;
import com.razysave.repository.property.UnitRepository;
import com.razysave.service.devices.DeviceService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DeviceServiceImpl implements DeviceService {
    private static final Logger logger = LoggerFactory.getLogger(DeviceServiceImpl.class);
    @Autowired
    private DeviceRepository deviceRepository;
    @Autowired
    private UnitRepository unitRepository;
    @Autowired
    private BuildingRepository buildingRepository;
    @Autowired
    private PropertyRepository propertyRepository;
    private ModelMapper modelMapper = new ModelMapper();


    public List<DeviceListDto> getDevices(String name,Integer propertyId) {
        logger.info("Enter of getDevices(String name) method");
        List<Device> devices = deviceRepository.findByNameAndPropertyId(name,propertyId);
        if (devices.isEmpty()) {
            logger.info("End of getDevices(String name) method with exception");
            throw new DeviceNotFoundException("Device not found with Name: " + name);
        }
        logger.info("End of getDevices(String name) method");
        return devices.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public List<ActiveDeviceDto> getDevicesOnAlert(Integer propertyId) {
        logger.info("Enter of getDevicesOnAlert() method");
        List<Device> devices = deviceRepository.findByStatusAndPropertyId("alert",propertyId);
        if (devices.isEmpty()) {
            logger.info("End of getDevicesOnAlert() method with exception");
            throw new DeviceNotFoundException("Device not found with Active alerts: ");
        }
        logger.info("End of getDevicesOnAlert() method");
        return devices.stream()
                .map(this::mapToActiveDeviceDto)
                .collect(Collectors.toList());
    }

    public DeviceListDto getDeviceById(Integer id) {
        logger.info("Enter of getDeviceById(String id)  method");
        Optional<Device> deviceOptional = deviceRepository.findById(id);
        if (deviceOptional.isPresent()) {
            logger.info("End of getDeviceById(String id)  method");
            return modelMapper.map(deviceOptional.get(), DeviceListDto.class);
        } else {
            logger.info("End of getDeviceById(String id) method with Exception");
            throw new DeviceNotFoundException("Device not found with id: " + id);
        }
    }

    public Device addDevice(Device device) {
        //device.setId(6);
        Optional<Unit> unitOptional = unitRepository.findById(device.getUnitId());
        if (unitOptional.isPresent()) {
            Unit unit = unitOptional.get();
            List<Device> devices = new ArrayList<>();
            if(unit.getDeviceList()!=null) {
                devices =unit.getDeviceList();
                devices.add(device);

            }
            else
                devices.add(device);
            unit.setDeviceList(devices);
            unitRepository.save(unit);
        } else
            throw new RuntimeException("Unit not found with UnitId: " + device.getUnitId());

        return deviceRepository.save(device);
    }

    public Device updateDevice(Integer deviceId, Device updatedDevice) {
        logger.info("Enter of updateDevice(String deviceId,DeviceListDto updatedDevice) method");
        Optional<Device> existingDeviceOptional = deviceRepository.findById(deviceId);
        if (existingDeviceOptional.isPresent()) {
            Device existingDevice = existingDeviceOptional.get();

            if (updatedDevice.getUnitId() != null) {
                existingDevice.setInstalledDate(updatedDevice.getInstalledDate());
            }
            if (updatedDevice.getName() != null) {
                existingDevice.setName(updatedDevice.getName());
            }
            if (updatedDevice.getBattery() != null) {
                existingDevice.setBattery(updatedDevice.getBattery());
            }
            if (updatedDevice.getConnection() != null) {
                existingDevice.setConnection(updatedDevice.getConnection());
            }
            if (updatedDevice.getInstalledDate() != null) {
                existingDevice.setInstalledDate(updatedDevice.getInstalledDate());
            }
            if (updatedDevice.getStatus() != null) {
                existingDevice.setStatus(updatedDevice.getStatus());
            }
            if (updatedDevice.getOfflineTime() != null) {
                existingDevice.setOfflineTime(updatedDevice.getOfflineTime());
            }
            if (updatedDevice.getOfflineSince() != null) {
                existingDevice.setOfflineSince(updatedDevice.getOfflineSince());
            }
            logger.info("End of updateDevice(String deviceId,DeviceListDto updatedDevice) method");
            return deviceRepository.save(existingDevice);
        } else {
            logger.info("End of updateDevice(String deviceId,DeviceListDto updatedDevice) method with Exception");
            throw new DeviceNotFoundException("Device not found with deviceId: " + deviceId);
        }
    }

    public void deleteDeviceById(Integer id) {
        logger.info("Enter deleteDeviceById(Integer id) method");
        Optional<Device> deviceOptional = deviceRepository.findById(id);
        if (deviceOptional.isPresent()) {
            Device device = deviceOptional.get();
            deviceRepository.deleteById(id);
        } else
            throw new DeviceNotFoundException("Device not found with id: " + id);
    }

    public List<OfflineDeviceDto> getOfflineDevices(Integer propertyId)
    {
        logger.info("Enter  getOfflineDevices(Integer propertyId) method");
        List<Device> offlineDevices = deviceRepository.findByPropertyIdAndConnection(propertyId, "offline");
        if (offlineDevices.isEmpty()) {
            logger.info("End of getOfflineDevice() method with exception");
            throw new DeviceNotFoundException("Device not found with offline connection");
        }
        logger.info("End of getOfflineDevices() method");
        return offlineDevices.stream()
                .map(this::mapToOfflineDevice)
                .collect(Collectors.toList());
    }

    public InstalledDevices getInstalledDevices(Integer propertyId) {
        logger.info("Enter getInstalledDevices(Integer propertyId) method");
        InstalledDevices installedDevices = new InstalledDevices();
        HashMap<String, Integer> deviceCountMap = new HashMap<>();
        List<Device> deviceList = deviceRepository.findByPropertyId(propertyId);
                    if (deviceList != null) {
                        for (Device device : deviceList) {
                            String deviceName = device.getName();
                            deviceCountMap.put(deviceName, deviceCountMap.getOrDefault(deviceName, 0) + 1);
                        }
                    }
        installedDevices.setDeviceCount(deviceCountMap);
        logger.info("Exit getInstalledDevices(Integer propertyId) method");
        return installedDevices;
    }
    private ActiveDeviceDto mapToActiveDeviceDto(Device device) {
        Optional<Unit> unitOptional = unitRepository.findById(device.getUnitId());
        Unit unit = unitOptional.get();
        ActiveDeviceDto dto = modelMapper.map(device, ActiveDeviceDto.class);
        dto.setBuildingId(unit.getBuildingId());
        dto.setUnitId(unit.getId());
        return dto;
    }


    private OfflineDeviceDto mapToOfflineDevice(Device device) {
        Optional<Unit> unitOptional = unitRepository.findById(device.getUnitId());
        Unit unit = unitOptional.get();
        OfflineDeviceDto dto = modelMapper.map(device, OfflineDeviceDto.class);
        dto.setBuildingId(unit.getBuildingId());
        dto.setUnitId(unit.getId());
        return dto;
    }

    private DeviceListDto mapToDto(Device device) {
        Optional<Unit> unitOptional = unitRepository.findById(device.getUnitId());
        if (unitOptional.isPresent()) {
            Unit unit = unitOptional.get();
            Optional<Building> buildingOptional = buildingRepository.findById(unit.getBuildingId());
            if (buildingOptional.isPresent()) {
                Building building = buildingOptional.get();
                String buildingName = building.getName();
                String unitName = unit.getName();
                DeviceListDto dto = modelMapper.map(device, DeviceListDto.class);
                dto.setPropertyName(buildingName + unitName);
                return dto;
            } else {
                throw new BuildingNotFoundException("Building Not Found with given id");
            }
        } else
            throw new UnitNotFoundException("Unit not Found with given id");
    }
}
