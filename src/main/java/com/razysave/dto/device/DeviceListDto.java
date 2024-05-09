package com.razysave.dto.device;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Map;

@Getter
@Setter
public class DeviceListDto {
    private Integer id;
    private Map<String, String> reading;
    private String name;
    private String propertyName;
    private Integer battery;
    private String connection;
    private String installedDate;
}
