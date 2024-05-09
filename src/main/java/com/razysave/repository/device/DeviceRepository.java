package com.razysave.repository.device;

import com.razysave.entity.devices.Device;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeviceRepository extends MongoRepository<Device, Integer> {
public List<Device> findByNameAndPropertyId(String name,Integer propertyId);
    public List<Device> findByStatusAndPropertyId(String status,Integer propertyId);
    public List<Device> findByPropertyIdAndConnection(Integer propertyId,String connection);
    public List<Device> findByPropertyId(Integer propertyId);

}
