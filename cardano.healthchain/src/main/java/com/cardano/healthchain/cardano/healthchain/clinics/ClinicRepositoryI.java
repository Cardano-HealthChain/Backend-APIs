package com.cardano.healthchain.cardano.healthchain.clinics;

public interface ClinicRepositoryI {
    int getTotalClinicsVisitedByUser(String user_email);
}
