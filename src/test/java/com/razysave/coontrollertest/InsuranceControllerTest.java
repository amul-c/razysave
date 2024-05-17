package com.razysave.coontrollertest;

import com.razysave.controller.InsuranceController;
import com.razysave.dto.InsuranceIndexDto;
import com.razysave.entity.insurance.InsuranceIndex;
import com.razysave.exception.InsuranceIndexNotFoundException;
import com.razysave.service.insurance.InsuranceIndexService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Collections;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(InsuranceController.class)
class InsuranceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InsuranceIndexService insuranceIndexService;

    @Autowired
    private InsuranceController insuranceController;


    @Test
    void testGetInsuranceIndex_Success() throws Exception {
        when(insuranceIndexService.getInsuranceIndex(anyInt())).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/insurance/property/{propertyId}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testGetInsuranceIndexById_Success() throws Exception {
        InsuranceIndexDto mockDto = new InsuranceIndexDto();
        when(insuranceIndexService.getInsuranceIndexById(anyInt())).thenReturn(mockDto);

        mockMvc.perform(get("/insurance/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testAddInsuranceIndex_Success() throws Exception {
        InsuranceIndex mockEntity = new InsuranceIndex();
        mockMvc.perform(post("/insurance")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testUpdateInsuranceIndex_Success() throws Exception {
        InsuranceIndex mockEntity = new InsuranceIndex();
        when(insuranceIndexService.updateInsuranceIndexById(anyInt(), any())).thenReturn(mockEntity);

        mockMvc.perform(put("/insurance/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testDeleteInsuranceIndex_Success() throws Exception {
        mockMvc.perform(delete("/insurance/{id}", 1))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteInsuranceIndex_InsuranceIndexNotFound() throws Exception {
        doThrow(InsuranceIndexNotFoundException.class).when(insuranceIndexService).deleteInsuranceIndexById(anyInt());

        mockMvc.perform(delete("/insurance/{id}", 1))
                .andExpect(status().isNoContent());
    }
}
