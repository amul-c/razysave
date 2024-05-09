package com.razysave.controller;

import com.razysave.dto.property.GraphMonthDto;
import com.razysave.dto.property.GraphWeekDto;
import com.razysave.dto.property.GraphYearDto;
import com.razysave.entity.property.GraphProperty;
import com.razysave.entity.property.GraphUnit;
import com.razysave.exception.GraphNotFoundException;
import com.razysave.service.property.GraphService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/graph")
public class GraphController {
    private static final Logger logger = LoggerFactory.getLogger(GraphController.class);
    @Autowired
    private GraphService graphService;
  @GetMapping("/week/property/{propertyId}")
    public ResponseEntity<List<GraphWeekDto>> getGraphPropertyByWeek(@PathVariable Integer propertyId) {
        try {
            logger.info("Enter  getGraphPropertyByWeek(@PathVariable Integer propertyId) with propertyId {}", propertyId);
          List<GraphWeekDto> graphWeekDto = graphService.getGraphPropertyByWeek(propertyId);
            logger.info("Exit  getGraphPropertyByWeek(@PathVariable Integer propertyId) with propertyId {}", propertyId);
            return ResponseEntity.ok(graphWeekDto);
        } catch (GraphNotFoundException e) {
            logger.info("Exit  getGraphPropertyByWeek(@PathVariable Integer propertyId) with propertyId {}", e.getMessage());
            return ResponseEntity.ok(Collections.emptyList());
        }
    }

    @GetMapping("/month/property/{propertyId}")
    public ResponseEntity<List<GraphMonthDto>> getGraphPropertyByMonth(@PathVariable Integer propertyId) {
        try {
            logger.info("Enter getGraphPropertyByMonth(@PathVariable Integer propertyId) with propertyId {}", propertyId);
            List<GraphMonthDto> graphMonthDto = graphService.getGraphPropertyhByMonth(propertyId);
            logger.info("Exit getGraphPropertyByMonth(@PathVariable Integer propertyId) with propertyId {}", propertyId);
            return ResponseEntity.ok(graphMonthDto);
        } catch (GraphNotFoundException e) {
            logger.info("Exit getGraphPropertyByWeek(@PathVariable Integer propertyId) with Exception {}", e.getMessage());
            return ResponseEntity.ok(Collections.emptyList());
        }
    }

    @GetMapping("/year/property/{propertyId}")
    public ResponseEntity<List<GraphYearDto>> getGraphUnitByYear(@PathVariable Integer propertyId) {
        try {
            logger.info("Enter getGraphUnitByYear(@PathVariable Integer propertyId) with propertyId {}", propertyId);
            List<GraphYearDto> graphYearDto = graphService.getGraphByPropertyYear(propertyId);
            logger.info("Exit getGraphUnitByYear(@PathVariable Integer propertyId) with propertyId {}", propertyId);
            return ResponseEntity.ok(graphYearDto);
        } catch (GraphNotFoundException e) {
            logger.info("Exit getGraphUnitByYear(@PathVariable Integer propertyId) with Exception {}", e.getMessage());
            return ResponseEntity.ok(Collections.emptyList());
        }
    }

    @GetMapping("/week/unit/{unitId}")
    public ResponseEntity<List<GraphWeekDto>> getGraphUnitByWeek(@PathVariable Integer unitId) {
        try {
            logger.info("Enter getGraphUnitByWeek(@PathVariable Integer unitId) with unitId {}", unitId);
            List<GraphWeekDto> graphWeekDto = graphService.getGraphUnitByWeek(unitId);
            logger.info("Exit getGraphUnitByWeek(@PathVariable Integer unitId) with unitId {}", unitId);
            return ResponseEntity.ok(graphWeekDto);
        } catch (GraphNotFoundException e) {
            logger.info("Exit getGraphUnitByWeek(@PathVariable Integer unitId) with Exception {}", e.getMessage());
            return ResponseEntity.ok(Collections.emptyList());
        }
    }

    @GetMapping("/month/unit/{unitId}")
    public ResponseEntity<List<GraphMonthDto>> getGraphUnitByMonth(@PathVariable Integer unitId) {
        try {
            logger.info("Enter getGraphUnitByMonth(@PathVariable Integer unitId) with unitId {}", unitId);
            List<GraphMonthDto> graphMonthDto = graphService.getGraphPropertyhByMonth(unitId);
            logger.info("Exit getGraphUnitByMonth(@PathVariable Integer unitId) with unitId {}", unitId);
            return ResponseEntity.ok(graphMonthDto);
        } catch (GraphNotFoundException e) {
            logger.info("Exit getGraphUnitByMonth(@PathVariable Integer unitId) with exception {}", e.getMessage());
            return ResponseEntity.ok(Collections.emptyList());
        }
    }

