package com.razysave.coontrollertest;

import com.razysave.controller.GraphController;
import com.razysave.entity.property.GraphProperty;
import com.razysave.entity.property.GraphUnit;
import com.razysave.exception.GraphNotFoundException;
import com.razysave.service.property.GraphService;
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

@WebMvcTest(GraphController.class)
class GraphControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GraphService graphService;

    @Autowired
    private GraphController graphController;


    @Test
    void testGetGraphPropertyByWeek_Success() throws Exception {
        when(graphService.getGraphPropertyByWeek(anyInt())).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/graph/week/property/{propertyId}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testGetGraphPropertyByMonth_Success() throws Exception {
        when(graphService.getGraphPropertyByMonth(anyInt())).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/graph/month/property/{propertyId}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testGetGraphUnitByYear_Success() throws Exception {
        when(graphService.getGraphByPropertyYear(anyInt())).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/graph/year/property/{propertyId}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testGetGraphUnitByWeek_Success() throws Exception {
        when(graphService.getGraphUnitByWeek(anyInt())).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/graph/week/unit/{unitId}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testGetGraphUnitByMonth_Success() throws Exception {
        when(graphService.getGraphPropertyByMonth(anyInt())).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/graph/month/unit/{unitId}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testGetGraphPropertyByYear_Success() throws Exception {
        when(graphService.getGraphByPropertyYear(anyInt())).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/graph/year/unit/{unitId}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testAddGraphProperty_Success() throws Exception {
        GraphProperty mockGraphProperty = new GraphProperty();
        when(graphService.addGraphProperty(any())).thenReturn(mockGraphProperty);

        mockMvc.perform(post("/graph/property")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testAddGraphUnit_Success() throws Exception {
        GraphUnit mockGraphUnit = new GraphUnit();
        when(graphService.addGraphUnit(any())).thenReturn(mockGraphUnit);

        mockMvc.perform(post("/graph/unit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testUpdateGraphUnit_Success() throws Exception {
        GraphUnit mockGraphUnit = new GraphUnit();
        when(graphService.updateGraphUnit(any(), anyInt())).thenReturn(mockGraphUnit);

        mockMvc.perform(put("/graph/{graphId}/unit", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isAccepted())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testUpdateGraphProperty_Success() throws Exception {
        GraphProperty mockGraphProperty = new GraphProperty();
        when(graphService.updateGraphProperty(any(), anyInt())).thenReturn(mockGraphProperty);

        mockMvc.perform(put("/graph/{graphId}/property", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isAccepted())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testDeleteGraphProperty_Success() throws Exception {
        mockMvc.perform(delete("/graph/{graphId}/property", 1))
                .andExpect(status().isAccepted());
    }

    @Test
    void testDeleteGraphUnit_Success() throws Exception {
        mockMvc.perform(delete("/graph/{graphId}/unit", 1))
                .andExpect(status().isAccepted());
    }

    @Test
    void testDeleteGraphProperty_GraphNotFound() throws Exception {
        doThrow(GraphNotFoundException.class).when(graphService).deleteGraphProperty(anyInt());

        mockMvc.perform(delete("/graph/{graphId}/property", 1))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeleteGraphUnit_GraphNotFound() throws Exception {
        doThrow(GraphNotFoundException.class).when(graphService).deleteGraphUnit(anyInt());

        mockMvc.perform(delete("/graph/{graphId}/unit", 1))
                .andExpect(status().isNoContent());
    }
}
