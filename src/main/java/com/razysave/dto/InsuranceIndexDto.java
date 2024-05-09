package com.razysave.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InsuranceIndexDto {
    private Integer id;
    private Integer propertyId;
    private String averageOccupancy;
    private String eviction;
    private Integer unregisteredVehicle;
    private Integer vacantAlert;
    private Integer curfewActivity;
}