    @GetMapping("/year/unit/{unitId}")
    public ResponseEntity<List<GraphYearDto>> getGraphPropertyByYear(@PathVariable Integer unitId) {
        try {
            logger.info("Enter getGraphPropertyByYear(@PathVariable Integer unitId) with unitId {}", unitId);
            List<GraphYearDto> graphYearDto = graphService.getGraphByPropertyYear(unitId);
            logger.info("Exit getGraphPropertyByYear(@PathVariable Integer unitId) with unitId {}", unitId);
            return ResponseEntity.ok(graphYearDto);
        } catch (GraphNotFoundException e) {
            logger.info("Exit getGraphPropertyByYear(@PathVariable Integer unitId) with unitId {}", unitId);
            return ResponseEntity.ok(Collections.emptyList());
        }
    }

    @PostMapping("/property")
    public ResponseEntity<GraphProperty> addGraphProperty(@RequestBody GraphProperty graph) {
        try {
            logger.info("Enter addGraphProperty(@RequestBody GraphProperty graph)");
            GraphProperty graph1 = graphService.addGraphProperty(graph);
            logger.info("Exit addGraphProperty(@RequestBody GraphProperty graph)");
            return ResponseEntity.ok(graph1);

        } catch (GraphNotFoundException e) {
            logger.info("Exit addGraphProperty(@RequestBody GraphProperty graph) with Exception {}", e.getMessage());
            return ResponseEntity.noContent().build();
        }
    }

    @PostMapping("/unit")
    public ResponseEntity<GraphUnit> addGraphUnit(@RequestBody GraphUnit graph) {
        try {
            logger.info("Enter addGraphUnit(@RequestBody GraphUnit graph)");
            GraphUnit graph1 = graphService.addGraphUnit(graph);
            logger.info("Exit addGraphUnit(@RequestBody GraphUnit graph)");
            return ResponseEntity.ok(graph1);

        } catch (GraphNotFoundException e) {
            logger.info("Exit addGraphUnit(@RequestBody GraphUnit graph) {}", e.getMessage());
            return ResponseEntity.unprocessableEntity().build();
        }
    }

    @PutMapping("/{graphId}/unit")
    public ResponseEntity<GraphUnit> updateGraphUnit(@PathVariable Integer graphId, @RequestBody GraphUnit graphUnit) {
        try {
            logger.info("Enter updateGraphUnit(@PathVariable Integer graphId,@RequestBody GraphUnit graphUnit)");
            GraphUnit graph1 = graphService.updateGraphUnit(graphUnit, graphId);
            logger.info("Exit updateGraphUnit(@PathVariable Integer graphId,@RequestBody GraphUnit graphUnit)");
            return ResponseEntity.accepted().body(graph1);
        } catch (GraphNotFoundException e) {
            logger.info("Exit updateGraphUnit(@PathVariable Integer graphId,@RequestBody GraphUnit graphUnit)");
            return ResponseEntity.noContent().build();
        }
    }

    @PutMapping("/{graphId}/property")
    public ResponseEntity<GraphProperty> updateGraphProperty(@PathVariable Integer graphId, @RequestBody GraphProperty graphProperty) {
        try {
            logger.info("Enter  updateGraphProperty(@PathVariable Integer graphId,@RequestBody GraphProperty graphProperty) ");
            GraphProperty graph1 = graphService.updateGraphProperty(graphProperty, graphId);
            logger.info("Exit  updateGraphProperty(@PathVariable Integer graphId,@RequestBody GraphProperty graphProperty) ");
            return ResponseEntity.accepted().body(graph1);
        } catch (GraphNotFoundException e) {
            logger.info("Exit  updateGraphProperty(@PathVariable Integer graphId,@RequestBody GraphProperty graphProperty) with exception {} ", e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("{graphId}/property")
    public ResponseEntity<GraphUnit> deleteGraphProperty(@PathVariable Integer graphId) {
        try {
            logger.info("Enter deleteGraphProperty(@PathVariable Integer graphId) {}", graphId);
            graphService.deleteGraphProperty(graphId);
            logger.info("Exit deleteGraphProperty(@PathVariable Integer graphId) {}", graphId);
            return ResponseEntity.accepted().build();
        } catch (GraphNotFoundException e) {
            logger.info("Exit deleteGraphProperty(@PathVariable Integer graphId) {}", graphId);
            return ResponseEntity.noContent().build();
        }
    }

    @DeleteMapping("{graphId}/unit")
    public ResponseEntity<GraphUnit> deleteGraphUnit(@PathVariable Integer graphId) {
        try {
            logger.info("Enter deleteGraphUnit(@PathVariable Integer graphId) {}", graphId);
            graphService.deleteGraphUnit(graphId);
            logger.info("Exit deleteGraphUnit(@PathVariable Integer graphId) {}", graphId);
            return ResponseEntity.accepted().build();
        } catch (GraphNotFoundException e) {
            logger.info("Exit deleteGraphUnit(@PathVariable Integer graphId) with Exception {}", e.getMessage());
            return ResponseEntity.noContent().build();
        }
    }
}
