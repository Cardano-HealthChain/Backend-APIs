package com.cardano.healthchain.cardano.healthchain.user;

import com.cardano.healthchain.cardano.healthchain.user.dtos.*;
import com.cardano.healthchain.cardano.healthchain.utils.medicalData.MedicalDataService;
import com.cardano.healthchain.cardano.healthchain.utils.medicalData.dtos.MedicalDataResponse;
import com.cardano.healthchain.cardano.healthchain.utils.notifications.NotificationService;
import com.cardano.healthchain.cardano.healthchain.utils.notifications.dtos.NotificationResponse;
import com.cardano.healthchain.cardano.healthchain.utils.permissions.PermissionService;
import com.cardano.healthchain.cardano.healthchain.utils.permissions.dtos.PermissionResponse;
import com.cardano.healthchain.cardano.healthchain.utils.web3Wallet.WalletService;
import com.cardano.healthchain.cardano.healthchain.utils.web3Wallet.dtos.WalletConnectionRequest;
import com.cardano.healthchain.cardano.healthchain.utils.web3Wallet.dtos.WalletConnectionStatus;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("api/v1/resident")
public class UserController {
    private final UserService userService;
    private final PermissionService permissionService;
    private final WalletService walletService;
    private final MedicalDataService medicalDataService;
    private final NotificationService notificationService;
    public UserController(UserService userService, PermissionService permissionService, WalletService walletService, MedicalDataService medicalDataService, NotificationService notificationService) {
        this.userService = userService;
        this.permissionService = permissionService;
        this.walletService = walletService;
        this.medicalDataService = medicalDataService;
        this.notificationService = notificationService;
    }
    @PostMapping("signup")
    public UserCreateResponse signup(@Valid @RequestBody UserCreateRequest userCreateRequest){
        return userService.createUser(userCreateRequest);
    }
    @PostMapping("otp/validate")
    public UserCreateResponse validateUserOtp(@RequestParam String otpcode, @RequestParam String user_email){
        return userService.validateUserOtp(otpcode, user_email);
    }
    @PostMapping("profile/personal_details")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateUserProfileWithPersonalDetails(@Valid @RequestBody UserUpdateProfilePersonalDetails userUpdateProfilePersonalDetails){
        //get email from authentication object
        String user_email = "random";
        userService.updateUserProfileWithPersonalDetails(userUpdateProfilePersonalDetails,user_email);
    }
    @PostMapping("profile/basic_health_information")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateUserProfileWithHealthInformation(@Valid @RequestBody UserUpdateProfileHealthInformation userUpdateProfileHealthInformation){
        String user_email = "random";
        userService.updateUserProfileWithHealthInformation(userUpdateProfileHealthInformation,user_email);
    }
    @PostMapping("profile/emergency_contact")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateUserProfileWithEmergencyContact(@Valid @RequestBody UserUpdateEmergencyInformation userUpdateEmergencyInformation){
        String user_email = "random";
        userService.updateUserProfileWithEmergencyContact(userUpdateEmergencyInformation,user_email);
    }
    @PostMapping("profile/location")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateUserProfileWithLocationData(@Valid @RequestBody UserUpdateLocationData userUpdateLocationData){
        String user_email = "random";
        userService.updateUserProfileWithLocationData(userUpdateLocationData,user_email);
    }
    @PostMapping("wallet/connect")
    public WalletConnectionStatus connectUserWallet(@RequestBody WalletConnectionRequest walletConnectionRequest){
        //get principal from authentication after adding spring security
        String user_email = "random";
        return walletService.connectWallet(walletConnectionRequest, user_email);
    }
    @GetMapping("/record")
    public MedicalDataResponse getMedicalRecordById(@RequestParam String record_id) {
        return medicalDataService.getMedicalRecordById(record_id);
    }
    @GetMapping("/records")
    public ArrayList<MedicalDataResponse> getMedicalRecordsPerPageForUser(@RequestParam int page) {
        //get user-email from authentication object;
        String email = "random";
        return medicalDataService.verifyAndGetMedicalRecordsForUser(page,email);
    }
    @GetMapping("/records/filtered")
    public ArrayList<MedicalDataResponse> getMedicalRecordPerPageForUserFiltered(@RequestParam int page, @RequestParam String category) {
        //get user email from authentication object;
        String email = "random";
        return medicalDataService.verifyAndGetMedicalRecordsForUserFiltered(page,email, category);
    }
    @GetMapping("permissions")
    public ArrayList<PermissionResponse> getPermittedClinicsForUser(@RequestParam int page){
        //get user-email from jwt
        return permissionService.getPermittedClinicsForUser("user-email gotten from jwt",page);
    }
    @PostMapping("permissions/grant")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void grantPermission(@RequestParam String clinicId){
        String email = "";
        permissionService.permitClinic(clinicId, email);
    }
    @PostMapping("permissions/revoke")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void revokePermissionForUser(@RequestParam String clinicId){
        //get email from jwt
        String email = "";
        permissionService.revokeClinicPermissionForUser(clinicId,email);
    }
    @GetMapping("notifications")
    public ArrayList<NotificationResponse> getNotificationsForUser(@RequestParam int page, @RequestParam String category){
        return notificationService.getNotificationsForUser(page, category, "");
    }
    @PostMapping("notifications/read")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void markNotificationAsRead(@RequestParam String notificationId){
        notificationService.markNotificationAsRead(notificationId);
    }
}