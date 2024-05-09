package com.razysave.service.property;

import com.razysave.dto.property.PropertyDto;
import com.razysave.entity.property.Property;
import java.util.List;

public interface PropertyService {
    public List<PropertyDto> getProperties();
    public PropertyDto getPropertyById(Integer id);
    public Property addProperty(Property property);

    public Property updateProperty(Integer id, Property property);

    public void deletePropertyById(Integer id);
}
