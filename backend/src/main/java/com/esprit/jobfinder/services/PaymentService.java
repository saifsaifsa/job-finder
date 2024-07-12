package com.esprit.jobfinder.services;

import com.esprit.jobfinder.payload.request.CheckoutPayment;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PaymentService {
    @Autowired
    public PaymentService(@Value("${STRIPE_PUBLIC_KEY}") String secretKey) {
        Stripe.apiKey = secretKey;
    }

    public Session createSession(CheckoutPayment payment) throws StripeException {
        SessionCreateParams params =
                SessionCreateParams.builder()
                        .setMode(SessionCreateParams.Mode.PAYMENT)
                        .setSuccessUrl(payment.getSuccessUrl())
                        .setCancelUrl(payment.getCancelUrl())
                        .addLineItem(
                                SessionCreateParams.LineItem.builder()
                                        .setQuantity(1L)
                                        .setPriceData(
                                                SessionCreateParams.LineItem.PriceData.builder()
                                                        .setCurrency(payment.getCurrency())
                                                        .setUnitAmount(payment.getAmount() * 100)
                                                        .setProductData(
                                                                SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                                        .setName(payment.getName())
                                                                        .build())
                                                        .build())
                                        .build())
                        .build();
        return Session.create(params);
    }

}
