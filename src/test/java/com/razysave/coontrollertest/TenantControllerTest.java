package com.razysave.coontrollertest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.razysave.controller.TenantController;
import com.razysave.dto.tenant.TenantDto;
import com.razysave.entity.tenant.Tenant;
import com.razysave.service.property.TenantService;
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

@WebMvcTest(TenantController.class)
class TenantControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TenantService tenantService;

    @Test
    void testGetTenants() throws Exception {
        List<TenantDto> tenants = Collections.singletonList(new TenantDto());
        when(tenantService.getTenants(anyInt())).thenReturn(tenants);

        mockMvc.perform(get("/tenant/unit/{unitId}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(tenants.get(0).getId()));
    }

    @Test
    void testGetTenantsByProperty() throws Exception {
        List<TenantDto> tenants = Collections.singletonList(new TenantDto());
        when(tenantService.getTenantsByPropertyId(anyInt())).thenReturn(tenants);

        mockMvc.perform(get("/tenant/property/{propertyId}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(tenants.get(0).getId()));
    }

    @Test
    void testGetTenantByName() throws Exception {
        Tenant tenant = new Tenant();
        when(tenantService.getTenantById(anyInt())).thenReturn(tenant);

        mockMvc.perform(get("/tenant/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(tenant.getId()));
    }

    @Test
    void testAddTenant() throws Exception {
        Tenant tenant = new Tenant();
        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(tenant);

        mockMvc.perform(post("/tenant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdateTenant() throws Exception {
        Tenant tenant = new Tenant();
        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(tenant);

        mockMvc.perform(put("/tenant/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteTenant() throws Exception {
        mockMvc.perform(delete("/tenant/{id}", 1))
                .andExpect(status().isNoContent());
    }
}
