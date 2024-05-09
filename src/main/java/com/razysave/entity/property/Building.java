package com.razysave.entity.property;

import com.razysave.entity.devices.Device;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@Document(collection = "buildings")
public class Building {
    @Id
    private Integer id;
    private String name;
    private List<Unit> units;
    private List<Device> devices;
    private Integer propertyId;

    public Integer getUnitCount() {
        if(units!=null)
        return units.size();
        return 0;
    }

    public Integer getDeviceCount() {
        if (devices != null)
        return devices.size();
        return 0;
    }

}
