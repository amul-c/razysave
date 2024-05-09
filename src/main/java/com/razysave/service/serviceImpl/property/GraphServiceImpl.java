package com.razysave.service.serviceImpl.property;

import com.razysave.dto.property.GraphMonthDto;
import com.razysave.dto.property.GraphWeekDto;
import com.razysave.dto.property.GraphYearDto;
import com.razysave.entity.property.GraphProperty;
import com.razysave.entity.property.GraphUnit;
import com.razysave.exception.GraphNotFoundException;
import com.razysave.repository.property.GraphPropertyRepository;
import com.razysave.repository.property.GraphUnitRepository;
import com.razysave.service.property.GraphService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GraphServiceImpl implements GraphService {
    @Autowired
    GraphPropertyRepository graphRepository;
    @Autowired
    GraphUnitRepository graphUnitRepository;
    private ModelMapper modelMapper = new ModelMapper();
    private static final Logger logger = LoggerFactory.getLogger(GraphService.class);
    public List<GraphWeekDto> getGraphPropertyByWeek(Integer propertyId) {
        logger.info("Enter  getGraphPropertyByWeek(Integer propertyId)  with propertyId {}", propertyId);
        GraphProperty graph = graphRepository.findByPropertyId(propertyId);
        if (graph != null) {
            logger.info("Enter  getGraphPropertyByWeek(Integer propertyId)  with propertyId {}", propertyId);
            return mapToGraphPropertyWeekDto(graph);
        } else {
            logger.info("Exit getGraphPropertyByWeek(Integer propertyId)  Empty");
            throw new GraphNotFoundException("Graph not found");
        }
    }

    public List<GraphWeekDto> getGraphUnitByWeek(Integer unitId) {
        logger.info("Enter getGraphUnitByWeek(Integer unitId) with unitId {}", unitId);
        GraphUnit graph = graphUnitRepository.findByUnitId(unitId);
        if (graph != null) {
            logger.info("Exit getGraphUnitByWeek(Integer unitId) with unitId {}", unitId);
            return mapToGraphUnitWeekDto(graph);
        } else {
            logger.info("Exit getGraphUnitByWeek(Integer unitId)  Empty");
            throw new GraphNotFoundException("Graph not found");
        }
    }


    public List<GraphMonthDto> getGraphPropertyhByMonth(Integer propertyId) {
        logger.info("Enter getGraphPropertyhByMonth(Integer propertyId) with propertyId {}", propertyId);
        GraphProperty graph = graphRepository.findByPropertyId(propertyId);
        if(graph!=null) {
            logger.info("Exit getGraphPropertyhByMonth(Integer propertyId) with propertyId {}", propertyId);
            return mapToGraphPropertyMonthDto(graph);
        } else {
            logger.info("Exit getGraphPropertyhByMonth(Integer propertyId)  Empty");
            throw new GraphNotFoundException("Graph not found");
        }
    }

    public List<GraphMonthDto> getGraphUnitByMonth(Integer propertyId) {
        logger.info("Enter getGraphUnitByMonth(Integer propertyId) with propertyId {}", propertyId);
        GraphUnit graph = graphUnitRepository.findByUnitId(propertyId);
        if(graph!=null) {
            logger.info("Exit getGraphUnitByMonth(Integer propertyId) with propertyId {}", propertyId);
            return mapToGraphUnitMonthDto(graph);
        }
        else {
            logger.info("Exit getGraphUnitByMonth(Integer propertyId) Empty");
            throw new GraphNotFoundException("Graph not found");
        }
    }

    public List<GraphYearDto> getGraphByPropertyYear(Integer propertyId) {
        logger.info("Enter getGraphUnitByMonth(Integer propertyId) with propertyId {}", propertyId);
        GraphProperty graph = graphRepository.findByPropertyId(propertyId);
        logger.info("Exit getGraphUnitByMonth(Integer propertyId) Empty");
        return mapToGraphPropertyYearDto(graph);
    }

    public List<GraphYearDto> getGraphByUnitYear(Integer propertyId) {
        logger.info("Enter getGraphByUnitYear(Integer propertyId) with propertyId {}", propertyId);
        GraphUnit graph = graphUnitRepository.findByUnitId(propertyId);
        if(graph!=null) {
            logger.info("Exit getGraphByUnitYear(Integer propertyId) with propertyId {}", propertyId);
            return mapToGraphUnitYearDto(graph);
        }
        else{
            logger.info("Exit getGraphByUnitYear(Integer propertyId) Empty");
            throw new GraphNotFoundException("Graph not found");
        }
    }

    public GraphProperty getGraphPropertytById(Integer id) {
        logger.info("Enter getGraphPropertytById(Integer id) with id {}", id);
        Optional<GraphProperty> graph = graphRepository.findById(id);
        if (graph.isPresent()) {
            logger.info("Exit getGraphPropertytById(Integer id) with id {}", id);
            return graph.get();
        }else {
            logger.info("Exit getGraphPropertytById(Integer id) Empty");
            throw new GraphNotFoundException("Graph of property water Usage not Found with id" + id);
        }
    }

    public GraphUnit getGraphUnitById(Integer id) {
        logger.info("Enter getGraphUnitById(Integer id with id {}", id);
        Optional<GraphUnit> graph = graphUnitRepository.findById(id);
        if (graph.isPresent()) {
            logger.info("Exit getGraphUnitById(Integer id) with id {}", id);
            return graph.get();
        }
        else {
            logger.info("Exit getGraphUnitById(Integer id) Empty");
            throw new GraphNotFoundException("Graph of unit water usage not Found with id" + id);
        }
    }
    public GraphProperty addGraphProperty(GraphProperty graph) {
        logger.info("Enter addGraphProperty(GraphProperty graph)");
        GraphProperty graphSaved = graphRepository.save(graph);
        logger.info("Exit addGraphProperty(GraphProperty graph)");
        return graphSaved;
    }

    public GraphUnit addGraphUnit(GraphUnit graph) {
        logger.info("Enter addGraphUnit(GraphUnit graph)");
        GraphUnit graphSaved = graphUnitRepository.save(graph);
        logger.info("Exit addGraphUnit(GraphUnit graph)");
        return graphSaved;
    }


    public GraphUnit updateGraphUnit(GraphUnit graphUnitUpdated, Integer id) {
        logger.info("Enter updateGraphUnit(GraphUnit graphUnitUpdated, Integer id) ");
        Optional<GraphUnit> graphUnit = graphUnitRepository.findById(id);
        if (graphUnit.isPresent()) {
            GraphUnit unit = graphUnit.get();
            if (graphUnitUpdated.getEstWeek() != null)
                unit.setEstWeek(graphUnitUpdated.getEstWeek());
            if (graphUnitUpdated.getCurWeek() != null)
                unit.setCurWeek(graphUnitUpdated.getCurWeek());
            if (graphUnitUpdated.getEstMonth() != null)
                unit.setEstMonth(graphUnitUpdated.getEstMonth());
            if (graphUnitUpdated.getEstYear() != null)
                unit.setEstYear(graphUnitUpdated.getEstYear());
            if (graphUnitUpdated.getCurYear() != null)
                unit.setCurYear(graphUnitUpdated.getCurYear());
            if (graphUnitUpdated.getPropertyId() != null)
                unit.setPropertyId(graphUnitUpdated.getPropertyId());
            logger.info("Exit updateGraphUnit(GraphUnit graphUnitUpdated, Integer id) ");
            return graphUnitRepository.save(unit);
        } else {
            logger.info("Exit updateGraphUnit(GraphUnit graphUnitUpdated, Integer id)");
            throw new GraphNotFoundException("graph not found with id" + id);
        }
    }

    public GraphProperty updateGraphProperty(GraphProperty graphPropertyUpdated, Integer id) {
        logger.info("Enter updateGraphProperty(GraphProperty graphPropertyUpdated, Integer id)");
        Optional<GraphProperty> graphProperty = graphRepository.findById(id);
        if (graphProperty.isPresent()) {
            GraphProperty unit = graphProperty.get();
            if (graphPropertyUpdated.getEstWeek() != null)
                unit.setEstWeek(graphPropertyUpdated.getEstWeek());
            if (graphPropertyUpdated.getCurWeek() != null)
                unit.setCurWeek(graphPropertyUpdated.getCurWeek());
            if (graphPropertyUpdated.getEstMonth() != null)
                unit.setEstMonth(graphPropertyUpdated.getEstMonth());
            if (graphPropertyUpdated.getEstYear() != null)
                unit.setEstYear(graphPropertyUpdated.getEstYear());
            if (graphPropertyUpdated.getCurYear() != null)
                unit.setCurYear(graphPropertyUpdated.getCurYear());
            if (graphPropertyUpdated.getPropertyId() != null)
                unit.setPropertyId(graphPropertyUpdated.getPropertyId());
            logger.info("Exit updateGraphProperty(GraphProperty graphPropertyUpdated, Integer id)");
            return graphRepository.save(unit);
        } else {
            logger.info("Exiy updateGraphProperty(GraphProperty graphPropertyUpdated, Integer id) Empty");
            throw new GraphNotFoundException("graph not found with id" + id);
        }
    }

    public void deleteGraphUnit(Integer id) {
        logger.info("Enter deleteGraphUnit(Integer id)");
        Optional<GraphUnit> graphUnit = graphUnitRepository.findById(id);
        if (graphUnit.isPresent()) {
            logger.info("Exit deleteGraphUnit(Integer id)");
            graphUnitRepository.delete(graphUnit.get());
        } else {
            logger.info("Exit deleteGraphUnit(Integer id) Empty");
            throw new GraphNotFoundException("graph not found with id" + id);
        }
    }

    public void deleteGraphProperty(Integer id) {
        logger.info("Enter deleteGraphProperty(Integer id)");
        Optional<GraphProperty> graphProperty = graphRepository.findById(id);
        if (graphProperty.isPresent()) {
            logger.info("Exit deleteGraphProperty(Integer id)");
            graphRepository.delete(graphProperty.get());
        } else{
            logger.info("Exit deleteGraphProperty(Integer id)");
            throw new GraphNotFoundException("graph not found with id" + id);
        }
    }
    private List<GraphWeekDto> mapToGraphPropertyWeekDto(GraphProperty graph) {
        List<GraphWeekDto> graphWeekDtos = new ArrayList<>();
        GraphWeekDto graphEstWeekDto = new GraphWeekDto();
        graphEstWeekDto.setPropertyId(graph.getPropertyId());
        graphEstWeekDto.setId(1);
        graphEstWeekDto.setWeek(graph.getEstWeek());
        GraphWeekDto graphCurWeekDto = new GraphWeekDto();
        graphCurWeekDto.setPropertyId(graph.getPropertyId());
        graphCurWeekDto.setId(1);
        graphCurWeekDto.setWeek(graph.getCurWeek());
        graphWeekDtos.add(graphEstWeekDto);
        graphWeekDtos.add(graphCurWeekDto);
        return graphWeekDtos;
    }

    private List<GraphWeekDto> mapToGraphUnitWeekDto(GraphUnit graph) {
        List<GraphWeekDto> graphWeekDtos = new ArrayList<>();
        GraphWeekDto graphEstWeekDto = new GraphWeekDto();
        graphEstWeekDto.setPropertyId(graph.getPropertyId());
        graphEstWeekDto.setId(1);
        graphEstWeekDto.setWeek(graph.getEstWeek());
        GraphWeekDto graphCurWeekDto = new GraphWeekDto();
        graphCurWeekDto.setPropertyId(graph.getPropertyId());
        graphCurWeekDto.setId(1);
        graphCurWeekDto.setWeek(graph.getCurWeek());
        graphWeekDtos.add(graphEstWeekDto);
        graphWeekDtos.add(graphCurWeekDto);
        return graphWeekDtos;
    }

    private List<GraphMonthDto> mapToGraphPropertyMonthDto(GraphProperty graph) {
        List<GraphMonthDto> graphMonthDtos = new ArrayList<>();
        GraphMonthDto estMonthDto = new GraphMonthDto();
        estMonthDto.setPropertyId(graph.getPropertyId());
        estMonthDto.setId(graph.getId());
        estMonthDto.setMonth(graph.getEstMonth());
        GraphMonthDto curMonthDto = new GraphMonthDto();
        curMonthDto.setPropertyId(graph.getPropertyId());
        curMonthDto.setId(graph.getId());
        curMonthDto.setMonth(graph.getCurMonth());
        graphMonthDtos.add(estMonthDto);
        graphMonthDtos.add(curMonthDto);
        return graphMonthDtos;
    }

    private List<GraphMonthDto> mapToGraphUnitMonthDto(GraphUnit graph) {
        List<GraphMonthDto> graphMonthDtos = new ArrayList<>();
        GraphMonthDto estMonthDto = new GraphMonthDto();
        estMonthDto.setPropertyId(graph.getPropertyId());
        estMonthDto.setId(graph.getId());
        estMonthDto.setMonth(graph.getEstMonth());
        GraphMonthDto curMonthDto = new GraphMonthDto();
        curMonthDto.setPropertyId(graph.getPropertyId());
        curMonthDto.setId(graph.getId());
        estMonthDto.setMonth(graph.getEstMonth());
        graphMonthDtos.add(estMonthDto);
        graphMonthDtos.add(curMonthDto);
        return graphMonthDtos;
    }

    private List<GraphYearDto> mapToGraphPropertyYearDto(GraphProperty graph) {
        List<GraphYearDto> graphYearDtos = new ArrayList<>();
        GraphYearDto estYearDto = new GraphYearDto();
        estYearDto.setPropertyId(graph.getPropertyId());
        estYearDto.setId(graph.getId());
        estYearDto.setYear(graph.getEstYear());
        GraphYearDto curYearDto = new GraphYearDto();
        curYearDto.setPropertyId(graph.getPropertyId());
        curYearDto.setId(graph.getId());
        curYearDto.setYear(graph.getCurYear());
        graphYearDtos.add(estYearDto);
        graphYearDtos.add(curYearDto);
        return graphYearDtos;
    }

    private List<GraphYearDto> mapToGraphUnitYearDto(GraphUnit graph) {
        List<GraphYearDto> graphYearDtos = new ArrayList<>();
        GraphYearDto estYearDto = new GraphYearDto();
        estYearDto.setPropertyId(graph.getPropertyId());
        estYearDto.setId(graph.getId());
        estYearDto.setYear(graph.getEstYear());
        GraphYearDto curYearDto = new GraphYearDto();
        curYearDto.setPropertyId(graph.getPropertyId());
        curYearDto.setId(graph.getId());
        curYearDto.setYear(graph.getCurYear());
        graphYearDtos.add(estYearDto);
        graphYearDtos.add(curYearDto);
        return graphYearDtos;
    }
}
