package com.razysave.repository.property;

import com.razysave.entity.property.GraphProperty;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GraphPropertyRepository extends MongoRepository<GraphProperty, Integer> {
    public GraphProperty findByPropertyId(Integer propertyId);

}
