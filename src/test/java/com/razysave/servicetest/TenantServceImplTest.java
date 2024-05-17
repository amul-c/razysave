package com.razysave.servicetest;

import com.razysave.dto.tenant.TenantDto;
import com.razysave.entity.property.Building;
import com.razysave.entity.property.Property;
import com.razysave.entity.property.Unit;
import com.razysave.entity.tenant.Tenant;
import com.razysave.exception.TenantNotFoundException;
import com.razysave.repository.property.BuildingRepository;
import com.razysave.repository.property.PropertyRepository;
import com.razysave.repository.property.UnitRepository;
import com.razysave.repository.tenant.TenantRepository;
import com.razysave.service.property.UnitService;
import com.razysave.service.serviceImpl.property.TenantServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@SpringBootTest
public class TenantServceImplTest {
    @InjectMocks
    TenantServiceImpl tenantService;

    @Mock
    private UnitService unitService;
    @Mock
    private TenantRepository tenantRepository;
    @Mock
    private UnitRepository unitRepository;
    @Mock
    private BuildingRepository buildingRepository;
    @Mock
    private PropertyRepository propertyRepository;

    @Test
    public void getTenantSuccessTest() {
        Tenant tenant = new Tenant();
        tenant.setName("Test1");
        tenant.setId(200);
        tenant.setUnitId(19);
        when(tenantRepository.findByUnitId(tenant.getUnitId()))
                .thenReturn(Collections.singletonList(tenant));
        List<TenantDto> tenantDtoList = tenantService.getTenants(tenant.getUnitId());
        assertNotNull(tenantDtoList);
        assertFalse(tenantDtoList.isEmpty());
    }
    @Test
    public void getTenantByPropertyIdSuccessTest() {
        Tenant tenant = new Tenant();
        tenant.setName("Test1");
        tenant.setId(200);
        tenant.setUnitId(19);
        tenant.setPropertyId(200);
        when(tenantRepository.findByPropertyId(tenant.getPropertyId()))
                .thenReturn(Collections.singletonList(tenant));
        List<TenantDto> tenantDtoList = tenantService.getTenantsByPropertyId(tenant.getPropertyId());
        Assertions.assertTrue(tenantDtoList.size() > 0);
    }
    @Test
    public void getTenantByPropertyIdFailerTest() {
        when(tenantRepository.findByPropertyId(200)).thenReturn(Collections.emptyList());
        Assertions.assertThrows(TenantNotFoundException.class, () -> tenantService.getTenantsByPropertyId(200));
    }
    @Test
    public void getTenantByIdSuccessTest() {
        Tenant tenant = new Tenant();
        tenant.setId(200);
        tenant.setUnitId(1);
        when(tenantRepository.findById(200)).thenReturn(Optional.of(tenant));
        Tenant tenantGet = tenantService.getTenantById(tenant.getId());
        Assertions.assertNotNull(tenantGet);
    }
    @Test
    public void getTenantByIdFailerTest() {
        when(tenantRepository.findById(2000)).thenReturn(Optional.empty());
        Assertions.assertThrows(TenantNotFoundException.class, () -> tenantService.getTenantById(2000));
    }
    @Test
    public void updateTenant() {
        Integer tenantId = 1;
        Integer unitId = 1;
        Tenant updatedTenant = new Tenant();
        updatedTenant.setId(tenantId);
        updatedTenant.setName("Updated Name");
        Tenant existingTenant = new Tenant();
        existingTenant.setId(tenantId);
        existingTenant.setUnitId(unitId);
        existingTenant.setName("existing Name");
        Unit unit = new Unit();
        unit.setId(unitId);
        unit.setTenant(existingTenant);
        when(tenantRepository.findById(tenantId)).thenReturn(Optional.of(existingTenant));
        when(unitRepository.findById(unitId)).thenReturn(Optional.of(unit));
        when(tenantRepository.save(existingTenant)).thenReturn(updatedTenant);
        Tenant result = tenantService.updateTenant(tenantId, existingTenant);
        Assertions.assertEquals("Updated Name", result.getName());
        verify(tenantRepository, times(1)).findById(tenantId);
        verify(unitRepository, times(1)).findById(unitId);
        verify(unitService, times(1)).updateUnit(eq(unitId), any(Unit.class));
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
        when(unitRepository.findById(tenant.getUnitId())).thenReturn(Optional.of(unit));
        when(propertyRepository.findById(building.getPropertyId())).thenReturn(Optional.of(property));
        when(buildingRepository.findById(building.getId())).thenReturn(Optional.of(building));
        when(tenantRepository.findById(building.getId())).thenReturn(Optional.of(tenant));
        tenantService.deleteTenantById(tenant.getId());
        verify(tenantRepository, times(1)).deleteById(eq(tenant.getId()));
    }
}
