package com.cardano.healthchain.cardano.healthchain.clinics;

import org.springframework.stereotype.Service;

@Service
public class ClinicService {
    private final ClinicRepositoryI clinicRepository;

    public ClinicService(ClinicRepositoryI clinicRepository) {
        this.clinicRepository = clinicRepository;
    }
    public int getTotalClinicsVisitedByUser(String user_email) {
        return clinicRepository.getTotalClinicsVisitedByUser(user_email);
    }
}
