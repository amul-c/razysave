package com.razysave.entity.tenant;

import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@Document(collection = "tenants")
public class Tenant {
    @Id
    private Integer id;
    private Integer unitId;
    private String name;
    private String role;
    private String joined;
    private String phone;
    private String email;
private Integer propertyId;
}
