package com.razysave.repository.property;

import com.razysave.entity.property.GraphProperty;
import com.razysave.entity.property.GraphUnit;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GraphUnitRepository extends MongoRepository<GraphUnit, Integer> {
    public GraphUnit findByUnitId(Integer propertyId);

}