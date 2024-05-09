package com.razysave.service.property;

import com.razysave.dto.InsuranceIndexDto;
import com.razysave.dto.property.GraphMonthDto;
import com.razysave.dto.property.GraphWeekDto;
import com.razysave.dto.property.GraphYearDto;
import com.razysave.entity.insurance.InsuranceIndex;
import com.razysave.entity.property.GraphProperty;
import com.razysave.entity.property.GraphUnit;


import java.util.List;

public interface GraphService {
    public List<GraphWeekDto> getGraphPropertyByWeek(Integer propertyId);
    public List<GraphWeekDto> getGraphUnitByWeek(Integer propertyId);
    public List<GraphMonthDto> getGraphPropertyhByMonth(Integer propertyId) ;
    public List<GraphMonthDto> getGraphUnitByMonth(Integer propertyId);
    public List<GraphYearDto> getGraphByPropertyYear(Integer propertyId);
    public List<GraphYearDto> getGraphByUnitYear(Integer propertyId);
    public GraphProperty addGraphProperty(GraphProperty graph);
    public GraphUnit addGraphUnit(GraphUnit graph);
    public void deleteGraphProperty(Integer id);
    public void deleteGraphUnit(Integer id);
    public GraphUnit getGraphUnitById(Integer id);
    public GraphProperty getGraphPropertytById(Integer id);
    public GraphUnit updateGraphUnit(GraphUnit  graphUnitUpdated, Integer id );
    public GraphProperty updateGraphProperty(GraphProperty graphProperty,Integer id);
}
