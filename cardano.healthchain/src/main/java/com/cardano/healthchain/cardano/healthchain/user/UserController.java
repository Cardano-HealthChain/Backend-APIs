package com.cardano.healthchain.cardano.healthchain.user;

import com.cardano.healthchain.cardano.healthchain.clinics.ClinicService;
import com.cardano.healthchain.cardano.healthchain.user.dtos.*;
import com.cardano.healthchain.cardano.healthchain.utils.audit.AuditService;
import com.cardano.healthchain.cardano.healthchain.utils.audit.dtos.AuditModel;
import com.cardano.healthchain.cardano.healthchain.utils.medicalData.MedicalDataService;
import com.cardano.healthchain.cardano.healthchain.utils.medicalData.dtos.MedicalDataResponse;
import com.cardano.healthchain.cardano.healthchain.utils.notifications.NotificationService;
import com.cardano.healthchain.cardano.healthchain.utils.notifications.dtos.NotificationResponse;
import com.cardano.healthchain.cardano.healthchain.utils.permissions.PermissionService;
import com.cardano.healthchain.cardano.healthchain.utils.permissions.dtos.PermissionResponse;
import com.cardano.healthchain.cardano.healthchain.utils.web3Wallet.WalletService;
import com.cardano.healthchain.cardano.healthchain.utils.web3Wallet.dtos.WalletConnectionRequest;
import com.cardano.healthchain.cardano.healthchain.utils.web3Wallet.dtos.WalletConnectionStatus;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.util.ArrayList;

@RestController
@RequestMapping("api/v1/resident")
public class UserController {
    private final UserService userService;
    private final PermissionService permissionService;
    private final WalletService walletService;
    private final MedicalDataService medicalDataService;
    private final NotificationService notificationService;
    private final ClinicService clinicService;
    private final AuditService auditService;
    public UserController(UserService userService, PermissionService permissionService, WalletService walletService, MedicalDataService medicalDataService, NotificationService notificationService, ClinicService clinicService, AuditService auditService) {
        this.userService = userService;
        this.permissionService = permissionService;
        this.walletService = walletService;
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
    public UserModel getUSerById(Principal principal){
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
    public void updateUserProfileWithPersonalDetails(Principal principal,@Valid @RequestBody UserUpdateProfilePersonalDetails userUpdateProfilePersonalDetails){
        userService.updateUserProfileWithPersonalDetails(userUpdateProfilePersonalDetails,principal.getName());
    }
    @PostMapping("profile/basic_health_information")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateUserProfileWithHealthInformation(Principal principal,@Valid @RequestBody UserUpdateProfileHealthInformation userUpdateProfileHealthInformation){
        userService.updateUserProfileWithHealthInformation(userUpdateProfileHealthInformation,principal.getName());
    }
    @PostMapping("profile/emergency_contact")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateUserProfileWithEmergencyContact(Principal principal,@Valid @RequestBody UserUpdateEmergencyInformation userUpdateEmergencyInformation){
        userService.updateUserProfileWithEmergencyContact(userUpdateEmergencyInformation,principal.getName());
    }
    @PostMapping("profile/location")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateUserProfileWithLocationData(Principal principal,@Valid @RequestBody UserUpdateLocationData userUpdateLocationData){
        userService.updateUserProfileWithLocationData(userUpdateLocationData,principal.getName());
    }
    @PostMapping("wallet/connect")
    public WalletConnectionStatus connectUserWallet(Principal principal, @RequestBody WalletConnectionRequest walletConnectionRequest){
        return walletService.connectWallet(walletConnectionRequest, principal.getName());
    }
    @GetMapping("/record")
    public MedicalDataResponse getMedicalRecordById(Principal principal,@RequestParam String record_id) {
        return medicalDataService.getMedicalRecordById(record_id, principal.getName());
    }
    @GetMapping("/records")
    public ArrayList<MedicalDataResponse> getMedicalRecordsPerPageForUser(Principal principal, @RequestParam int page) throws NoSuchAlgorithmException, JsonProcessingException {
        return medicalDataService.getMedicalRecordsForUser(page, principal.getName());
    }
    @GetMapping("/records/filtered")
    public ArrayList<MedicalDataResponse> getMedicalRecordPerPageForUserFiltered(Principal principal, @RequestParam int page, @RequestParam String category) throws NoSuchAlgorithmException, JsonProcessingException {
        return medicalDataService.getMedicalRecordsForUserFiltered(page,principal.getName(), category);
    }
    @GetMapping("/verified_records")
    public ArrayList<MedicalDataResponse> getVerifiedMedicalRecordsPerPageForUser(Principal principal, @RequestParam int page) throws NoSuchAlgorithmException, JsonProcessingException {
        return medicalDataService.verifyAndGetMedicalRecordsForUser(page, principal.getName());
    }
    @GetMapping("/verified_records/filtered")
    public ArrayList<MedicalDataResponse> getVerifiedMedicalRecordPerPageForUserFiltered(Principal principal, @RequestParam int page, @RequestParam String category) throws NoSuchAlgorithmException, JsonProcessingException {
        return medicalDataService.verifyAndGetMedicalRecordsForUserFiltered(page,principal.getName(), category);
    }
    @GetMapping("/records/search")
    public ArrayList<MedicalDataResponse> searchForMedicalRecord(Principal principal, @RequestParam String search_keyword, @RequestParam int page) throws NoSuchAlgorithmException, JsonProcessingException {
        return medicalDataService.verifyAndGetMedicalRecordsForUserFiltered(page,principal.getName(),search_keyword);
    }
    @GetMapping("permissions")
    public ArrayList<PermissionResponse> getPermittedClinicsForUser(Principal principal, @RequestParam int page){
        return permissionService.getPermittedClinicsForUser(principal.getName(),page);
    }
    @GetMapping("permission-requests")
    public ArrayList<PermissionResponse> getClinicPermissionRequests(Principal principal, @RequestParam int page){
        return permissionService.getClinicPermissionRequestsForUser(principal.getName(),page);
    }
    @PostMapping("permissions/grant")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void grantPermission(Principal principal, @RequestParam String clinicId, @RequestParam String permissionAccess){
        permissionService.permitClinic(clinicId, principal.getName(), permissionAccess);
    }
    @PostMapping("permissions/revoke")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void revokePermissionForUser(Principal principal, @RequestParam String clinicId){
        permissionService.revokeClinicPermissionForUser(clinicId,principal.getName());
    }
    @GetMapping("notifications")
    public ArrayList<NotificationResponse> getNotificationsForUser(Principal principal, @RequestParam int page){
        return notificationService.getNotificationsForUser(page, principal.getName());
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
    public ArrayList<AuditModel> getAuditLogInformation(Principal principal,@RequestParam int page){
        return auditService.getAuditsByActorReference(page,principal.getName());
    }
    @PostMapping("delete")
    public void deleteAccount(Principal principal){
        userService.deleteAccount(principal.getName());
    }
}