package io.project.app.shipment.repositories;


import io.project.app.domain.Shipment;
import java.util.List;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 *
 * @author armena
 */
@Component
@Repository
public interface ShipmentRepository extends MongoRepository<Shipment, String> {

    Optional<Shipment> findByIdAndUserId(String id, String userId);

    Optional<List<Shipment>> findAllByUserIdOrderByRecordDateDesc(String userId);

    Optional<List<Shipment>> findAllByUserId(String userId);

}
