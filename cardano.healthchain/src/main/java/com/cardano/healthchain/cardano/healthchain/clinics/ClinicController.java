package com.cardano.healthchain.cardano.healthchain.clinics;

import com.cardano.healthchain.cardano.healthchain.clinics.doctors.DoctorService;
import com.cardano.healthchain.cardano.healthchain.clinics.doctors.dtos.DoctorDataResponse;
import com.cardano.healthchain.cardano.healthchain.clinics.dtos.*;
import com.cardano.healthchain.cardano.healthchain.utils.audit.AuditService;
import com.cardano.healthchain.cardano.healthchain.utils.medicalData.MedicalDataService;
import com.cardano.healthchain.cardano.healthchain.utils.medicalData.dtos.MedicalDataResponse;
import com.cardano.healthchain.cardano.healthchain.utils.notifications.NotificationService;
import com.cardano.healthchain.cardano.healthchain.utils.notifications.dtos.NotificationResponse;
import com.cardano.healthchain.cardano.healthchain.utils.permissions.PermissionService;
import com.cardano.healthchain.cardano.healthchain.utils.permissions.dtos.PermissionDataResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.util.ArrayList;

@RestController
@RequestMapping("api/v1/clinic")
public class ClinicController {
    private final ClinicService clinicService;
    private final MedicalDataService medicalDataService;
    private final PermissionService permissionService;
    private final NotificationService notificationService;
    private final AuditService auditService;
    private final DoctorService doctorService;
    public ClinicController(ClinicService clinicService, MedicalDataService medicalDataService, PermissionService permissionService, NotificationService notificationService, AuditService auditService, DoctorService doctorService) {
        this.clinicService = clinicService;
        this.medicalDataService = medicalDataService;
        this.permissionService = permissionService;
        this.notificationService = notificationService;
        this.auditService = auditService;
        this.doctorService = doctorService;
    }
    @PostMapping("signup")
    public ClinicCreateResponse signup(@RequestBody ClinicCreateRequest clinicCreateRequest){
        return clinicService.signUp(clinicCreateRequest);
    }
    @PostMapping("signup/admin-details")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateAdminDetails(@Valid @RequestBody ClinicAdminCreateRequest clinicAdminCreateRequest, Principal principal){
        clinicService.updateAdminDetails(clinicAdminCreateRequest, principal.getName());
    }
    @GetMapping("clinic-details")
    public ClinicDataResponse getClinicInformation(Principal principal){
        return clinicService.getClinicInformation(principal.getName());
    }
    @GetMapping("doctors")
    public ArrayList<DoctorDataResponse> getDoctorsUnderClinic(Principal principal, @RequestParam int page){
        return clinicService.getDoctorsUnderClinic(principal.getName(),page);
    }
    @GetMapping("doctors-count")
    public int getDoctorsUnderClinicCount(Principal principal, @RequestParam int page){
        return clinicService.getTotalDoctorsUnderClinic(principal.getName());
    }
    @PutMapping("region")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateClinicRegion(Principal principal,@RequestParam String new_region){
        clinicService.updateClinicRegion(principal.getName(), new_region);
    }
    @GetMapping("permissions")
    public ArrayList<PermissionDataResponse> viewRequestedPermissionsByClinicUsingClinicID(Principal principal, int page){
        return permissionService.getClinicPermissionRequestsById(principal.getName(),page);
    }
    @GetMapping("user-records")
    public ArrayList<MedicalDataResponse> viewUserRecordsById(Principal principal,@RequestParam String userId, @RequestParam int page) throws NoSuchAlgorithmException, JsonProcessingException {
        permissionService.checkIfClinicHasReadPermissionForUser(userId,principal.getName());
        return medicalDataService.getMedicalRecordsForUser(userId,page);
    }
    @GetMapping("notifications")
    public ArrayList<NotificationResponse> getClinicNotifications(Principal principal, @RequestParam int page){
        return notificationService.getNotificationForEntity(principal.getName(),page);
    }
    @PostMapping("create-doctor")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void createDoctor(Principal principal, @RequestBody DoctorCreateRequest doctorCreateRequest){
        doctorService.createDoctor(principal.getName(),doctorCreateRequest);
    }
}