package io.project.app.shipment.resources;

import io.project.app.shipment.services.ShipmentService;

import io.micrometer.core.annotation.Timed;
import io.project.app.domain.Shipment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author armena
 */
@RestController
@RequestMapping("/api/v2/shipments")
@Slf4j
public class ShipmentController {

    @Autowired
    private ShipmentService shipmentService;

    /**
     *
     * @param id
     * @return
     */
    @GetMapping(path = "/shipment/id", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @CrossOrigin
    @Timed
    public ResponseEntity<?> get(@RequestParam(name = "id", required = true) String id) {
        log.info("Fins shipment by id");
        return ResponseEntity.status(HttpStatus.OK).body(shipmentService.findShipmentById(id).get());
    }

    /**
     *
     * @param id
     * @param userId
     * @return
     */
    @GetMapping(path = "/shipment/id/userid", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @CrossOrigin
    @Timed
    public ResponseEntity<?> load(@RequestParam(name = "id", required = true) String id, @RequestParam(name = "userId", required = true) String userId) {
        return ResponseEntity.status(HttpStatus.OK).body(shipmentService.findShipmentByIdAndUserId(id, userId).get());
    }

    /**
     *
     * @param userId
     * @return
     */
    @GetMapping(path = "/shipment/find/list/user/id", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @CrossOrigin
    @Timed
    public ResponseEntity<?> find(@RequestParam(required = true, name = "userId") String userId) {
        log.info("LOAD USER SHIPMENT, with user id");
        return ResponseEntity.status(HttpStatus.OK).body(shipmentService.findAllByUserId(userId).get());
    }

    /**
     *
     * @param shipment
     * @return
     */
    @PostMapping(path = "/shipment", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @CrossOrigin
    @Timed
    public ResponseEntity<?> post(@RequestBody Shipment shipment) {
        log.info("ADD NEW SHIPMENT STARTED");
        return ResponseEntity.status(HttpStatus.OK).body(shipmentService.createShipment(shipment));
    }

    /**
     *
     * @param shipment
     * @return
     */
    @PutMapping(path = "/shipment/update/id", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @CrossOrigin
    @Timed
    public ResponseEntity<?> put(@RequestBody Shipment shipment) {
        log.info("Starting update shipment " + shipment.getId());
        return ResponseEntity.status(HttpStatus.OK).body(shipmentService.updateShipment(shipment));
    }

}
