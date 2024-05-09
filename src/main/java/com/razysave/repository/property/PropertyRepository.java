package com.razysave.repository.property;

import com.razysave.entity.property.Property;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PropertyRepository extends MongoRepository<Property, Integer> {
    public Property findByName(String name);
   // public void deleteById(Integer id);


}
