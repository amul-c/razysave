package com.razysave.serviceTest;

import com.razysave.dto.property.GraphMonthDto;
import com.razysave.dto.property.GraphWeekDto;
import com.razysave.dto.property.GraphYearDto;
import com.razysave.entity.property.GraphProperty;
import com.razysave.entity.property.GraphUnit;
import com.razysave.exception.GraphNotFoundException;
import com.razysave.service.property.GraphService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;

@SpringBootTest
public class GraphServiceImplTest {
    @Autowired
    GraphService graphService;

    @Test
    public void getGraphPropertyByWeekSuccessTest() {
        GraphProperty graphProperty = new GraphProperty();
        graphProperty.setPropertyId(200);
        graphProperty.setId(200);
        GraphProperty graphPropertySaved = graphService.addGraphProperty(graphProperty);
        List<GraphWeekDto> graphWeekDtos = graphService.getGraphPropertyByWeek(graphPropertySaved.getPropertyId());
        Assertions.assertTrue(graphWeekDtos.size() > 0);
    }

    @Test
    public void getGraphUnitByWeekSuccessTest() {
        GraphUnit graphUnit = new GraphUnit();
        graphUnit.setUnitId(200);
        graphUnit.setId(200);
        GraphUnit graphUnitSaved = graphService.addGraphUnit(graphUnit);
        List<GraphWeekDto> graphWeekDtos = graphService.getGraphUnitByWeek(graphUnitSaved.getUnitId());
        Assertions.assertTrue(graphWeekDtos.size() > 0);
    }

    @Test
    public void getGraphPropertyByMonthSuccessTest() {
        GraphProperty graphProperty = new GraphProperty();
        graphProperty.setPropertyId(1);
        graphProperty.setId(200);
        GraphProperty graphPropertySaved = graphService.addGraphProperty(graphProperty);
        GraphProperty graphPropertyGet = graphService.getGraphPropertytById(graphPropertySaved.getId());
        Assertions.assertNotNull(graphPropertyGet);
    }

    @Test
    public void getGraphUnitByMonthSuccessTest() {
        GraphUnit graphUnit = new GraphUnit();
        graphUnit.setUnitId(200);
        graphUnit.setId(200);
        GraphUnit graphSaved = graphService.addGraphUnit(graphUnit);
        List<GraphMonthDto> graphMonthDtos= graphService.getGraphUnitByMonth(graphSaved.getUnitId());
        Assertions.assertNotNull(graphMonthDtos);
    }

    @Test
    public void getGraphPropertyByYearSuccessTest() {
        GraphProperty graphProperty = new GraphProperty();
        graphProperty.setPropertyId(200);
        graphProperty.setId(200);
        GraphProperty graphPropertySaved = graphService.addGraphProperty(graphProperty);
        List<GraphYearDto> graphYearDtos = graphService.getGraphByPropertyYear(graphPropertySaved.getId());
        Assertions.assertNotNull(graphYearDtos);
    }

    @Test
    public void getGraphUnitByYearSuccessTest() {
        GraphUnit graphUnit = new GraphUnit();
        graphUnit.setUnitId(200);
        graphUnit.setId(200);
        GraphUnit graphUnitPropertySaved = graphService.addGraphUnit(graphUnit);
        List<GraphYearDto> graphYearDtos = graphService.getGraphByUnitYear(graphUnitPropertySaved.getId());
        Assertions.assertNotNull(graphYearDtos);
    }

    @Test
    public void getGraphPropertyByIdSuccessTest() {
        GraphProperty graphProperty = new GraphProperty();
        graphProperty.setPropertyId(1);
        graphProperty.setId(200);
        GraphProperty graphPropertySaved = graphService.addGraphProperty(graphProperty);
        GraphProperty graphPropertyGet = graphService.getGraphPropertytById(graphPropertySaved.getId());
        Assertions.assertNotNull(graphPropertyGet);
    }

    @Test
    public void getGraphPropertyByIdFailerTest() {
        Assertions.assertThrows(GraphNotFoundException.class, () -> graphService.getGraphPropertytById(2000));
    }

    @Test
    public void getGraphUnitByIdSuccessTest() {
        GraphUnit graphUnit = new GraphUnit();
        graphUnit.setPropertyId(200);
        graphUnit.setId(200);
        GraphUnit graphUnitSaved = graphService.addGraphUnit(graphUnit);
        GraphUnit graphUnitGet = graphService.getGraphUnitById(graphUnitSaved.getId());
        Assertions.assertNotNull(graphUnitGet);
    }

    @Test
    public void getGraphUnitByIdFailerTest() {
        Assertions.assertThrows(GraphNotFoundException.class, () -> graphService.getGraphUnitById(2000));
    }

    @Test
    public void updateGraphPropertyTest() {
        GraphProperty graphProperty = new GraphProperty();
        graphProperty.setPropertyId(1);
        graphProperty.setId(200);
        GraphProperty graphPropertySaved = graphService.addGraphProperty(graphProperty);
        graphPropertySaved.setPropertyId(2);
        GraphProperty graphPropertyUpdated = graphService.updateGraphProperty(graphPropertySaved, graphPropertySaved.getId());
        Assertions.assertEquals(2, graphPropertyUpdated.getPropertyId());
        Assertions.assertNotNull(graphPropertyUpdated);
    }

    @Test
    public void updateGraphUnitTest() {
        GraphUnit graphUnit = new GraphUnit();
        graphUnit.setPropertyId(1);
        graphUnit.setId(200);
        GraphUnit graphUnitSaved = graphService.addGraphUnit(graphUnit);
        graphUnitSaved.setPropertyId(2);
        GraphUnit graphUnitUpdated = graphService.updateGraphUnit(graphUnitSaved, graphUnitSaved.getId());
        Assertions.assertEquals(2, graphUnitUpdated.getPropertyId());
        Assertions.assertNotNull(graphUnitUpdated);
    }

    @Test
    public void deleteGraphPropertyTest() {
        GraphProperty graphProperty = new GraphProperty();
        graphProperty.setPropertyId(1);
        graphProperty.setId(200);
        GraphProperty graphPropertySaved = graphService.addGraphProperty(graphProperty);
        graphService.deleteGraphProperty(graphPropertySaved.getId());
        Assertions.assertThrows(GraphNotFoundException.class, () -> graphService.getGraphPropertytById(graphPropertySaved.getId()));
    }

    @Test
    public void deleteGraphUnitTest() {
        GraphUnit graphUnit = new GraphUnit();
        graphUnit.setPropertyId(1);
        graphUnit.setId(200);
        GraphUnit graphUnitSaved = graphService.addGraphUnit(graphUnit);
        graphService.deleteGraphProperty(graphUnitSaved.getId());
        Assertions.assertThrows(GraphNotFoundException.class, () -> graphService.getGraphPropertytById(graphUnitSaved.getId()));
    }
}
