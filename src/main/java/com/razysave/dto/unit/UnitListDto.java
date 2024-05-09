package com.razysave.dto.unit;

import com.razysave.dto.device.DeviceListDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UnitListDto {
    private Integer id;
    private String name;
    private List<DeviceListDto> deviceList;
    private String tenantName;
}
