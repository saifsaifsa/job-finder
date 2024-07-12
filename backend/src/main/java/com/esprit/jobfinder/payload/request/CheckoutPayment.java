package com.esprit.jobfinder.payload.request;

import lombok.Data;

@Data
public class CheckoutPayment {
    private String name;
    private long amount;
    private String currency;
    private String successUrl;
    private String cancelUrl;
}