package com.razysave.coontrollertest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.razysave.controller.KPIController;
import com.razysave.dto.kpi.KPIDto;
import com.razysave.entity.kpi.KPI;
import com.razysave.service.kpi.KPIService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Collections;
import java.util.List;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(KPIController.class)
class KPIControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private KPIService kpiService;

    @Test
    void testGetKPI() throws Exception {
        List<KPIDto> kpiList = Collections.singletonList(new KPIDto());
        when(kpiService.getKPI(anyInt())).thenReturn(kpiList);

        mockMvc.perform(get("/kpi/property/{propertyId}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].propertyId").value(kpiList.get(0).getPropertyId()));
    }

    @Test
    void testGetKPIById() throws Exception {
        KPIDto kpiDto = new KPIDto();
        when(kpiService.getKPIById(anyInt())).thenReturn(kpiDto);

        mockMvc.perform(get("/kpi/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(kpiDto.getId()));
    }

    @Test
    void testAddKPI() throws Exception {
        KPI kpi = new KPI();
        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(kpi);

        mockMvc.perform(post("/kpi")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdateKPI() throws Exception {
        KPI kpi = new KPI();
        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(kpi);

        mockMvc.perform(put("/kpi/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteKPI() throws Exception {
        mockMvc.perform(delete("/kpi/{id}", 1))
                .andExpect(status().isOk());
    }
}
