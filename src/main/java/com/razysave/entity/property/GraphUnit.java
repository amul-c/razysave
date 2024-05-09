package com.razysave.entity.property;

import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class GraphUnit {
    @Id
    private Integer id;
    private Integer unitId;
    private Integer propertyId;
    private Integer[] estWeek;
    private Integer[] estMonth;
    private Integer[] estYear;
    private Integer[] curWeek;
    private Integer[] curMonth;
    private Integer[] curYear;
}
