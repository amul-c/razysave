package com.razysave.serviceTest;

import com.razysave.dto.InsuranceIndexDto;
import com.razysave.entity.insurance.InsuranceIndex;
import com.razysave.entity.property.Property;
import com.razysave.exception.InsuranceIndexNotFoundException;
import com.razysave.repository.insurance.InsuranceIndexRepository;
import com.razysave.service.serviceImpl.InsuranceIndex.InsuranceIndexServiceImpl;
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
public class InsuranceIndexServiceImplTest {
    @InjectMocks
    InsuranceIndexServiceImpl insuranceIndexService;
    @Mock
    private InsuranceIndexRepository insuranceIndexRepository;

    @Test
    public void getInsuranceIndexSuccessTest() {
        Property property = new Property();
        property.setId(200);
        InsuranceIndex insuranceIndex = new InsuranceIndex();
        insuranceIndex.setId(200);
        insuranceIndex.setPropertyId(property.getId());
        when(insuranceIndexRepository.findByPropertyId(insuranceIndex.getPropertyId())).thenReturn(Stream.of(insuranceIndex)
                .collect(Collectors.toList()));
        List<InsuranceIndexDto> indexDtos = insuranceIndexService.getInsuranceIndex(insuranceIndex.getPropertyId());
        Assertions.assertTrue(indexDtos.size() > 0);
    }

    @Test
    public void getInsuranceIndexByIdSuccessTest() {
        InsuranceIndex insuranceIndex = new InsuranceIndex();
        insuranceIndex.setId(200);
        insuranceIndex.setPropertyId(200);
        when(insuranceIndexRepository.findById(insuranceIndex.getId())).thenReturn(Optional.of(insuranceIndex));
        InsuranceIndexDto insuranceIndexDto = insuranceIndexService.getInsuranceIndexById(insuranceIndex.getId());
        Assertions.assertEquals(insuranceIndex.getId(), insuranceIndexDto.getId());
        Assertions.assertNotNull(insuranceIndexDto);
    }

    @Test
    public void getInsuranceIndexByIdFailerTest() {
        when(insuranceIndexRepository.findById(2000)).thenReturn(Optional.empty());
        Assertions.assertThrows(InsuranceIndexNotFoundException.class, () -> insuranceIndexService.getInsuranceIndexById(2000));
    }

    @Test
    public void updateInsurance() {
        InsuranceIndex insuranceIndex = new InsuranceIndex();
        insuranceIndex.setId(200);
        insuranceIndex.setPropertyId(200);
        when(insuranceIndexRepository.save(insuranceIndex)).thenReturn(insuranceIndex);
        InsuranceIndex insuranceIndexSaved = insuranceIndexService.addInsuranceIndex(insuranceIndex);
        insuranceIndexSaved.setPropertyId(2);
        when(insuranceIndexRepository.save(insuranceIndexSaved)).thenReturn(insuranceIndexSaved);
        when(insuranceIndexRepository.findById(insuranceIndexSaved.getId())).thenReturn(Optional.of(insuranceIndexSaved));
        InsuranceIndex insuranceIndexUpdated = insuranceIndexService.updateInsuranceIndexById(insuranceIndexSaved.getId(), insuranceIndexSaved);
        Assertions.assertEquals(2, insuranceIndexUpdated.getPropertyId());
        Assertions.assertNotNull(insuranceIndexUpdated);
    }

    @Test
    public void deleteInsuranceIndexTest() {
        InsuranceIndex insuranceIndex = new InsuranceIndex();
        insuranceIndex.setId(200);
        when(insuranceIndexRepository.save(insuranceIndex)).thenReturn(insuranceIndex);
        InsuranceIndex insuranceIndexSaved = insuranceIndexService.addInsuranceIndex(insuranceIndex);
        when(insuranceIndexRepository.findById(insuranceIndexSaved.getId())).thenReturn(Optional.of(insuranceIndexSaved));
        insuranceIndexService.deleteInsuranceIndexById(insuranceIndexSaved.getId());
        verify(insuranceIndexRepository, times(1)).deleteById(eq(insuranceIndexSaved.getId()));
    }
}
