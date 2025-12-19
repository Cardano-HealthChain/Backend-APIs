package com.cardano.healthchain.cardano.healthchain.user;

import com.cardano.healthchain.cardano.healthchain.clinics.ClinicService;
import com.cardano.healthchain.cardano.healthchain.user.dtos.*;
import com.cardano.healthchain.cardano.healthchain.utils.audit.AuditService;
import com.cardano.healthchain.cardano.healthchain.utils.audit.dtos.AuditDataResponse;
import com.cardano.healthchain.cardano.healthchain.utils.medicalData.MedicalDataService;
import com.cardano.healthchain.cardano.healthchain.utils.medicalData.dtos.MedicalDataResponse;
import com.cardano.healthchain.cardano.healthchain.utils.notifications.NotificationService;
import com.cardano.healthchain.cardano.healthchain.utils.notifications.dtos.NotificationMessages;
import com.cardano.healthchain.cardano.healthchain.utils.notifications.dtos.NotificationResponse;
import com.cardano.healthchain.cardano.healthchain.utils.notifications.dtos.NotificationSeverityLevel;
import com.cardano.healthchain.cardano.healthchain.utils.notifications.dtos.NotificationTypes;
import com.cardano.healthchain.cardano.healthchain.utils.permissions.PermissionService;
import com.cardano.healthchain.cardano.healthchain.utils.permissions.dtos.PermissionDataResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.util.ArrayList;

