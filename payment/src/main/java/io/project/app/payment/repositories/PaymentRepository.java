package io.project.app.payment.repositories;


import io.project.app.domain.PaymentAccount;
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
public interface PaymentRepository extends MongoRepository<PaymentAccount, String> {

    Optional<PaymentAccount> findByUserId(String userId);

}
