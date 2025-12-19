package com.cardano.healthchain.cardano.healthchain.clinics.doctors;

import com.cardano.healthchain.cardano.healthchain.clinics.doctors.dtos.DoctorDataResponse;
import com.cardano.healthchain.cardano.healthchain.clinics.dtos.DoctorCreateRequest;
import com.cardano.healthchain.cardano.healthchain.utils.medicalData.MedicalDataService;
import com.cardano.healthchain.cardano.healthchain.utils.medicalData.dtos.MedicalDataResponse;
import com.cardano.healthchain.cardano.healthchain.utils.medicalData.dtos.MedicalDataUploadRequest;
import com.cardano.healthchain.cardano.healthchain.utils.notifications.NotificationService;
import com.cardano.healthchain.cardano.healthchain.utils.permissions.PermissionService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
@Service
public class DoctorService {
    private final MedicalDataService medicalDataService;
    private final NotificationService notificationService;
    private final PermissionService permissionService;
    private final DoctorRepositoryI doctorRepository;
    private final PasswordEncoder passswordEncoder;
    public DoctorService(MedicalDataService medicalDataService, NotificationService notificationService, PermissionService permissionService, DoctorRepositoryI doctorRepository, PasswordEncoder passswordEncoder) {
        this.medicalDataService = medicalDataService;
        this.notificationService = notificationService;
        this.permissionService = permissionService;
        this.doctorRepository = doctorRepository;
        this.passswordEncoder = passswordEncoder;
    }
    public void createDoctor(String clinicId, DoctorCreateRequest doctorCreateRequest) {
        doctorCreateRequest.setDoctor_password(passswordEncoder.encode(doctorCreateRequest.getDoctor_password()));
        doctorRepository.createDoctor(clinicId,doctorCreateRequest);
    }
    public void doctorUploadRecordForUser(String doctorId, String userId, MedicalDataUploadRequest medicalDataUploadRequest){
        medicalDataService.uploadMedicalDataForUser(medicalDataUploadRequest,doctorId,userId);
    }
    @Transactional
    public ArrayList<MedicalDataResponse> doctorViewRecordsForUser(String doctorId, String userId, int page){
        DoctorDataResponse doctorById = doctorRepository.getDoctorById(doctorId);
        permissionService.checkIfClinicHasReadPermissionForUser(userId,doctorById.getClinic_id().toString());
        return medicalDataService.clinicGetMedicalRecordsForUser(userId, page);
    }
    @Transactional
    public ArrayList<MedicalDataResponse> doctorViewRecordsForUserFiltered(String doctorId, String userId, String filterTerm, int page){
        DoctorDataResponse doctorById = doctorRepository.getDoctorById(doctorId);
        permissionService.checkIfClinicHasReadPermissionForUser(userId,doctorById.getClinic_id().toString());
        return medicalDataService.getMedicalRecordsForUserFiltered(userId,filterTerm,page);
    }
}