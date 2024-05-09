package com.razysave.service.property;

import com.razysave.dto.BuildingListDto;
import com.razysave.entity.property.Building;

import java.util.List;

public interface BuildingService {
    public List<BuildingListDto> getBuildings(Integer propertyId);

    public Building getBuildingById(Integer id);

    public Building addBuilding(Building building);

    public Building updateBuilding(Integer id, Building building);

    public void deleteBuildingById(Integer id);
}
