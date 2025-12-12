package com.cardano.healthchain.cardano.healthchain.clinics;

import com.cardano.healthchain.cardano.healthchain.clinics.dtos.ClinicModel;

public interface ClinicRepositoryI {
    int getTotalClinicsVisitedByUser(String user_id);
    ClinicModel getClinicById(String clinic_id);
}
