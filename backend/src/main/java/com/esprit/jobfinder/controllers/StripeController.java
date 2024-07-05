package com.esprit.jobfinder.controllers;

import java.util.HashMap;
import java.util.Map;

import com.esprit.jobfinder.payload.request.CheckoutPayment;
import com.esprit.jobfinder.services.PaymentService;
import com.stripe.model.PaymentIntent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;

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
//        Map<String, String> responseData = new HashMap<>();
//        responseData.put("id", session.getId());
        String paymentStr = session.toJson();

        return new ResponseEntity<>(paymentStr, HttpStatus.OK);
    }
//    @PostMapping()
//    /**
//     * Payment with Stripe checkout page
//     *
//     * @throws StripeException
//     */
//    public ResponseEntity<String> paymentWithCheckoutPage(@RequestBody CheckoutPayment payment) throws StripeException {
//        try {
//            System.out.println("Received payment request: " + payment);
//            Map<String, Object> params = new HashMap<>();
//            params.put("amount", payment.getAmount());
//            params.put("currency", payment.getCurrency());
//            params.put("quantity", payment.getQuantity());
//            params.put("payment_method_types", new String[]{"card"});
//            PaymentIntent paymentIntent = PaymentIntent.create(params);
//            Map<String, String> responseData = new HashMap<>();
//            responseData.put("clientSecret", paymentIntent.getClientSecret());
//        return ResponseEntity.ok().body(responseData.toString());
//        } catch (StripeException e) {
//            return ResponseEntity.status(500).body(e.getMessage());
//        }
//    }
}
