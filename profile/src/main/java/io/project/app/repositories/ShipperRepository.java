package io.project.app.repositories;

import io.project.app.domain.ShipperAccount;
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
public interface ShipperRepository extends MongoRepository<ShipperAccount, String> {

    Optional<ShipperAccount> findByUserId(String id);

}
