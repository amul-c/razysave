package com.razysave.dto.property;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class PropertyDto {
    private Integer id;
    private String name;
    private Integer buildingCount;
    private Integer unitCount;
    private String size;
    private String occupancy;
    private String Owner;
    private String phone;
    private String email;
    private Integer tenantCount;
}
