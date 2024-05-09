package com.razysave.serviceTest;

import com.razysave.dto.tenant.TenantDto;
import com.razysave.entity.property.Building;
import com.razysave.entity.property.Property;
import com.razysave.entity.property.Unit;
import com.razysave.entity.tenant.Tenant;
import com.razysave.exception.TenantNotFoundException;
import com.razysave.service.property.BuildingService;
import com.razysave.service.property.PropertyService;
import com.razysave.service.property.TenantService;
import com.razysave.service.property.UnitService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class TenantServceImplTest {
    @Autowired
    private TenantService tenantService;
@Autowired
private UnitService unitService;
@Autowired
    BuildingService buildingService;
@Autowired
    PropertyService propertyService;
    @Test
    public void getTenantSuccessTest() {
        Tenant tenant = new Tenant();
        tenant.setName("Test1");
        tenant.setId(200);
        tenant.setUnitId(19);
        Tenant tenantSaved = tenantService.addTenant(tenant);
        List<TenantDto> tenantDtoList = tenantService.getTenants(tenant.getUnitId());
        Assertions.assertTrue(tenantDtoList.size() > 0);
    }
    @Test
    public void getTenantByPropertyIdSuccessTest() {
        Tenant tenant = new Tenant();
        tenant.setName("Test1");
        tenant.setId(200);
        tenant.setUnitId(19);
        Property property = new Property();
        property.setId(200);
        tenant.setPropertyId(property.getId());
        Tenant tenantSaved = tenantService.addTenant(tenant);
        List<TenantDto> tenantDtoList = tenantService.getTenantsByPropertyId(tenant.getPropertyId());
        Assertions.assertTrue(tenantDtoList.size() > 0);
    }
    @Test
    public void getTenantByIdSuccessTest() {
        Tenant tenant = new Tenant();
        tenant.setId(200);
        tenant.setUnitId(1);
        Tenant tenantSaved = tenantService.addTenant(tenant);
        Tenant tenantGet = tenantService.getTenantById(tenantSaved.getId());
        Assertions.assertEquals(tenantSaved.getId(), tenantGet.getId());
        Assertions.assertNotNull(tenantGet);
    }
    @Test
    public void getTenantByIdFailerTest() {
        Assertions.assertThrows(TenantNotFoundException.class, () -> tenantService.getTenantById(2000));
    }
    @Test
    public void updateTenant() {
        Tenant tenant = new Tenant();
        tenant.setId(200);
        tenant.setName("test1");
        tenant.setUnitId(19);
        Tenant tenantSaved = tenantService.addTenant(tenant);
        tenantSaved.setName("Test2");
        Tenant updatedTenant = tenantService.updateTenant(tenantSaved.getId(), tenantSaved);
        Assertions.assertEquals("Test2", updatedTenant.getName());
        Assertions.assertNotNull(updatedTenant);
    }
    @Test
    public void deleteTenantTest() {
        Tenant tenant = new Tenant();
        tenant.setId(200);
        tenant.setName("test1");
        Unit unit = new Unit();
        unit.setId(200);
        Building building = new Building();
        building.setId(200);
        unit.setBuildingId(200);
        tenant.setUnitId(200);
        Property property = new Property();
        property.setId(200);
        building.setPropertyId(200);
        property.setTenantCount(10);
        propertyService.addProperty(property);
        buildingService.addBuilding(building);
        unitService.addUnit(unit);
        Tenant tenantSaved = tenantService.addTenant(tenant);;
        tenantService.deleteTenantById(tenantSaved.getId());
        Assertions.assertThrows(TenantNotFoundException.class, () -> tenantService.getTenantById(tenantSaved.getId()));
    }
}
