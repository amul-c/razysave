package com.razysave.service.serviceImpl;

import com.razysave.dto.kpi.KPIDto;
import com.razysave.entity.kpi.KPI;
import com.razysave.exception.KPINotFoundException;
import com.razysave.repository.kpi.KPIRepository;
import com.razysave.service.kpi.KPIService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
public class KPIServiceImpl implements KPIService {
    private static final Logger logger = LoggerFactory.getLogger(KPIServiceImpl.class);
    @Autowired
    private KPIRepository kpiRepository;
    private ModelMapper modelMapper = new ModelMapper();

    @Override
    public List<KPIDto> getKPI(Integer propertyId) {
        logger.info("Enter of getKPI() method");
        List<KPI> kpiList = kpiRepository.findByPropertyId(propertyId);
        logger.info("End of getKPI() method");
        return kpiList.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public KPIDto getKPIById(Integer id) {
        logger.info("Enter of getKPIById(Integer id)  method");
        Optional<KPI> kpiOptional = kpiRepository.findById(id);
        if (kpiOptional.isPresent()) {
            logger.info("End of getKPIById(Integer id)  method");
            return modelMapper.map(kpiOptional.get(), KPIDto.class);
        } else {
            logger.info("End of getInsuranceIndexById(Integer id)  method with Exception");
            throw new KPINotFoundException("KPI not found with id: " + id);
        }
    }

    @Override
    public KPI addKPI(KPI kpi) {
        logger.info("Enter of addKPI(KPI kpi) method");
        return kpiRepository.save(kpi);
    }

    @Override
    public KPI updateKPIById(Integer id, KPI updatedKPI) {
        logger.info("Enter of updateKPIById(Integer id, KPI kpi) method");
        Optional<KPI> existingKpiOptional = kpiRepository.findById(id);
        if (existingKpiOptional.isPresent()) {
            KPI exisitingkpi = existingKpiOptional.get();
            if (updatedKPI.getGallons() != null) {
                exisitingkpi.setGallons(updatedKPI.getGallons());
            }
            if (updatedKPI.getExpenses() != null) {
                exisitingkpi.setExpenses(updatedKPI.getExpenses());
            }
            if (updatedKPI.getRevenue() != null) {
                exisitingkpi.setRevenue(updatedKPI.getRevenue());
            }
            if (updatedKPI.getPAndL() != null) {
                exisitingkpi.setPAndL(updatedKPI.getPAndL());
            }
            logger.info("End of updateKPIById(Integer id, KPI kpi) method");
            return kpiRepository.save(updatedKPI);
        } else {
            logger.info("End of updateKPIById(Integer id, KPI updatedKPI) method with Exception");
            throw new KPINotFoundException("KPI not found with id: " + id);
        }
    }

    @Override
    public void deleteKPIById(Integer id) {
        logger.info("Enter of deleteInsuranceIndexById(Integer id)  method");
        Optional<KPI> kpiOptional = kpiRepository.findById(id);
        if (kpiOptional.isPresent()) {
            logger.info("End of deleteInsuranceIndexById(Integer id) method deleted successfully");
            kpiRepository.deleteById(id);
        } else {
            throw new KPINotFoundException("KPI not found with id: " + id);
        }
    }

    private KPIDto mapToDto(KPI kpi) {
        KPIDto dto = modelMapper.map(kpi, KPIDto.class);
        return dto;
    }
}
