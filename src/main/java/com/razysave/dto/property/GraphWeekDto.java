package com.razysave.dto.property;

import lombok.Data;

@Data
public class GraphWeekDto {
    private Integer id;
    private Integer propertyId;
    private Integer unitId;
    private Integer[] week;
}
