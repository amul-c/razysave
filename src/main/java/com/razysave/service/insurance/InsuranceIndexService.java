package com.razysave.service.insurance;

import com.razysave.dto.InsuranceIndexDto;
import com.razysave.entity.insurance.InsuranceIndex;

import java.util.List;

public interface InsuranceIndexService {
    public List<InsuranceIndexDto> getInsuranceIndex(Integer propertyId);

    public InsuranceIndexDto getInsuranceIndexById(Integer id);

    public InsuranceIndex addInsuranceIndex(InsuranceIndex insuranceIndex);

    public InsuranceIndex updateInsuranceIndexById(Integer id, InsuranceIndex insuranceIndex);

    public void deleteInsuranceIndexById(Integer id);
}
