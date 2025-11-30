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
    @PostMapping("profile/personal_details")
    public boolean updateUserProfileWithPersonalDetails(@Valid @RequestBody UserUpdateProfilePersonalDetails userUpdateProfilePersonalDetails){
        return userService.updateUserProfileWithPersonalDetails(userUpdateProfilePersonalDetails);
    }
    @PostMapping("profile/basic_health_information")
    public boolean updateUserProfileWithHealthInformation(@Valid @RequestBody UserUpdateProfileHealthInformation userUpdateProfileHealthInformation){
        return userService.updateUserProfileWithHealthInformation(userUpdateProfileHealthInformation);
    }
    @PostMapping("profile/emergencyContact")
    public boolean updateUserProfileWithEmergencyContact(@Valid @RequestBody UserUpdateEmergencyInformation userUpdateEmergencyInformation){
        return userService.updateUserProfileWithEmergencyContact(userUpdateEmergencyInformation);
    }
    @PostMapping("profile/location")
    public boolean updateUserProfileWithLocationData(@Valid @RequestBody UserUpdateLocationData userUpdateLocationData){
        return userService.updateUserProfileWithLocationData(userUpdateLocationData);
    }
    @PostMapping("wallet/connect")
    public WalletConnectionStatus connectUserWallet(@RequestBody WalletConnectionRequest walletConnectionRequest){
        //get principal from authentication after adding spring security
        String userId = "random";
        return walletService.connectWallet(walletConnectionRequest, userId);
    }
    @GetMapping("/records")
    public MedicalDataResponse getMedicalRecordPerPageForUser(@RequestParam int page) {
        //get userId from authentication object;
        String userId = "random";
        return medicalDataService.verifyAndGetMedicalRecordForUser(page,userId);
    }
    public MedicalDataResponse getMedicalRecordPerPageForUserFiltered(@RequestParam int page, @RequestParam String category) {
        //get userId from authentication object;
        String userId = "random";
        return medicalDataService.verifyAndGetMedicalRecordForUserFiltered(page,userId, category);
    }
    @GetMapping("permissions")
    public ArrayList<PermissionResponse> getPermittedClinicsForUser(){
        //get user_id from jwt
        return permissionService.getPermittedClinicsForUser("random user id gotten from jwt");
    }
    @PostMapping("permissions/grant")
    public PermissionResponse grantPermission(@RequestParam String clinicId, @RequestParam String expiresIn){
        String userId = "";
        return permissionService.permitClinic(clinicId, userId, expiresIn);
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