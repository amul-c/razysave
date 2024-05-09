package com.razysave.entity.property;

import com.razysave.entity.devices.Device;
import com.razysave.entity.tenant.Tenant;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

@Data
@NoArgsConstructor
@Document(collection = "units")
public class Unit {
    @Id
    private Integer id;
    private String name;
    private Integer buildingId;
    private List<Device> deviceList;
    private String installedDate;
    private Tenant tenant;
    private boolean occupied;
private Integer propertyId;
}