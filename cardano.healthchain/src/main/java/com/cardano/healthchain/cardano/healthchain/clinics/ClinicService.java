package com.cardano.healthchain.cardano.healthchain.clinics;

import com.cardano.healthchain.cardano.healthchain.clinics.doctors.DoctorRepositoryI;
import com.cardano.healthchain.cardano.healthchain.clinics.doctors.dtos.DoctorDataResponse;
import com.cardano.healthchain.cardano.healthchain.clinics.dtos.ClinicAdminCreateRequest;
import com.cardano.healthchain.cardano.healthchain.clinics.dtos.ClinicCreateRequest;
import com.cardano.healthchain.cardano.healthchain.clinics.dtos.ClinicCreateResponse;
import com.cardano.healthchain.cardano.healthchain.clinics.dtos.ClinicDataResponse;
import com.cardano.healthchain.cardano.healthchain.user.UserRepositoryI;
import com.cardano.healthchain.cardano.healthchain.utils.Healthchain_Roles_Enum;
import com.cardano.healthchain.cardano.healthchain.security.JwtService;
import com.cardano.healthchain.cardano.healthchain.utils.blockchain.BlockChainServiceI;
import com.cardano.healthchain.cardano.healthchain.utils.notifications.NotificationService;
import com.cardano.healthchain.cardano.healthchain.utils.permissions.PermissionService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

@Service
public class ClinicService {
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepositoryI userRepository;
    private final ClinicRepositoryI clinicRepository;
    private final DoctorRepositoryI doctorRepository;
    private final NotificationService notificationService;
    private final PermissionService permissionService;
    private final BlockChainServiceI cardanoBlockChainServiceImpl;
    public ClinicService(ClinicRepositoryI clinicRepository, JwtService jwtService, PasswordEncoder passwordEncoder, UserRepositoryI userRepository, DoctorRepositoryI doctorRepository, NotificationService notificationService, PermissionService permissionService, BlockChainServiceI cardanoBlockChainServiceImpl) {
        this.clinicRepository = clinicRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.doctorRepository = doctorRepository;
        this.notificationService = notificationService;
        this.permissionService = permissionService;
        this.cardanoBlockChainServiceImpl = cardanoBlockChainServiceImpl;
    }
    public int getTotalClinicsVisitedByUser(String user_id) {
        return clinicRepository.getTotalClinicsVisitedByUser(user_id);
    }
    public ClinicCreateResponse signUp(ClinicCreateRequest clinicCreateRequest) {
        UUID clinicId = clinicRepository.createClinic(clinicCreateRequest);
        return new ClinicCreateResponse(
                Healthchain_Roles_Enum.CLINIC.name(),
                jwtService.generateTokenWithEntityId(
                        String.valueOf(clinicId),
                        Healthchain_Roles_Enum.CLINIC.name(),
                        Map.of()
                )
        );
    }
    public void updateClinicRegion(String clinicId, String newRegion) {
        clinicRepository.updateClinicRegion(clinicId,newRegion);
    }
    public void updateAdminDetails(ClinicAdminCreateRequest clinicAdminCreateRequest, String clinicId) {
        clinicAdminCreateRequest.setPassword(passwordEncoder.encode(clinicAdminCreateRequest.getPassword()));
        clinicRepository.updateAdminDetails(clinicAdminCreateRequest, clinicId);
    }
    public ClinicDataResponse getClinicInformation(String clinicId) {
        return clinicRepository.getClinicById(clinicId);
    }
    public int getTotalDoctorsUnderClinic(String clinicId){
        return doctorRepository.getDoctorsCountUnderClinic(clinicId);
    }
    public void userAddToClinicsSharedRecordWith(String userId, String clinicId) {
        userRepository.userAddToClinicsSharedRecordWith(userId, clinicId);
    }

    public ArrayList<DoctorDataResponse> getDoctorsUnderClinic(String clinicId, int page) {
        return doctorRepository.getDoctorsUnderClinic(clinicId,page);
    }
}