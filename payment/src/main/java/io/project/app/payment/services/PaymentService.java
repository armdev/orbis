package io.project.app.payment.services;

import io.project.app.domain.PaymentAccount;
import io.project.app.payment.repositories.PaymentRepository;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 *
 * @author root
 */
@Service
@Component
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    public Optional<PaymentAccount> findUserPayment(String userId) {
        return paymentRepository.findByUserId(userId);
    }

    public PaymentAccount createPayment(PaymentAccount paymentAccount) {
        return paymentRepository.save(paymentAccount);
    }

    public PaymentAccount updatePayment(PaymentAccount paymentAccount) {
        return paymentRepository.save(paymentAccount);
    }

}
