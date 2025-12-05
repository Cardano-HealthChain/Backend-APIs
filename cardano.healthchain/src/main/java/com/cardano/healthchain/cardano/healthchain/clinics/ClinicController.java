package com.cardano.healthchain.cardano.healthchain.clinics;

import com.cardano.healthchain.cardano.healthchain.clinics.dtos.ClinicCreateRequest;
import com.cardano.healthchain.cardano.healthchain.clinics.dtos.ClinicCreateResponse;
import com.cardano.healthchain.cardano.healthchain.clinics.dtos.ClinicModel;
import com.cardano.healthchain.cardano.healthchain.utils.medicalData.MedicalDataService;
import com.cardano.healthchain.cardano.healthchain.utils.medicalData.dtos.MedicalDataResponse;
import com.cardano.healthchain.cardano.healthchain.utils.medicalData.dtos.MedicalDataUploadRequest;
import com.cardano.healthchain.cardano.healthchain.utils.medicalData.dtos.MedicalDataUploadResponse;
import com.cardano.healthchain.cardano.healthchain.utils.notifications.NotificationService;
import com.cardano.healthchain.cardano.healthchain.utils.notifications.dtos.NotificationResponse;
import com.cardano.healthchain.cardano.healthchain.utils.permissions.PermissionService;
import com.cardano.healthchain.cardano.healthchain.utils.permissions.dtos.PermissionStatusResponse;
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
    public ClinicController(ClinicService clinicService, MedicalDataService medicalDataService, PermissionService permissionService, NotificationService notificationService) {
        this.clinicService = clinicService;
        this.medicalDataService = medicalDataService;
        this.permissionService = permissionService;
        this.notificationService = notificationService;
    }
    @PostMapping("signup")
    public ClinicCreateResponse signup(@Valid @RequestBody ClinicCreateRequest clinicCreateRequest){
        return clinicService.signUp(clinicCreateRequest);
    }
    @GetMapping("profile")
    public ClinicModel getClinicProfile(Principal principal){
        return clinicService.getClinicProfile(principal.getName());
    }
    @PutMapping("region")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateClinicRegion(Principal principal,@RequestParam String new_region){
        clinicService.updateClinicRegion(principal.getName(), new_region);
    }
    @PostMapping("clinic/permissions/request")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void requestRecordPermissionFromUser(Principal principal,@RequestParam String user_email, @RequestParam String access_type){
        clinicService.requestRecordPermissionFromUser(principal.getName(),user_email, access_type);
    }
    @GetMapping("clinic/permissions")
    public ArrayList<PermissionStatusResponse> viewPermissionStatus(Principal principal){
        return clinicService.viewPermissionStatusForClinic(principal.getName());
    }
    @PostMapping("clinic/records/upload")
    public MedicalDataUploadResponse uploadMedicalDataForUser(@Valid @RequestBody MedicalDataUploadRequest medicalDataUploadRequest){
        return clinicService.uploadMedicalDataForUser(medicalDataUploadRequest);
    }
    @GetMapping("clinic/records/user")
    public ArrayList<MedicalDataResponse> viewAllUserRecords(Principal principal,@RequestParam String user_email, @RequestParam int page) throws NoSuchAlgorithmException, JsonProcessingException {
        permissionService.checkClinicAccessToUserRecordStatus(principal.getName());
        return medicalDataService.verifyAndGetMedicalRecordsForUser(1,user_email);
    }
    @GetMapping("clinic/notifications")
    public ArrayList<NotificationResponse> getClinicNotifications(Principal principal){
        return notificationService.getNotificationsForClinic(principal.getName());
    }
}