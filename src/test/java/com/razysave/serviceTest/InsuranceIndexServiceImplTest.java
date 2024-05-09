package com.razysave.serviceTest;

import com.razysave.dto.InsuranceIndexDto;
import com.razysave.entity.insurance.InsuranceIndex;
import com.razysave.entity.property.Property;
import com.razysave.exception.InsuranceIndexNotFoundException;
import com.razysave.service.insurance.InsuranceIndexService;
import com.razysave.service.property.PropertyService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class InsuranceIndexServiceImplTest {
   @Autowired
    InsuranceIndexService insuranceIndexService;
    @Autowired
    PropertyService propertyService;
    @Test
    public void getInsuranceIndexSuccessTest() {
        Property property = new Property();
        property.setId(200);
        InsuranceIndex insuranceIndex = new InsuranceIndex();
        insuranceIndex.setId(200);
        propertyService.addProperty(property);
        InsuranceIndex insuranceIndexSaved = insuranceIndexService.addInsuranceIndex(insuranceIndex);
        List<InsuranceIndexDto> indexDtos = insuranceIndexService.getInsuranceIndex(insuranceIndexSaved.getPropertyId());
        Assertions.assertTrue(indexDtos.size() > 0);
    }

    @Test
    public void getInsuranceIndexByIdSuccessTest() {

        InsuranceIndex insuranceIndex = new InsuranceIndex();
        insuranceIndex.setId(200);
        insuranceIndex.setPropertyId(200);
        InsuranceIndex insuranceIndexSaved = insuranceIndexService.addInsuranceIndex(insuranceIndex);
        InsuranceIndexDto insuranceIndexDto = insuranceIndexService.getInsuranceIndexById(insuranceIndexSaved.getId());
        Assertions.assertEquals(insuranceIndexSaved.getId(), insuranceIndexDto.getId());
        Assertions.assertNotNull(insuranceIndexDto);
    }

    @Test
    public void getInsuranceIndexByIdFailerTest() {
        Assertions.assertThrows(InsuranceIndexNotFoundException.class, () -> insuranceIndexService.getInsuranceIndexById(2000));
    }

    @Test
    public void updateInsurance() {
        InsuranceIndex insuranceIndex = new InsuranceIndex();
        insuranceIndex.setId(200);
        insuranceIndex.setPropertyId(200);
        InsuranceIndex insuranceIndexSaved = insuranceIndexService.addInsuranceIndex(insuranceIndex);
        insuranceIndexSaved.setPropertyId(2);
        InsuranceIndex insuranceIndexUpdated = insuranceIndexService.updateInsuranceIndexById(insuranceIndexSaved.getId(), insuranceIndexSaved);
        Assertions.assertEquals(2, insuranceIndexUpdated.getPropertyId());
        Assertions.assertNotNull(insuranceIndexUpdated);
    }

    @Test
    public void deleteInsuranceIndexTest() {
        InsuranceIndex insuranceIndex = new InsuranceIndex();
        insuranceIndex.setId(200);
        InsuranceIndex insuranceIndexSaved = insuranceIndexService.addInsuranceIndex(insuranceIndex);
        insuranceIndexService.deleteInsuranceIndexById(insuranceIndexSaved.getId());
        Assertions.assertThrows(InsuranceIndexNotFoundException.class, () -> insuranceIndexService.getInsuranceIndexById(insuranceIndexSaved.getId()));
    }
}
