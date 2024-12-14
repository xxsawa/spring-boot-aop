package com.auth.auth.controllers;

import com.auth.auth.services.Train.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/train")
@RestController
public class TrainController {
//    DriverAssistanceSystemsService
//    ShiftSchedulingService
//    SafetyAlertService
//    MobileTicketScannersService
//    BaggageService

    private final ShiftSchedulingService shiftSchedulingService;
    private final SafetyAlertService safetyAlertService;
    private final MobileTicketScannersService mobileTicketScannersService;
    private final BaggageService baggageService;

    public TrainController(ShiftSchedulingService shiftSchedulingService, SafetyAlertService safetyAlertService, MobileTicketScannersService mobileTicketScannersService, BaggageService baggageService) {
        this.shiftSchedulingService = shiftSchedulingService;
        this.safetyAlertService = safetyAlertService;
        this.mobileTicketScannersService = mobileTicketScannersService;
        this.baggageService = baggageService;
    }

    @PreAuthorize("hasRole('TRAINDRIVER')")
    @GetMapping(value = "/assistance")
    public String getAssistance() {
        IDriverAssistanceSystemsService driverAssistanceSystemsService = new DriverAssistanceSystemsService();
        return driverAssistanceSystemsService.getAssistance();
    }

    @PreAuthorize("hasRole('TRAINTICKETPERSON')")
    @GetMapping(value = "/ticketInfo")
    public String getTicketInfo() {
        return mobileTicketScannersService.getTicketInfo();
    }

    @PreAuthorize("hasRole('TRAINTRAVELER')")
    @GetMapping(value = "/baggageInfo")
    public String getBaggageInfo() {
        return baggageService.getBaggageInfo();
    }
    @PreAuthorize("hasRole('TRAINDRIVER') or hasRole('TRAINTICKETPERSON')")
    @GetMapping(value = "/shifts")
    public String getShifts() {
        return shiftSchedulingService.getShifts();
    }

    @PreAuthorize("hasRole('TRAINDRIVER') or hasRole('TRAINTICKETPERSON')")
    @PostMapping(value = "/shifts")
    public String setShifts() {
        return shiftSchedulingService.setShifts();
    }

    @PreAuthorize("hasRole('TRAINDRIVER') or hasRole('TRAINTICKETPERSON') or hasRole('TRAINTRAVELER')")
    @GetMapping(value = "/safetyAlert")
    public String getSafetyAlert() {
        return safetyAlertService.getSafetyAlert();
    }
}
