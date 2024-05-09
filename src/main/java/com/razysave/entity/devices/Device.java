package com.razysave.entity.devices;

import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Map;

@Data
@NoArgsConstructor
@Document(collection = "devices")
public class Device {
    @Id
    private Integer id;
    private Integer unitId;
    private Integer propertyId;
    private String name;
    private Integer battery;
    private String connection;
    private String installedDate;
    private String status;
    private String offlineTime;
    private String offlineSince;
    private Map<String, String> reading;
}