@RestController
@RequestMapping("api/v1/resident")
public class UserController {
    private final UserService userService;
    private final PermissionService permissionService;
    private final MedicalDataService medicalDataService;
    private final NotificationService notificationService;
    private final ClinicService clinicService;
    private final AuditService auditService;
    public UserController(UserService userService, PermissionService permissionService, MedicalDataService medicalDataService, NotificationService notificationService, ClinicService clinicService, AuditService auditService) {
        this.userService = userService;
        this.permissionService = permissionService;
        this.medicalDataService = medicalDataService;
        this.notificationService = notificationService;
        this.clinicService = clinicService;
        this.auditService = auditService;
    }
    @PostMapping("signup")
    public UserCreateResponse signup(@Valid @RequestBody UserCreateRequest userCreateRequest){
        return userService.createUserWithEmail(userCreateRequest);
    }
    @GetMapping("profile_data")
    public UserDataProfileResponse getUSerById(Principal principal){
        return userService.getUserById(principal.getName());
    }
    @GetMapping("profile_completion")
    public int getProfileCompletionLevel(Principal principal){
        return userService.getProfileCompletionLevel(principal.getName());
    }
    @PostMapping("otp/validate")
    public UserCreateResponse validateUserOtp(@RequestParam String otpcode, @RequestParam String user_email){
        return userService.validateUserOtp(otpcode, user_email);
    }
    @PostMapping("profile/personal_details")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateUserProfileWithPersonalDetails(Principal principal,@Valid @RequestBody UserUpdateProfilePersonalDetailsRequest userUpdateProfilePersonalDetailsRequest){
        userService.updateUserProfileWithPersonalDetails(userUpdateProfilePersonalDetailsRequest,principal.getName());
    }
    @PostMapping("profile/basic_health_information")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateUserProfileWithHealthInformation(Principal principal,@Valid @RequestBody UserUpdateProfileHealthInformationRequest userUpdateProfileHealthInformationRequest){
        userService.updateUserProfileWithHealthInformation(userUpdateProfileHealthInformationRequest,principal.getName());
    }
    @PostMapping("profile/emergency_contact")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateUserProfileWithEmergencyContact(Principal principal,@Valid @RequestBody UserUpdateEmergencyInformationRequest userUpdateEmergencyInformationRequest){
        userService.updateUserProfileWithEmergencyContact(userUpdateEmergencyInformationRequest,principal.getName());
    }
    @PostMapping("profile/location")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateUserProfileWithLocationData(Principal principal,@Valid @RequestBody UserUpdateLocationDataRequest userUpdateLocationDataRequest){
        userService.updateUserProfileWithLocationData(userUpdateLocationDataRequest,principal.getName());
    }
    @GetMapping("/record")
    public MedicalDataResponse getMedicalRecordById(@RequestParam String record_id) {
        return medicalDataService.getMedicalRecordById(record_id);
    }
    @GetMapping("/records")
    public ArrayList<MedicalDataResponse> getMedicalRecordsPerPageForUser(Principal principal, @RequestParam int page) throws NoSuchAlgorithmException, JsonProcessingException {
        return medicalDataService.getMedicalRecordsForUser(principal.getName(),page);
    }
    @GetMapping("/records/filtered")
    public ArrayList<MedicalDataResponse> getMedicalRecordPerPageForUserFiltered(Principal principal, @RequestParam int page, @RequestParam String category) throws NoSuchAlgorithmException, JsonProcessingException {
        return medicalDataService.getMedicalRecordsForUserFiltered(principal.getName(), category, page);
    }
    @GetMapping("/verified_records")
    public ArrayList<MedicalDataResponse> getVerifiedMedicalRecordsPerPageForUser(Principal principal, @RequestParam int page) throws NoSuchAlgorithmException, JsonProcessingException {
        return medicalDataService.getVerifiedMedicalRecordsForUser(principal.getName(),page);
    }
    @GetMapping("/verified_records/filtered")
    public ArrayList<MedicalDataResponse> getVerifiedMedicalRecordPerPageForUserFiltered(Principal principal, @RequestParam int page, @RequestParam String category) throws NoSuchAlgorithmException, JsonProcessingException {
        return medicalDataService.getVerifiedMedicalRecordsForUserFiltered(principal.getName(),category,page);
    }
    @GetMapping("/records/search")
    public ArrayList<MedicalDataResponse> searchForMedicalRecord(Principal principal, @RequestParam String search_keyword, @RequestParam int page) throws NoSuchAlgorithmException, JsonProcessingException {
        return medicalDataService.getMedicalRecordsForUserFiltered(principal.getName(), search_keyword, page);
    }
    @GetMapping("permissions")
    public ArrayList<PermissionDataResponse> getPermittedClinicsForUser(Principal principal, @RequestParam int page){
        return permissionService.getPermittedClinicsForUserById(principal.getName(),page);
    }
    @GetMapping("permission-requests")
    public ArrayList<PermissionDataResponse> getClinicPermissionRequests(Principal principal, @RequestParam int page){
        return permissionService.getRequestedPermissionsForUserById(principal.getName(),page);
    }
    //@Transactional should be in service layer, quick fix later
    @PostMapping("permissions/grant")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void grantPermission(Principal principal, @RequestParam String clinicId, @RequestParam String permissionAccess){
        notificationService.insertUserGrantedPermissionForClinic(clinicId,"PERMISSION_GRANTED", NotificationMessages.USER_GRANTED_PERMISSION_TO_CLINIC.getMessage(), NotificationSeverityLevel.high.name(), NotificationTypes.healthUpdates.name());
        permissionService.userPermitClinic(clinicId, principal.getName());
        clinicService.userAddToClinicsSharedRecordWith(principal.getName(),clinicId);
    }
    //@Transactional should be in service layer, quick fix later
    @PostMapping("permissions/revoke")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void revokePermissionForUser(Principal principal, @RequestParam String clinicId){
        notificationService.insertUserGrantedPermissionForClinic(clinicId,"PERMISSION_REVOKED", NotificationMessages.USER_REVOKED_PERMISSION_TO_CLINIC.getMessage(), NotificationSeverityLevel.high.name(), NotificationTypes.healthUpdates.name());
        permissionService.userRevokeClinic(clinicId,principal.getName());
    }
    @GetMapping("notifications")
    public ArrayList<NotificationResponse> getNotificationsForUser(Principal principal, @RequestParam int page){
        return notificationService.getNotificationForEntity(principal.getName(),page);
    }
    @PostMapping("notifications/read")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void markNotificationAsRead(@RequestParam String notificationId){
        notificationService.markNotificationAsRead(notificationId);
    }
    @PostMapping("dashboard/records/verified_count")
    public int getVerifiedRecordsCount(Principal principal){
        return medicalDataService.getVerifiedRecordsCountForUser(principal.getName());
    }
    @GetMapping("clinics_visited")
    public int getTotalClinicRecordSharedWith(Principal principal, @RequestParam int page){
        return clinicService.getTotalClinicsVisitedByUser(principal.getName());
    }
    @GetMapping("audit")
    public ArrayList<AuditDataResponse> getAuditLogInformation(Principal principal, @RequestParam int page){
        return auditService.getAuditsByActorReference(principal.getName(),page);
    }
    @PostMapping("delete")
    public void deleteAccount(Principal principal){
        userService.deleteAccount(principal.getName());
    }
}