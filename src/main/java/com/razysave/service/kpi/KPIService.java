package com.razysave.service.kpi;

import com.razysave.dto.kpi.KPIDto;
import com.razysave.entity.kpi.KPI;

import java.util.List;

public interface KPIService {
    public List<KPIDto> getKPI(Integer propertyId);

    public KPIDto getKPIById(Integer id);

    public KPI addKPI(KPI kpi);

    public KPI updateKPIById(Integer id, KPI kpi);

    public void deleteKPIById(Integer id);
}
