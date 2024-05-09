package com.razysave.dto.unit;

import com.razysave.entity.devices.Device;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class UnitInfoDto {
    private Integer id;
    private String name;
    private List<Device> deviceList;
    private Map<String, Integer> deviceCount;
    private String tenantName;
    private String tenantPhone;
    private String tenantEmail;
    private String status;

    public Map<String, Integer> countDevicesByName() {
        Map<String, Integer> deviceCountMap = new HashMap<>();
        if (deviceList != null) {
            for (Device device : deviceList) {
                String deviceName = device.getName();
                deviceCountMap.put(deviceName, deviceCountMap.getOrDefault(deviceName, 0) + 1);
            }
        }
        return deviceCountMap;
    }
}