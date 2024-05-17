package com.razysave.servicetest;

import com.razysave.dto.kpi.KPIDto;
import com.razysave.entity.kpi.KPI;
import com.razysave.exception.KPINotFoundException;
import com.razysave.repository.kpi.KPIRepository;
import com.razysave.service.serviceImpl.KPIServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@SpringBootTest
public class KPIServiceImplTest {
    @Mock
    private KPIRepository kpiRepository;

    @InjectMocks
    private KPIServiceImpl kpiService;
    @Test
    public void getKPISuccessTest() {
        KPI kpi = new KPI();
        kpi.setPropertyId(1);
        kpi.setId(200);
        when(kpiRepository.findByPropertyId(kpi.getPropertyId()))
                .thenReturn(Stream.of(kpi).collect(Collectors.toList()));
        List<KPIDto> kpiDto = kpiService.getKPI(kpi.getPropertyId());
        Assertions.assertTrue(kpiDto.size() > 0);
    }
    @Test
    public void getKPIByIdSuccessTest() {
        KPI kpi = new KPI();
        kpi.setPropertyId(1);
        kpi.setId(200);
        when(kpiRepository.findById(200)).thenReturn(Optional.of(kpi));
        KPIDto kpiGet = kpiService.getKPIById(kpi.getId());
        Assertions.assertEquals(kpiGet.getId(), kpiGet.getId());
        Assertions.assertNotNull(kpiGet);
    }
    @Test
    public void getKPIByIdFailerTest() {
        when(kpiRepository.findById(2000)).thenReturn(Optional.empty());
        Assertions.assertThrows(KPINotFoundException.class, () -> kpiService.getKPIById(2000));
    }
    @Test
    public void updateKPI() {
        KPI kpi = new KPI();
        kpi.setPropertyId(1);
        kpi.setId(200);
        when(kpiRepository.save(kpi)).thenReturn(kpi);
        KPI kpiSaved = kpiService.addKPI(kpi);
        kpiSaved.setPropertyId(2);
        when(kpiRepository.findById(kpiSaved.getId())).thenReturn(Optional.of(kpiSaved));
        when(kpiRepository.save(kpi)).thenReturn(kpiSaved);
        KPI updatedKPI = kpiService.updateKPIById(kpiSaved.getId(), kpiSaved);
        Assertions.assertEquals(2, updatedKPI.getPropertyId());
        Assertions.assertNotNull(updatedKPI);
    }
    @Test
    public void deleteKPITest() {
        KPI kpi = new KPI();
        kpi.setId(200);
        when(kpiRepository.save(kpi)).thenReturn(kpi);
        KPI kpiSaved = kpiService.addKPI(kpi);
        when(kpiRepository.findById(kpiSaved.getId())).thenReturn(Optional.of(kpiSaved));
        kpiService.deleteKPIById(kpiSaved.getId());
        verify(kpiRepository, times(1)).deleteById(eq(kpi.getId()));
    }
}
