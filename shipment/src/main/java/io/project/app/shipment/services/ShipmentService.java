package io.project.app.shipment.services;

import io.project.app.shipment.repositories.ShipmentRepository;
import io.project.app.domain.Shipment;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 *
 * @author root
 */
@Service
@Component
@Slf4j
public class ShipmentService {

    @Autowired
    private ShipmentRepository shipmentRepository;

    public Shipment createShipment(Shipment shipment) {
        log.info("ADD SHIPMENT SERVICE");
        shipment.setRecordDate(new Date());
        return shipmentRepository.save(shipment);
    }

    public Shipment updateShipment(Shipment shipment) {
        log.info("UPDATE SHIPMENT SERVICE");
        if (shipment.getId() != null) {
            log.info("UPDATE SHIPMENT SERVICE, START");
            shipment = shipmentRepository.save(shipment);
        }

        return shipment;

    }

    public Optional<Shipment> findShipmentById(String id) {
        return shipmentRepository.findById(id);

    }

    public Optional<Shipment> findShipmentByIdAndUserId(String id, String userId) {
        return shipmentRepository.findByIdAndUserId(id, userId);
    }

    public Optional<List<Shipment>> findAllByUserId(String userId) {
        return shipmentRepository.findAllByUserIdOrderByRecordDateDesc(userId);
    }

}
