package com.razysave.entity.kpi;


import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashMap;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "kpi")
public class KPI {
    @Id
    private Integer id;
    private Integer propertyId;
    private HashMap<String, String> gallons;
    private HashMap<String, String> expenses;
    private HashMap<String, String> revenue;
    private HashMap<String, String> pAndL;
}
