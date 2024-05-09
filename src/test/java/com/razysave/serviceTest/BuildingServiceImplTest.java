package com.razysave.serviceTest;

import com.razysave.dto.BuildingListDto;
import com.razysave.entity.property.Building;
import com.razysave.entity.property.Property;
import com.razysave.entity.property.Unit;
import com.razysave.exception.BuildingNotFoundException;
import com.razysave.service.property.BuildingService;
import com.razysave.service.property.PropertyService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class BuildingServiceImplTest {
    @Autowired
    private BuildingService buildingService;
    @Autowired
    private PropertyService propertyService;

    @Test
    public void getBuildingsSuccessTest() {
        Building building = new Building();
        building.setName("Test1");

        Building savedBuilding = buildingService.addBuilding(building);
        List<BuildingListDto> buildingList = buildingService.getBuildings(1);
        Assertions.assertTrue(buildingList.size() > 0);
    }

    @Test
    public void updateBuilding() {
        Building building = new Building();
        building.setName("test1");
        Building savedBuilding = buildingService.addBuilding(building);
        savedBuilding.setName("Test2");
        Building updateBuilding = buildingService.updateBuilding(savedBuilding.getId(), savedBuilding);
        Assertions.assertEquals("Test2", updateBuilding.getName());
        Assertions.assertNotNull(updateBuilding);
    }

    @Test
    public void getBuildingyByIdSuccessTest() {
        Building building = new Building();
        Building buildingSaved = buildingService.addBuilding(building);
        Building buildingGet = buildingService.getBuildingById(buildingSaved.getId());
        Assertions.assertEquals(buildingSaved.getId(), buildingGet.getId());
        Assertions.assertNotNull(buildingGet);
    }

    @Test
    public void getBuildingByIdFailerTest() {
        Building building = new Building();
        Assertions.assertThrows(BuildingNotFoundException.class, () -> buildingService.getBuildingById(200));
    }

    @Test
    public void deleteBuildingTest() {
        Property property = new Property();
        property.setId(20);
        property.setUnitCount(10);
        Building building = new Building();
        List<Building> buildings = new ArrayList<>();
        buildings.add(new Building());
        buildings.add(building);
        building.setName("test1");
        building.setPropertyId(20);
        property.setBuilding(buildings);
        List<Unit> units = new ArrayList<>();
        Unit unit = new Unit();
        unit.setId(1);
        units.add(unit);
        building.setUnits(units);
        propertyService.addProperty(property);
        Building buildingSaved = buildingService.addBuilding(building);
        buildingService.deleteBuildingById(buildingSaved.getId());
        Assertions.assertThrows(BuildingNotFoundException.class, () -> buildingService.getBuildingById(buildingSaved.getId()));
    }
}
