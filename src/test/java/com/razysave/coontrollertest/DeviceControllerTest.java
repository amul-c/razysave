package com.razysave.coontrollertest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.razysave.controller.DeviceController;
import com.razysave.dto.device.ActiveDeviceDto;
import com.razysave.dto.device.DeviceListDto;
import com.razysave.dto.device.InstalledDevices;
import com.razysave.dto.device.OfflineDeviceDto;
import com.razysave.entity.devices.Device;
import com.razysave.exception.DeviceNotFoundException;
import com.razysave.service.devices.DeviceService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
        import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DeviceController.class)
class DeviceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DeviceService deviceService;

    @Test
    void testGetDevices() throws Exception {
        List<DeviceListDto> devices = Collections.singletonList(new DeviceListDto());
        when(deviceService.getDevices(any(), anyInt())).thenReturn(devices);
        mockMvc.perform(get("/device/{name}/info/property/{propertyId}", "deviceName", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(devices.get(0).getId()));
    }

    @Test
    void testGetDeviceById_WithValidId() throws Exception {
        DeviceListDto deviceListDto = new DeviceListDto();
        deviceListDto.setId(1);
        when(deviceService.getDeviceById(1)).thenReturn(deviceListDto);

        mockMvc.perform(get("/device/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void testGetDeviceById_WithInvalidId() throws Exception {
        when(deviceService.getDeviceById(1)).thenThrow(new DeviceNotFoundException("Device not found"));
        mockMvc.perform(get("/device/{id}", 1))
                .andExpect(status().isNoContent());
    }

    @Test
    void testAddDevice() throws Exception {
        Device device = new Device();
        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(device);

        mockMvc.perform(post("/device")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdateDevice() throws Exception {
        Device device = new Device();
        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(device);

        mockMvc.perform(put("/device/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteDevice() throws Exception {
        mockMvc.perform(delete("/device/{id}", 1))
                .andExpect(status().isOk());
    }

    @Test
    void testGetActiveDevices() throws Exception {
        List<ActiveDeviceDto> activeDevices = Collections.singletonList(new ActiveDeviceDto());
        when(deviceService.getDevicesOnAlert(anyInt())).thenReturn(activeDevices);

        mockMvc.perform(get("/device/active-alert/property/{propertyId}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(activeDevices.get(0).getId()));
    }

    @Test
    void testGetOfflineDevice() throws Exception {
        List<OfflineDeviceDto> offlineDevices = Collections.singletonList(new OfflineDeviceDto());
        when(deviceService.getOfflineDevices(anyInt())).thenReturn(offlineDevices);

        mockMvc.perform(get("/device/offline/property/{propertyId}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(offlineDevices.get(0).getId()));
    }

    @Test
    void testGetInstalledDevices() throws Exception {
        InstalledDevices installedDevices = new InstalledDevices();
        Device device=new Device();
        device.setPropertyId(1);
        when(deviceService.getInstalledDevices(anyInt())).thenReturn(installedDevices);

        mockMvc.perform(get("/device/installed-devices/property/{propertyId}", device.getPropertyId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

}
