package com.razysave.service.devices;

import com.razysave.dto.device.ActiveDeviceDto;
import com.razysave.dto.device.DeviceListDto;
import com.razysave.dto.device.InstalledDevices;
import com.razysave.dto.device.OfflineDeviceDto;
import com.razysave.entity.devices.Device;
import java.util.List;

public interface DeviceService {
    public List<DeviceListDto> getDevices(String deviceName,Integer propertyId);

    public List<ActiveDeviceDto> getDevicesOnAlert(Integer propertyId);
    public List<OfflineDeviceDto> getOfflineDevices(Integer propertyId);
    public InstalledDevices getInstalledDevices(Integer propertyId);

    public Device addDevice(Device device);

    public DeviceListDto getDeviceById(Integer id);

    public Device updateDevice(Integer deviceId, Device updatedDevice);

    public void deleteDeviceById(Integer deviceId);
}
