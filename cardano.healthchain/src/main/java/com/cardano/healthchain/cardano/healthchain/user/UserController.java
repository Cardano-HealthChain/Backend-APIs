package com.cardano.healthchain.cardano.healthchain.user;

import com.cardano.healthchain.cardano.healthchain.user.dtos.*;
import com.cardano.healthchain.cardano.healthchain.utils.blockchain.BlockChainService;
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
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("api/v1/user")
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
    public boolean updateUserProfileWithPersonalDetails(@Valid @RequestBody UserUpdateProfilePersonalDetails userUpdateProfilePersonalDetails){
        String userId = "random";
        return userService.updateUserProfileWithPersonalDetails(userUpdateProfilePersonalDetails,userId);
    }
    @PostMapping("profile/basic_health_information")
    public boolean updateUserProfileWithHealthInformation(@Valid @RequestBody UserUpdateProfileHealthInformation userUpdateProfileHealthInformation){
        String userId = "random";
        return userService.updateUserProfileWithHealthInformation(userUpdateProfileHealthInformation,userId);
    }
    @PostMapping("profile/emergency_contact")
    public boolean updateUserProfileWithEmergencyContact(@Valid @RequestBody UserUpdateEmergencyInformation userUpdateEmergencyInformation){
        String userId = "random";
        return userService.updateUserProfileWithEmergencyContact(userUpdateEmergencyInformation,userId);
    }
    @PostMapping("profile/location")
    public boolean updateUserProfileWithLocationData(@Valid @RequestBody UserUpdateLocationData userUpdateLocationData){
        String userId = "random";
        return userService.updateUserProfileWithLocationData(userUpdateLocationData,userId);
    }
    @PostMapping("wallet/connect")
    public WalletConnectionStatus connectUserWallet(@RequestBody WalletConnectionRequest walletConnectionRequest){
        //get principal from authentication after adding spring security
        String userId = "random";
        return walletService.connectWallet(walletConnectionRequest, userId);
    }
    @GetMapping("/record")
    public MedicalDataResponse getMedicalRecordById(@RequestParam String record_id) {
        //get userId from authentication object;
        return medicalDataService.getMedicalRecordById(record_id);
    }
    @GetMapping("/records")
    public ArrayList<MedicalDataResponse> getMedicalRecordsPerPageForUser(@RequestParam int page) {
        //get userId from authentication object;
        String email = "random";
        return medicalDataService.verifyAndGetMedicalRecordsForUser(page,email);

    }
    @GetMapping("/records/filtered")
    public ArrayList<MedicalDataResponse> getMedicalRecordPerPageForUserFiltered(@RequestParam int page, @RequestParam String category) {
        //get userId from authentication object;
        String email = "random";
        return medicalDataService.verifyAndGetMedicalRecordsForUserFiltered(page,email, category);
    }
    @GetMapping("permissions")
    public ArrayList<PermissionResponse> getPermittedClinicsForUser(){
        //get user_id from jwt
        return permissionService.getPermittedClinicsForUser("random user id gotten from jwt");
    }
    @PostMapping("permissions/grant")
    public PermissionResponse grantPermission(@RequestParam String clinicId, @RequestParam String expiresIn){
        String email = "";
        return permissionService.permitClinic(clinicId, email, expiresIn);
    }
    @PostMapping("permissions/revoke")
    public boolean revokePermission(@RequestParam String clinicId){
        return permissionService.revokeClinicPermission(clinicId);
    }
    @GetMapping("notifications")
    public ArrayList<NotificationResponse> getNotificationsForUser(@RequestParam int page, @RequestParam String category){
        return notificationService.getNotificationsForUser(page, category, "");
    }
    @PostMapping("notifications/read")
    public boolean markNotificationAsRead(@RequestParam String notificationId){
        return notificationService.markNotificationAsRead(notificationId);
    }
}