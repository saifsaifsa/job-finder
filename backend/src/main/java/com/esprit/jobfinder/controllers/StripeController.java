package com.esprit.jobfinder.controllers;

import com.esprit.jobfinder.payload.request.CheckoutPayment;
import com.esprit.jobfinder.services.PaymentService;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/payment")
public class StripeController {
    private PaymentService paymentService;

    @Autowired
    public StripeController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/checkout")
    public ResponseEntity<String> createPaymentIntent(@RequestBody CheckoutPayment paymentInfoRequest)
            throws StripeException {

        Session session = paymentService.createSession(paymentInfoRequest);
        String paymentStr = session.toJson();

        return new ResponseEntity<>(paymentStr, HttpStatus.OK);
    }
}
