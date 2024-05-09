package com.razysave.serviceTest;

import com.razysave.dto.unit.UnitInfoDto;
import com.razysave.dto.unit.UnitListDto;
import com.razysave.entity.property.Building;
import com.razysave.entity.property.Unit;
import com.razysave.exception.UnitNotFoundException;
import com.razysave.repository.property.BuildingRepository;
import com.razysave.repository.property.UnitRepository;
import com.razysave.service.property.BuildingService;
import com.razysave.service.property.UnitService;
import com.razysave.service.serviceImpl.property.BuildingServiceImpl;
import com.razysave.service.serviceImpl.property.UnitServiceImpl;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class UnitServiceImplTest {

    @Autowired
    private UnitRepository unitRepository; // Assuming you have a UnitRepository
    @Autowired
    private BuildingRepository buildingRepository;
   @Autowired
    private UnitServiceImpl unitService;
    @Autowired
    private BuildingServiceImpl buildingService;


    @Test
    public void getUnitsSuccessTest() {
        Unit unit = new Unit();
        unit.setName("Test1");
        unit.setId(200);
        Building building = new Building();
        building.setId(200);
        unit.setBuildingId(building.getId());
        buildingRepository.save(building);
        Unit unitSaved = unitRepository.save(unit);
        List<UnitListDto> unitListDtos = unitService.getUnits(unit.getBuildingId());
        Assertions.assertTrue(unitListDtos.size() > 0);
    }

    @Test
    public void updateUnit() {
        Unit unit = new Unit();
        unit.setId(200);
        unit.setName("test1");
        Unit savedUnit = unitService.addUnit(unit);
        savedUnit.setName("Test2");
        Unit updatedUnit = unitService.updateUnit(savedUnit.getId(), savedUnit);
        Assertions.assertEquals("Test2", updatedUnit.getName());
        Assertions.assertNotNull(updatedUnit);
    }

    @Test
    public void getUnitByIdSuccessTest() {
        Unit unit = new Unit();
        unit.setId(200);
        Unit savedUnit = unitService.addUnit(unit);
        UnitInfoDto unitGet = unitService.getUnitById(savedUnit.getId());
        Assertions.assertEquals(savedUnit.getId(), unitGet.getId());
        Assertions.assertNotNull(unitGet);
    }

    @Test
    public void getUnitByIdFailerTest() {
        Unit unit = new Unit();
        unit.setId(300);
        Assertions.assertThrows(UnitNotFoundException.class, () -> unitService.getUnitById(900));
    }

    @Test
    public void deleteUnitTest() {
        Unit unit = new Unit();
        unit.setName("testing");
        unit.setId(200);
        unit.setBuildingId(1);
        Unit unitSaved = unitService.addUnit(unit);
        unitService.deleteUnitById(unitSaved.getId());
        Assertions.assertThrows(UnitNotFoundException.class, () -> unitService.getUnitById(unitSaved.getId()));
    }
}
