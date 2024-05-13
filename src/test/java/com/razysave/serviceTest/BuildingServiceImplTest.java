package com.razysave.serviceTest;

import com.razysave.dto.BuildingListDto;
import com.razysave.entity.property.Building;
import com.razysave.entity.property.Property;
import com.razysave.entity.property.Unit;
import com.razysave.exception.BuildingNotFoundException;
import com.razysave.repository.property.BuildingRepository;
import com.razysave.repository.property.PropertyRepository;
import com.razysave.repository.property.UnitRepository;
import com.razysave.service.property.UnitService;
import com.razysave.service.serviceImpl.property.BuildingServiceImpl;
import com.razysave.service.serviceImpl.property.PropertyServiceImpl;
import com.razysave.service.serviceImpl.property.UnitServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@SpringBootTest
public class BuildingServiceImplTest {
    @Mock
    private BuildingRepository buildingRepository;
    @Mock
    private PropertyRepository propertyRepository;
    @Mock
    private UnitRepository unitRepository;
    @InjectMocks
    private BuildingServiceImpl buildingService;

    @Test
    public void getBuildingsSuccessTest() {
        Building building = new Building();
        building.setPropertyId(200);
        when(buildingRepository.findByPropertyId(building.getPropertyId()))
                .thenReturn(Stream.of(building).collect(Collectors.toList()));
        List<BuildingListDto> buildingList = buildingService.getBuildings(building.getPropertyId());
        Assertions.assertTrue(buildingList.size() > 0);
    }

    @Test
    public void updateBuilding() {
        Building building = new Building();
        building.setName("test1");
        building.setId(200);
        when(buildingRepository.save(building)).thenReturn(building);
        Building savedBuilding = buildingService.addBuilding(building);
        savedBuilding.setName("Test2");
        when(buildingRepository.findById(building.getId())).thenReturn(Optional.of(building));
        when(buildingRepository.save(building)).thenReturn(savedBuilding);
        Building updateBuilding = buildingService.updateBuilding(savedBuilding.getId(), savedBuilding);
        Assertions.assertEquals("Test2", updateBuilding.getName());
        Assertions.assertNotNull(updateBuilding);
    }

    @Test
    public void getBuildingyByIdSuccessTest() {
        Building building = new Building();
        building.setId(200);
        when(buildingRepository.findById(building.getId())).thenReturn(Optional.of(building));
        Building buildingGet = buildingService.getBuildingById(building.getId());
        Assertions.assertEquals(building.getId(), buildingGet.getId());
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
        property.setId(200);
        property.setUnitCount(10);
        Building building = new Building();
        building.setId(200);
        List<Building> buildings = new ArrayList<>();
        building.setName("test1");
        building.setPropertyId(200);
        buildings.add(building);
        property.setBuilding(buildings);
        List<Unit> units = new ArrayList<>();
        Unit unit = new Unit();
        unit.setId(1);
        units.add(unit);
        building.setUnits(units);
        UnitService unitService = Mockito.mock(UnitService.class);
        BuildingServiceImpl buildingService = new BuildingServiceImpl(buildingRepository, propertyRepository, unitService,unitRepository);
        when(propertyRepository.findById(building.getPropertyId())).thenReturn(Optional.of(property));
        when(buildingRepository.findById(building.getId())).thenReturn(Optional.of(building));
        verify(unitRepository, times(0)).deleteById(eq(unit.getId()));
        buildingService.deleteBuildingById(building.getId());
        verify(buildingRepository, times(1)).deleteById(eq(building.getId()));
    }
}
