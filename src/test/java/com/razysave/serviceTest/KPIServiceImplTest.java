package com.razysave.serviceTest;

import com.razysave.dto.device.DeviceListDto;
import com.razysave.dto.kpi.KPIDto;
import com.razysave.entity.devices.Device;
import com.razysave.entity.kpi.KPI;
import com.razysave.exception.DeviceNotFoundException;
import com.razysave.exception.KPINotFoundException;
import com.razysave.service.kpi.KPIService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class KPIServiceImplTest {
    @Autowired
    KPIService kpiService;
    @Test
    public void getKPISuccessTest() {
        KPI kpi = new KPI();
        kpi.setPropertyId(1);
        kpi.setId(200);
        KPI kpiSaved = kpiService.addKPI(kpi);
        List<KPIDto> kpiDto = kpiService.getKPI(kpi.getPropertyId());
        Assertions.assertTrue(kpiDto.size() > 0);
    }
    @Test
    public void getKPIByIdSuccessTest() {
        KPI kpi = new KPI();
        kpi.setPropertyId(1);
        kpi.setId(200);
        KPI kpiSaved = kpiService.addKPI(kpi);
        KPIDto kpiGet = kpiService.getKPIById(kpiSaved.getId());
        Assertions.assertEquals(kpiSaved.getId(), kpiGet.getId());
        Assertions.assertNotNull(kpiGet);
    }
    @Test
    public void getKPIByIdFailerTest() {
        Assertions.assertThrows(KPINotFoundException.class, () -> kpiService.getKPIById(2000));
    }
    @Test
    public void updateDevice() {
        KPI kpi = new KPI();
        kpi.setPropertyId(1);
        kpi.setId(200);
        KPI kpiSaved = kpiService.addKPI(kpi);
        kpiSaved.setPropertyId(2);
        KPI updatedKPI = kpiService.updateKPIById(kpiSaved.getId(), kpiSaved);
        Assertions.assertEquals(2, updatedKPI.getPropertyId());
        Assertions.assertNotNull(updatedKPI);
    }
    @Test
    public void deleteBuildingTest() {
        KPI kpi = new KPI();
        kpi.setId(200);
        KPI kpiSaved = kpiService.addKPI(kpi);
        kpiService.deleteKPIById(kpiSaved.getId());
        Assertions.assertThrows(KPINotFoundException.class, () -> kpiService.getKPIById(kpiSaved.getId()));
    }
}
