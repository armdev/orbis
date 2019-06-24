package io.project.app.payment.resources;

import io.project.app.payment.services.PaymentService;

import io.micrometer.core.annotation.Timed;
import io.project.app.domain.PaymentAccount;
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
@RequestMapping("/api/v2/payments")
public class PaymentResource {

    @Autowired
    private PaymentService paymentService;

    /**
     *
     * @param userId
     * @return
     */
    @GetMapping(path = "/payment/userid", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @CrossOrigin
    @Timed
    public ResponseEntity<?> get(@RequestParam String userId) {
        return ResponseEntity.status(HttpStatus.OK).body(paymentService.findUserPayment(userId));
    }

    /**
     *
     * @param paymentAccount
     * @return
     */
    @PostMapping(path = "/payment", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @CrossOrigin
    @Timed
    public ResponseEntity<?> post(@RequestBody PaymentAccount paymentAccount) {
        return ResponseEntity.status(HttpStatus.OK).body(paymentService.createPayment(paymentAccount));
    }

    /**
     *
     * @param paymentAccount
     * @return
     */
    @PutMapping(path = "/payment", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @CrossOrigin
    @Timed
    public ResponseEntity<?> put(@RequestBody PaymentAccount paymentAccount) {
        return ResponseEntity.status(HttpStatus.OK).body(paymentService.updatePayment(paymentAccount));
    }

}
