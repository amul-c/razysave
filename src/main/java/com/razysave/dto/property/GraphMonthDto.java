package com.razysave.dto.property;

import lombok.Data;

@Data
public class GraphMonthDto {
    private Integer id;
    private Integer propertyId;
    private Integer unitId;
    private Integer[] month;
}
