package com.razysave.serviceTest;

import com.razysave.dto.property.GraphMonthDto;
import com.razysave.dto.property.GraphWeekDto;
import com.razysave.dto.property.GraphYearDto;
import com.razysave.entity.property.GraphProperty;
import com.razysave.entity.property.GraphUnit;
import com.razysave.exception.GraphNotFoundException;
import com.razysave.repository.property.GraphPropertyRepository;
import com.razysave.repository.property.GraphUnitRepository;
import com.razysave.service.serviceImpl.property.GraphServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;
import java.util.Optional;
import static org.mockito.Mockito.*;

@SpringBootTest
public class GraphServiceImplTest {
    @InjectMocks
    GraphServiceImpl graphService;
    @Mock
    GraphUnitRepository graphUnitRepository;
    @Mock
    GraphPropertyRepository graphPropertyRepository;
    @Test
    public void getGraphPropertyByWeekSuccessTest() {
        GraphProperty graphProperty = new GraphProperty();
        graphProperty.setPropertyId(200);
        graphProperty.setId(200);
        when(graphPropertyRepository.findByPropertyId(graphProperty.getPropertyId())).thenReturn(graphProperty);
        List<GraphWeekDto> graphWeekDtos = graphService.getGraphPropertyByWeek(graphProperty.getPropertyId());
        Assertions.assertTrue(graphWeekDtos.size() > 0);
    }

    @Test
    public void getGraphUnitByWeekSuccessTest() {
        GraphUnit graphUnit = new GraphUnit();
        graphUnit.setUnitId(200);
        graphUnit.setId(200);
        when(graphUnitRepository.findByUnitId(graphUnit.getUnitId())).thenReturn(graphUnit);
        List<GraphWeekDto> graphWeekDtos = graphService.getGraphUnitByWeek(graphUnit.getUnitId());
        Assertions.assertTrue(graphWeekDtos.size() > 0);
    }

    @Test
    public void getGraphPropertyByMonthSuccessTest() {
        GraphProperty graphProperty = new GraphProperty();
        graphProperty.setPropertyId(200);
        graphProperty.setId(200);
        when(graphPropertyRepository.findByPropertyId(graphProperty.getPropertyId())).thenReturn(graphProperty);
        List<GraphMonthDto> graphMonthDtos = graphService.getGraphPropertyhByMonth(graphProperty.getId());
        Assertions.assertTrue(graphMonthDtos.size() > 0);
    }

    @Test
    public void getGraphUnitByMonthSuccessTest() {
        GraphUnit graphUnit = new GraphUnit();
        graphUnit.setUnitId(200);
        graphUnit.setId(200);
        when(graphUnitRepository.findByUnitId(graphUnit.getUnitId())).thenReturn(graphUnit);
        List<GraphMonthDto> graphMonthDtos= graphService.getGraphUnitByMonth(graphUnit.getUnitId());
        Assertions.assertNotNull(graphMonthDtos);
    }

    @Test
    public void getGraphPropertyByYearSuccessTest() {
        GraphProperty graphProperty = new GraphProperty();
        graphProperty.setPropertyId(200);
        graphProperty.setId(200);
        when(graphPropertyRepository.findByPropertyId(graphProperty.getPropertyId())).thenReturn(graphProperty);
        List<GraphYearDto> graphYearDtos = graphService.getGraphByPropertyYear(graphProperty.getId());
        Assertions.assertNotNull(graphYearDtos);
    }

    @Test
    public void getGraphUnitByYearSuccessTest() {
        GraphUnit graphUnit = new GraphUnit();
        graphUnit.setUnitId(200);
        graphUnit.setId(200);
        when(graphUnitRepository.findByUnitId(graphUnit.getUnitId())).thenReturn(graphUnit);
        List<GraphYearDto> graphYearDtos = graphService.getGraphByUnitYear(graphUnit.getId());
        Assertions.assertNotNull(graphYearDtos);
    }

