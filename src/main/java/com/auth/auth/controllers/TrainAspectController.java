package com.auth.auth.controllers;

import com.auth.auth.annotations.RequireRole;
import com.auth.auth.models.Guest;
import com.auth.auth.models.IInteractingClient;
import com.auth.auth.services.Train.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/aspect")
@RestController
public class TrainAspectController {
    private final ShiftSchedulingService shiftSchedulingService;
    private final SafetyAlertService safetyAlertService;
    private final MobileTicketScannersService mobileTicketScannersService;
    private final BaggageService baggageService;

    public TrainAspectController(ShiftSchedulingService shiftSchedulingService, SafetyAlertService safetyAlertService, MobileTicketScannersService mobileTicketScannersService, BaggageService baggageService) {
        this.shiftSchedulingService = shiftSchedulingService;
        this.safetyAlertService = safetyAlertService;
        this.mobileTicketScannersService = mobileTicketScannersService;
        this.baggageService = baggageService;
    }

    @RequireRole(value={"TRAINDRIVER"})
    @GetMapping(value = "/assistance")
    public ResponseEntity<String> getAssistanceAspect() {
        IDriverAssistanceSystemsService driverAssistanceSystemsService = new DriverAssistanceSystemsService();
        return new ResponseEntity<>(driverAssistanceSystemsService.getAssistance() + " aspect", HttpStatus.OK);
    }

    @RequireRole(value={"TRAINTICKETPERSON"})
    @GetMapping(value = "/ticketInfo")
    public ResponseEntity<String> getTicketInfoAspect() {
        return new ResponseEntity<>(mobileTicketScannersService.getTicketInfo()+ " aspect", HttpStatus.OK);
    }

    @RequireRole(value={"TRAINTRAVELER"})
    @GetMapping(value = "/baggageInfo")
    public ResponseEntity<String> getBaggageInfoAspect() {
        return new ResponseEntity<>(baggageService.getBaggageInfo()+ " aspect", HttpStatus.OK);
    }

    @RequireRole(value={"TRAINDRIVERl,TRAINTICKETPERSON"})
    @GetMapping(value = "/shifts")
    public ResponseEntity<String> getShiftsAspect() {
        return new ResponseEntity<>(shiftSchedulingService.getShifts()+ " aspect", HttpStatus.OK);
    }

    @RequireRole(value={"TRAINDRIVER,TRAINTICKETPERSON"})
    @PostMapping(value = "/shifts")
    public ResponseEntity<String> setShiftsAspect() {
        return new ResponseEntity<>(shiftSchedulingService.setShifts()+ " aspect", HttpStatus.OK);
    }

    @RequireRole(value={"TRAINDRIVER,TRAINTICKETPERSON,TRAINTRAVELER"})
    @GetMapping(value = "/safetyAlert")
    public ResponseEntity<String> getSafetyAlertAspect() {
        return new ResponseEntity<>(safetyAlertService.getSafetyAlert()+ " aspect", HttpStatus.OK);
    }

    @RequireRole(value = {})
    @GetMapping(value = "/getRegion")
    public ResponseEntity<String> getRegionAspect() {
        IInteractingClient client = new Guest();
        return new ResponseEntity<>(client.getRegion() + " aspect", HttpStatus.OK);
    }
}
