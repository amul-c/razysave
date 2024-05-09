package com.razysave.repository.tenant;

import com.razysave.entity.tenant.Tenant;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TenantRepository extends MongoRepository<Tenant, Integer> {
    public Tenant findByName(String name);

    public Tenant deleteByName(String name);

    public List<Tenant> findByUnitId(Integer UnitId);
    public List<Tenant> findByPropertyId(Integer UnitId);
}
