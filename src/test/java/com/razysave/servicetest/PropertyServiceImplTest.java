package com.razysave.servicetest;

import com.razysave.dto.property.PropertyDto;
import com.razysave.entity.property.Property;
import com.razysave.exception.PropertyNotFoundException;
import com.razysave.repository.property.PropertyRepository;
import com.razysave.service.serviceImpl.property.PropertyServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.Mockito.*;

@SpringBootTest
public class PropertyServiceImplTest {

    @Mock
    private PropertyRepository propertyRepository;

    @InjectMocks
    private PropertyServiceImpl propertyService;

    @Test
    public void getPropertiesSuccessTest() {
        Property property2 = new Property();
        property2.setId(300);
        when(propertyRepository.findAll()).thenReturn(Stream.of(property2)
                .collect(Collectors.toList()));
        List<PropertyDto> properties = propertyService.getProperties();
        Assertions.assertTrue(properties.size()>0);
    }

   @Test
   public void updatedProperty(){
       Property property = new Property();
       property.setName("test1");
       property.setId(200);
       when(propertyRepository.save(property)).thenReturn(property);
       Property property1 = propertyService.addProperty(property);
       property1.setName("Test2");
       when(propertyRepository.findById(property1.getId())).thenReturn(Optional.of(property1));
       when(propertyRepository.save(property)).thenReturn(property1);
       Property updateProperty = propertyService.updateProperty(property1.getId(),property1);
       Assertions.assertEquals("Test2",updateProperty.getName());
       Assertions.assertNotNull(updateProperty);
   }

    @Test
    public void getPropertyByIdSuccessTest() {
        Property property = new Property();
        property.setId(200);
        when(propertyRepository.findById(200)).thenReturn(Optional.of(property));
        PropertyDto propertyGet = propertyService.getPropertyById(property.getId());
        Assertions.assertEquals(property.getId(), propertyGet.getId());
        Assertions.assertNotNull(propertyGet);
    }
    @Test
    public void getPropertyByIdFailerTest() {
        when(propertyRepository.findById(2000)).thenReturn(Optional.empty());
        Assertions.assertThrows(PropertyNotFoundException.class, ()->propertyService.getPropertyById(2000));
    }

    @Test
    public void deletePropertyTest() {
        Property property = new Property();
        property.setId(200);
        when(propertyRepository.findById(200)).thenReturn(Optional.of(property));
        propertyService.deletePropertyById(property.getId());
        verify(propertyRepository, times(1)).deleteById(eq(property.getId()));
    }
}
