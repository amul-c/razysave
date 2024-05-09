package com.razysave.repository.insurance;

import com.razysave.entity.insurance.InsuranceIndex;
import com.razysave.entity.property.Unit;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InsuranceIndexRepository extends MongoRepository<InsuranceIndex, Integer> {
    public List<InsuranceIndex> findByPropertyId(Integer propertyId);

}
