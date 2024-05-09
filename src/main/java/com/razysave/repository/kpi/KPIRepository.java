package com.razysave.repository.kpi;

import com.razysave.entity.kpi.KPI;
import com.razysave.entity.property.Unit;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KPIRepository extends MongoRepository<KPI, Integer> {
    public List<KPI> findByPropertyId(Integer propertyId);

}
