package io.project.app.repositories;

import io.project.app.domain.CareerAccount;
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
public interface CareerRepository extends MongoRepository<CareerAccount, String> {

    Optional<CareerAccount> findByUserId(String id);

}
