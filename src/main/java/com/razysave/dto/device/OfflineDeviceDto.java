package com.razysave.dto.device;

import lombok.Data;

@Data
public class OfflineDeviceDto {
    private String id;
    private String name;
    private Integer buildingId;
    private Integer unitId;
    private String offlineTime;
    private String offlineSince;
}
