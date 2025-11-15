package com.cardano.healthchain.cardano.healthchain.alert;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/alerts")
public class AlertController {
    private final AlertService alertService;
    public AlertController(AlertService alertService) {
        this.alertService = alertService;
    }
    @PostMapping("send")
    public AlertDTOResponse sendAlertsToCategory(@Valid @RequestBody AlertDTORequest alertDTORequest){
        return null;
    }
}