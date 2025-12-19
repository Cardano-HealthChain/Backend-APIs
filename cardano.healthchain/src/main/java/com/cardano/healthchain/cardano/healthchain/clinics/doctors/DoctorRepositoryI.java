package com.cardano.healthchain.cardano.healthchain.clinics.doctors;

import com.cardano.healthchain.cardano.healthchain.clinics.doctors.dtos.DoctorDataResponse;
import com.cardano.healthchain.cardano.healthchain.clinics.dtos.DoctorCreateRequest;

public interface DoctorRepositoryI {
    void createDoctor(String clinicId, DoctorCreateRequest doctorCreateRequest);
    DoctorDataResponse getDoctorById(String doctorId);
    DoctorDataResponse getDoctorByEmail(String doctorEmail);
    int getDoctorsCountUnderClinic(String clinicId);
}