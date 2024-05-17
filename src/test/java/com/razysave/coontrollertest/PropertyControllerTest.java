package com.razysave.coontrollertest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.razysave.controller.PropertyController;
import com.razysave.dto.property.PropertyDto;
import com.razysave.entity.property.Property;
import com.razysave.service.property.PropertyService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Collections;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PropertyController.class)
public class PropertyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PropertyService propertyService;

    @Test
    public void testGetProperties() throws Exception {
        when(propertyService.getProperties()).thenReturn(Collections.emptyList());
        mockMvc.perform(get("/property"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    public void testGetPropertyById() throws Exception {
        PropertyDto propertyDto = new PropertyDto();
        propertyDto.setId(1);
        when(propertyService.getPropertyById(1)).thenReturn(propertyDto);
        mockMvc.perform(get("/property/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    public void testAddProperty() throws Exception {
        Property property = new Property();
        property.setId(1);
        when(propertyService.addProperty(Mockito.any(Property.class))).thenReturn(property);
        mockMvc.perform(post("/property")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(property)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    public void testUpdateProperty() throws Exception {
        Property property = new Property();
        property.setId(1);
        when(propertyService.updateProperty(anyInt(), Mockito.any(Property.class))).thenReturn(property);
        mockMvc.perform(put("/property/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(property)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    public void testDeleteProperty() throws Exception {
        mockMvc.perform(delete("/property/1"))
                .andExpect(status().isNoContent());
    }
}
