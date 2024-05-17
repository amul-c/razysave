package com.razysave.coontrollertest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.razysave.controller.BuildingController;
import com.razysave.dto.BuildingListDto;
import com.razysave.entity.property.Building;
import com.razysave.exception.BuildingNotFoundException;
import com.razysave.exception.PropertyNotFoundException;
import com.razysave.service.property.BuildingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Collections;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
        import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BuildingController.class)
@AutoConfigureMockMvc
public class BuildingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BuildingService buildingService;

    private Building building;

    @BeforeEach
    void setUp() {
        building = new Building();
        building.setId(1);
        building.setName("Building 1");
    }

    @Test
    void testGetBuildings_Success() throws Exception {
        when(buildingService.getBuildings(anyInt())).thenReturn(Collections.singletonList(new BuildingListDto()));

        mockMvc.perform(get("/building/property/{propertyId}/list", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void testGetBuildings_BuildingsNotFound() throws Exception {
        when(buildingService.getBuildings(anyInt())).thenThrow(BuildingNotFoundException.class);

        mockMvc.perform(get("/building/property/{propertyId}/list", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    void testGetBuildingById_Success() throws Exception {
        when(buildingService.getBuildingById(anyInt())).thenReturn(building);

        mockMvc.perform(get("/building/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Building 1"));
    }

    @Test
    void testGetBuildingById_BuildingNotFound() throws Exception {
        when(buildingService.getBuildingById(anyInt())).thenThrow(BuildingNotFoundException.class);

        mockMvc.perform(get("/building/{id}", 1))
                .andExpect(status().isNoContent());
    }

    @Test
    void testAddBuilding_Success() throws Exception {
        when(buildingService.addBuilding(any(Building.class))).thenReturn(building);

        mockMvc.perform(post("/building")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(building)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Building 1"));
    }

    @Test
    void testAddBuilding_PropertyNotFound() throws Exception {
        when(buildingService.addBuilding(any(Building.class))).thenThrow(PropertyNotFoundException.class);

        mockMvc.perform(post("/building")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(building)))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void testUpdateBuilding_Success() throws Exception {
        when(buildingService.updateBuilding(anyInt(), any(Building.class))).thenReturn(building);

        mockMvc.perform(put("/building/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(building)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Building 1"));
    }

    @Test
    void testUpdateBuilding_BuildingNotFound() throws Exception {
        when(buildingService.updateBuilding(anyInt(), any(Building.class))).thenThrow(BuildingNotFoundException.class);

        mockMvc.perform(put("/building/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(building)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteBuilding_Success() throws Exception {
        mockMvc.perform(delete("/building/{id}", 1))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeleteBuilding_BuildingNotFound() throws Exception {
        doThrow(BuildingNotFoundException.class).when(buildingService).deleteBuildingById(anyInt());
        mockMvc.perform(delete("/building/{id}", 1))
                .andExpect(status().isNotFound());
    }

    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
