package com.cardano.healthchain.cardano.healthchain.data;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/vc/data")
public class DataController {
    private final DataService dataService;
    public DataController(DataService dataService) {
        this.dataService = dataService;
    }
    @PostMapping("/upload")
    public DataDTOResponse uploadAndEncryptVaccinationData(@Valid @RequestBody DataDTORequest dataDTORequest){
      return null;
    };
}