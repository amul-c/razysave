package com.razysave.serviceTest;

import com.razysave.dto.unit.UnitInfoDto;
import com.razysave.dto.unit.UnitListDto;
import com.razysave.entity.property.Unit;
import com.razysave.exception.UnitNotFoundException;
import com.razysave.repository.property.BuildingRepository;
import com.razysave.repository.property.UnitRepository;
import com.razysave.service.serviceImpl.property.BuildingServiceImpl;
import com.razysave.service.serviceImpl.property.UnitServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UnitServiceImplTest {

    @Mock
    private UnitRepository unitRepository;
    @Mock
    private BuildingRepository buildingRepository;
   @InjectMocks
    private UnitServiceImpl unitService;
    @InjectMocks
    private BuildingServiceImpl buildingService;


    @Test
    public void getUnitsSuccessTest() {
        Unit unit = new Unit();
        unit.setName("Test1");
        unit.setId(200);
        unit.setBuildingId(200);
        when(unitRepository.findByBuildingId(unit.getBuildingId()))
                .thenReturn(Stream.of(unit).collect(Collectors.toList()));
        List<UnitListDto> unitListDtos = unitService.getUnits(unit.getBuildingId());
        Assertions.assertTrue(unitListDtos.size() > 0);
    }

    @Test
    public void updateUnit() {
        Unit unit = new Unit();
        unit.setId(200);
        unit.setName("test1");
        when(unitRepository.save(unit)).thenReturn(unit);
        Unit savedUnit = unitService.addUnit(unit);
        savedUnit.setName("Test2");
        when(unitRepository.findById(unit.getId())).thenReturn(Optional.of(unit));
        Unit updatedUnit = unitService.updateUnit(savedUnit.getId(), savedUnit);
        Assertions.assertEquals("Test2", updatedUnit.getName());
        Assertions.assertNotNull(updatedUnit);
    }

    @Test
    public void getUnitByIdSuccessTest() {
        Unit unit = new Unit();
        unit.setId(200);
        when(unitRepository.findById(unit.getId())).thenReturn(Optional.of(unit));
        UnitInfoDto unitGet = unitService.getUnitById(unit.getId());
        Assertions.assertEquals(unitGet.getId(), unitGet.getId());
        Assertions.assertNotNull(unitGet);
    }

    @Test
    public void getUnitByIdFailerTest() {
        when(unitRepository.findById(300)).thenReturn(Optional.empty());
        Assertions.assertThrows(UnitNotFoundException.class, () -> unitService.getUnitById(900));
    }

    @Test
    public void deleteUnitTest() {
        Unit unit = new Unit();
        unit.setName("testing");
        unit.setId(200);
        unit.setBuildingId(1);
        when(unitRepository.findById(unit.getId())).thenReturn(Optional.of(unit));
        unitService.deleteUnitById(unit.getId());
        verify(unitRepository, times(1)).deleteById(eq(unit.getId()));
    }
}
