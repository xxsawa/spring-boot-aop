package com.auth.auth.services.Train;

import org.springframework.stereotype.Service;

@Service
public class ShiftSchedulingService {
    public String getShifts(){
        return "You work monday, friday, sunday";
    }

    public String setShifts(){
        return "Shift set";
    }
}
