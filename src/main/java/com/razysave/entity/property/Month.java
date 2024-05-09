package com.razysave.entity.property;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class Month {
    private Integer week1;
    private Integer week2;
    private Integer week3;
    private Integer week4;
}
