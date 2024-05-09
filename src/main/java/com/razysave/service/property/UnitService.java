package com.razysave.service.property;
import com.razysave.dto.unit.UnitInfoDto;
import com.razysave.dto.unit.UnitListDto;
import com.razysave.entity.property.Unit;
import java.util.List;

public interface UnitService {
    public List<UnitListDto> getUnits(Integer buildingId);
    public List<UnitListDto> getUnitsByProperty(Integer propertyID);
    public UnitInfoDto getUnitById(Integer id);
    public Unit addUnit(Unit unit);
    public Unit updateUnit(Integer id, Unit unit);
    public void deleteUnitById(Integer id);
}
