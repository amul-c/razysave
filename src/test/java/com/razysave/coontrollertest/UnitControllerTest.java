package com.razysave.coontrollertest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.razysave.controller.UnitController;
import com.razysave.dto.unit.UnitInfoDto;
import com.razysave.entity.property.Unit;
import com.razysave.exception.BuildingNotFoundException;
import com.razysave.exception.UnitNotFoundException;
import com.razysave.service.property.UnitService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Collections;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
        import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UnitController.class)
class UnitControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UnitService unitService;

    @Test
    void testGetUnits() throws Exception {
        when(unitService.getUnits(anyInt())).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/unit/building/{buildingId}/list", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void testGetUnitByPropertyId() throws Exception {
        when(unitService.getUnitsByProperty(anyInt())).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/unit/property/{propertyId}/list", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void testGetUnitById_UnitFound() throws Exception {
        UnitInfoDto unitInfoDto = new UnitInfoDto();
        when(unitService.getUnitById(anyInt())).thenReturn(unitInfoDto);

        mockMvc.perform(get("/unit/info/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isNotEmpty());
    }

    @Test
    void testGetUnitById_UnitNotFound() throws Exception {
        when(unitService.getUnitById(anyInt())).thenThrow(UnitNotFoundException.class);

        mockMvc.perform(get("/unit/info/{id}", 1))
                .andExpect(status().isNoContent());
    }

    @Test
    void testAddUnits_BuildingFound() throws Exception {
        Unit unit = new Unit();
        ObjectMapper objectMapper = new ObjectMapper();
        String unitJson = objectMapper.writeValueAsString(unit);

        when(unitService.addUnit(any(Unit.class))).thenReturn(unit);

        mockMvc.perform(post("/unit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(unitJson))
                .andExpect(status().isOk());
    }

    @Test
    void testAddUnits_BuildingNotFound() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String unitJson = objectMapper.writeValueAsString(new Unit());

        when(unitService.addUnit(any(Unit.class))).thenThrow(BuildingNotFoundException.class);

        mockMvc.perform(post("/unit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(unitJson))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void testUpdateUnit_UnitFound() throws Exception {
        Unit unit = new Unit();
        ObjectMapper objectMapper = new ObjectMapper();
        String unitJson = objectMapper.writeValueAsString(unit);

        mockMvc.perform(put("/unit/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(unitJson))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdateUnit_UnitNotFound() throws Exception {
        Unit unit = new Unit();
        ObjectMapper objectMapper = new ObjectMapper();
        String unitJson = objectMapper.writeValueAsString(unit);

        when(unitService.updateUnit(anyInt(), any(Unit.class))).thenThrow(UnitNotFoundException.class);

        mockMvc.perform(put("/unit/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(unitJson))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteUnit_UnitFound() throws Exception {
        doNothing().when(unitService).deleteUnitById(anyInt());

        mockMvc.perform(delete("/unit/{id}", 1))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeleteUnit_UnitNotFound() throws Exception {
        doThrow(UnitNotFoundException.class).when(unitService).deleteUnitById(anyInt());

        mockMvc.perform(delete("/unit/{id}", 1))
                .andExpect(status().isNotFound());
    }
}
