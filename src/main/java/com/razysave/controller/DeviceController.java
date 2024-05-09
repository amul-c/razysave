package com.razysave.controller;

import com.razysave.dto.device.ActiveDeviceDto;
import com.razysave.dto.device.DeviceListDto;
import com.razysave.dto.device.InstalledDevices;
import com.razysave.dto.device.OfflineDeviceDto;
import com.razysave.entity.devices.Device;
import com.razysave.exception.DeviceNotFoundException;
import com.razysave.response.ResponseHandler;
import com.razysave.service.devices.DeviceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;


@RestController
@RequestMapping("/device")
public class DeviceController {
    private static final Logger logger = LoggerFactory.getLogger(DeviceController.class);
    @Autowired
    private DeviceService deviceService;

    @GetMapping("/{name}/info/property/{propertyId}")
    public ResponseEntity<Object> getDevices(@PathVariable String name,@PathVariable Integer propertyId) {
        try {
            logger.info("Enter getDevices(@PathVariable String name,@PathVariable Integer propertyId)");
            List<DeviceListDto> devices = deviceService.getDevices(name,propertyId);
            logger.info(" Exit getDevices(@PathVariable String name,@PathVariable Integer propertyId)");
            return ResponseEntity.ok(devices);
        } catch (DeviceNotFoundException e) {
            logger.error("Exit getDevices(@PathVariable String name,@PathVariable Integer propertyId) with an DeviceNotFoundException exception occurred {},", e.getMessage());
            return ResponseEntity.ok(Collections.emptyList());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getDeviceById(@PathVariable Integer id) {
        try {
            logger.info("Enter getDeviceById(@PathVariable Integer id) with {}", id);
            DeviceListDto deviceListDto = deviceService.getDeviceById(id);
            logger.info("Exit getDeviceById(@PathVariable Integer id) with {}", id);
            return ResponseEntity.ok(deviceListDto);
        } catch (DeviceNotFoundException e) {
            logger.error("Exit getDeviceById(@PathVariable Integer id) an DeviceNotFoundException exception occurred, {}", e.getMessage());
            return ResponseEntity.noContent().build();
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<Object> updateDevice(@PathVariable Integer id, @RequestBody Device device) {
        try {
            logger.info("Enter updateDevice(@PathVariable Integer id, @RequestBody Device device) with {}", id);
            deviceService.updateDevice(id, device);
            logger.info("Exit updateDevice(@PathVariable Integer id, @RequestBody Device device)");
            return ResponseHandler.generateResponse("updated succesfully", HttpStatus.CREATED, device);
        } catch (DeviceNotFoundException e) {
            logger.error("Exit updateDevice(@PathVariable Integer id, @RequestBody Device device) an DeviceNotFoundException exception occurred, {}", e.getMessage());
            return ResponseEntity.noContent().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteDevice(@PathVariable Integer id) {
        try {
            logger.info("Enter deleteDevice(@PathVariable Integer id) with {}", id);
            deviceService.deleteDeviceById(id);
            logger.info("Exit deleteDevice(@PathVariable Integer id) with {}", id);
            return ResponseHandler.generateResponse("deleted succesfully", HttpStatus.CREATED, id);
        } catch (DeviceNotFoundException e) {
            logger.error("Exit deleteDevice(@PathVariable Integer id) with an DeviceNotFoundException exception occurred, {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    @GetMapping("/active-alert/property/{propertyId}")
    public ResponseEntity<Object> getActiveDevices(@PathVariable Integer propertyId) {
        try {
            logger.info("Enter deleteDevice(@PathVariable Integer id) with {},id", propertyId);
            List<ActiveDeviceDto> devices = deviceService.getDevicesOnAlert(propertyId);
            logger.info("Exit deleteDevice(@PathVariable Integer id)");
            return ResponseEntity.ok(devices);
        } catch (DeviceNotFoundException e) {
            logger.error("Exit deleteDevice(@PathVariable Integer id) an DeviceNotFoundException exception occurred {},", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    @GetMapping("/offline/property/{propertyId}")
    public ResponseEntity<Object> getOfflineDevice(@PathVariable Integer propertyId) {
        try {
            logger.info("Enter deleteDevice(@PathVariable Integer id) with id {}", propertyId);
            List<OfflineDeviceDto> devices = deviceService.getOfflineDevices(propertyId);
            logger.info("Exit deleteDevice(@PathVariable Integer id)");
            return ResponseEntity.ok(devices);
        } catch (DeviceNotFoundException e) {
            logger.error("Exit deleteDevice(@PathVariable Integer id) an DeviceNotFoundException exception occurred {},", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    @GetMapping("/installed-devices/property/{propertyId}")
    public ResponseEntity<Object> getInstalledDevices(@PathVariable Integer propertyId) {
        try {
            logger.info("Enter getInstalledDevices(@PathVariable Integer propertyId) with id {}", propertyId);
            InstalledDevices devices = deviceService.getInstalledDevices(propertyId);
            logger.info("Exit getInstalledDevices(@PathVariable Integer propertyId) ");
            return ResponseEntity.ok(devices);
        } catch (DeviceNotFoundException e) {
            logger.error("Exit getInstalledDevices(@PathVariable Integer propertyId) An DeviceNotFoundException exception occurred {},", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
