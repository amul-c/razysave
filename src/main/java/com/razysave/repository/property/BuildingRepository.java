package com.razysave.repository.property;

import com.razysave.entity.property.Building;
import com.razysave.entity.property.Property;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface BuildingRepository extends MongoRepository<Building, Integer> {
    public List<Building>  findByPropertyId(Integer propertyId);

}
