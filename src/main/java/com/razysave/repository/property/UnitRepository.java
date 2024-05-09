package com.razysave.repository.property;

import com.razysave.entity.property.Building;
import com.razysave.entity.property.Unit;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UnitRepository extends MongoRepository<Unit, Integer> {
  public List<Unit> findByBuildingId(Integer buildingId);
  public List<Unit> findByPropertyId(Integer propertyId);

}
