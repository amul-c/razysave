package com.razysave.dto.tenant;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TenantDto {
    private Integer id;
    private Integer unitId;
    private String name;
    private String role;
    private String joined;
    private String phone;
    private String email;
    private Property property;
}
