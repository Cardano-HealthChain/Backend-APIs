package com.cardano.healthchain.cardano.healthchain.vaccination;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/vc")
public class VaccinationCredentialController {
    private final VaccinationService vaccinationService;
    public VaccinationCredentialController(VaccinationService vaccinationService) {
        this.vaccinationService = vaccinationService;
    }
    @PostMapping("/issue-request")
    public VaccinationDTOResponse initiateBlockChainWriteTransactionWithNewVcData(@Valid @RequestBody VaccinationDTOResponse vaccinationDTOResponse){
        return null;
    }
}
