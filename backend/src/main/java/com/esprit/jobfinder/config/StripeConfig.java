package com.esprit.jobfinder.config;

import com.stripe.Stripe;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StripeConfig {
    @PostConstruct
    public void init() {
        Stripe.apiKey = "sk_test_51PWiqcP0nEG1Y5KaURXgxMGkLUbn7qWzizYjCpyPRwhM4OoqPRYtmhcVrHv36UuypAfVPcxK62FpLbGKU0qxIPRL00NxoJpF83";
    }
}
