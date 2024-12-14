package com.auth.auth.services.Train;

import org.springframework.stereotype.Service;

@Service
public class SafetyAlertService {
    public String getSafetyAlert(){
        return "Get out of train!";
    }
}
