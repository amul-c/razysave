package com.razysave.dto.device;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ActiveDeviceDto {
    private String id;
    private String name;
    private Integer buildingId;
    private Integer unitId;
}
