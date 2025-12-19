package com.cardano.healthchain.cardano.healthchain.clinics;

import com.cardano.healthchain.cardano.healthchain.clinics.dtos.ClinicAdminCreateRequest;
import com.cardano.healthchain.cardano.healthchain.clinics.dtos.ClinicCreateRequest;
import com.cardano.healthchain.cardano.healthchain.clinics.dtos.ClinicDataResponse;

import java.util.UUID;

public interface ClinicRepositoryI {
    int getTotalClinicsVisitedByUser(String user_id);
    UUID createClinic(ClinicCreateRequest clinicCreateRequest);
    void updateClinicRegion(String clinicId, String newRegion);
    void updateAdminDetails(ClinicAdminCreateRequest clinicAdminCreateRequest);
    ClinicDataResponse getClinicById(String clinic_id);
    ClinicDataResponse getClinicByEmail(String clinic_email);
}