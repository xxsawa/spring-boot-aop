package com.auth.auth.services.Train;

import org.springframework.stereotype.Component;

@Component
public class BaggageService {

    public String getBaggageInfo() {
        return "Your baggage is reserved";
    }

    public String anyDifferentFunction() {
        return "Your anyDifferentFunction is reserved";
    }
}
