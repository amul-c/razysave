package com.razysave.serviceTest;

import com.razysave.dto.property.PropertyDto;
import com.razysave.entity.property.Building;
import com.razysave.entity.property.Property;
import com.razysave.exception.PropertyNotFoundException;
import com.razysave.service.property.PropertyService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.ArrayList;
import java.util.List;
@SpringBootTest
public class PropertyServiceImplTest {

    @Autowired
    private PropertyService propertyService;

    @Test
    public void getPropertiesSuccessTest() {
        Property property = new Property();
        property.setName("Test1");
        Property savedCustomer = propertyService.addProperty(property);
        List<PropertyDto> properties = propertyService.getProperties();
        Assertions.assertTrue(properties.size()>0);
    }

   @Test
   public void updatedProperty(){
       Property property = new Property();
       property.setName("test1");
       Property property1 = propertyService.addProperty(property);
       property1.setName("Test2");
       Property updateProperty = propertyService.updateProperty(property1.getId(),property1);
       Assertions.assertEquals("Test2",updateProperty.getName());
       Assertions.assertNotNull(updateProperty);
   }

    @Test
    public void getPropertyByIdSuccessTest() {
        Property property = new Property();
        Property propertySaved = propertyService.addProperty(property);
        PropertyDto propertyGet = propertyService.getPropertyById(propertySaved.getId());
        Assertions.assertEquals(propertySaved.getId(),propertyGet.getId());
        Assertions.assertNotNull(propertyGet);
    }
    @Test
    public void getPropertyByIdFailerTest() {
        Property property = new Property();
        Assertions.assertThrows(PropertyNotFoundException.class, ()->propertyService.getPropertyById(200));
    }

    @Test
    public void deletePropertyTest() {
        Property property = new Property();
        property.setName("test1");
        Property saveCustomer =propertyService.addProperty(property);
        List<Building> buildings = new ArrayList<>();
        buildings.add(new Building());
        buildings.add(new Building());
        saveCustomer.setBuilding(buildings);
        propertyService.deletePropertyById(property.getId());
        Assertions.assertThrows(PropertyNotFoundException.class, ()->propertyService.getPropertyById(saveCustomer.getId())) ;
    }
}