    @Test
    public void getGraphPropertyByIdSuccessTest() {
        GraphProperty graphProperty = new GraphProperty();
        graphProperty.setPropertyId(200);
        graphProperty.setId(200);
        when(graphPropertyRepository.findById(graphProperty.getPropertyId())).thenReturn(Optional.of(graphProperty));
        GraphProperty graphPropertyGet = graphService.getGraphPropertytById(graphProperty.getId());
        Assertions.assertNotNull(graphPropertyGet);
    }

    @Test
    public void getGraphPropertyByIdFailerTest() {
        when(graphPropertyRepository.findById(200)).thenReturn(Optional.empty());
        Assertions.assertThrows(GraphNotFoundException.class, () -> graphService.getGraphPropertytById(2000));
    }

    @Test
    public void getGraphUnitByIdSuccessTest() {
        GraphUnit graphUnit = new GraphUnit();
        graphUnit.setPropertyId(200);
        graphUnit.setId(200);
        when(graphUnitRepository.findById(graphUnit.getId())).thenReturn(Optional.of(graphUnit));
        GraphUnit graphUnitGet = graphService.getGraphUnitById(graphUnit.getId());
        Assertions.assertNotNull(graphUnitGet);
    }

    @Test
    public void getGraphUnitByIdFailerTest() {
        when(graphUnitRepository.findById(200)).thenReturn(Optional.empty());
        Assertions.assertThrows(GraphNotFoundException.class, () -> graphService.getGraphUnitById(2000));
    }

    @Test
    public void updateGraphPropertyTest() {
        GraphProperty graphProperty = new GraphProperty();
        graphProperty.setPropertyId(1);
        graphProperty.setId(200);
        when(graphPropertyRepository.save(graphProperty)).thenReturn(graphProperty);
        GraphProperty graphPropertySaved = graphService.addGraphProperty(graphProperty);
        graphPropertySaved.setPropertyId(2);
        when(graphPropertyRepository.findById(graphPropertySaved.getId())).thenReturn(Optional.of(graphPropertySaved));
        when(graphPropertyRepository.save(graphPropertySaved)).thenReturn(graphPropertySaved);
        GraphProperty graphPropertyUpdated = graphService.updateGraphProperty(graphPropertySaved, graphPropertySaved.getId());
        Assertions.assertEquals(2, graphPropertyUpdated.getPropertyId());
        Assertions.assertNotNull(graphPropertyUpdated);
    }

    @Test
    public void updateGraphUnitTest() {
        GraphUnit graphUnit = new GraphUnit();
        graphUnit.setPropertyId(1);
        graphUnit.setId(200);
        when(graphUnitRepository.save(graphUnit)).thenReturn(graphUnit);
        GraphUnit graphUnitSaved = graphService.addGraphUnit(graphUnit);
        graphUnitSaved.setPropertyId(2);
        when(graphUnitRepository.findById(graphUnitSaved.getId())).thenReturn(Optional.of(graphUnitSaved));
        when(graphUnitRepository.save(graphUnitSaved)).thenReturn(graphUnitSaved);
        GraphUnit graphUnitUpdated = graphService.updateGraphUnit(graphUnitSaved, graphUnitSaved.getId());
        Assertions.assertEquals(2, graphUnitUpdated.getPropertyId());
        Assertions.assertNotNull(graphUnitUpdated);
    }

    @Test
    public void deleteGraphPropertyTest() {
        GraphProperty graphProperty = new GraphProperty();
        graphProperty.setPropertyId(1);
        graphProperty.setId(200);
        when(graphPropertyRepository.findById(graphProperty.getId())).thenReturn(Optional.of(graphProperty));
        graphService.deleteGraphProperty(graphProperty.getId());
        verify(graphPropertyRepository, times(1)).delete(graphProperty);
    }

    @Test
    public void deleteGraphUnitTest() {
        GraphUnit graphUnit = new GraphUnit();
        graphUnit.setPropertyId(1);
        graphUnit.setId(200);
        when(graphUnitRepository.findById(graphUnit.getId())).thenReturn(Optional.of(graphUnit));
        graphService.deleteGraphUnit(graphUnit.getId());
        verify(graphUnitRepository, times(1)).delete(graphUnit);
    }
}
