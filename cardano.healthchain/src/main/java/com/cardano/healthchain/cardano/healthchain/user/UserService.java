package com.cardano.healthchain.cardano.healthchain.user;

import com.cardano.healthchain.cardano.healthchain.user.dtos.*;
import com.cardano.healthchain.cardano.healthchain.utils.otp.OtpService;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepositoryI userRepository;
    private final OtpService otpService;
    public UserService(UserRepositoryI userRepository, OtpService otpService) {
        this.userRepository = userRepository;
        this.otpService = otpService;
    }

    public UserCreateResponse createUser(UserCreateRequest userCreateRequest) {
        if(this.checkIfUserHasPendingAccount(userCreateRequest.getEmail())){
            otpService.sendOtpMessageToEmail(userCreateRequest.getEmail(),otpService.generateOTP(4));
        };
        UserCreateResponse createdUser = userRepository.createUser(userCreateRequest);
        otpService.sendOtpMessageToEmail(userCreateRequest.getEmail(),otpService.generateOTP(4));
        return createdUser;
    }
    public boolean updateUserProfileWithPersonalDetails(UserUpdateProfilePersonalDetails userUpdateProfilePersonalDetails, String email) {
        userRepository.updateUserProfilePersonalDetails(userUpdateProfilePersonalDetails, email);
        return true;
    }
    public boolean updateUserProfileWithHealthInformation(UserUpdateProfileHealthInformation userUpdateProfileHealthInformation, String email) {
        userRepository.updateUserProfileHealthInformation(userUpdateProfileHealthInformation, email);
        return true;
    }
    public boolean updateUserProfileWithEmergencyContact(UserUpdateEmergencyInformation userUpdateEmergencyInformation, String email) {
        userRepository.updateUserProfileEmergencyContact(userUpdateEmergencyInformation, email);
        return true;
    }
    public boolean updateUserProfileWithLocationData(UserUpdateLocationData userUpdateLocationData, String email) {
        userRepository.updateUserProfileLocationData(userUpdateLocationData,email);
        return true;
    }
    public UserCreateResponse validateUserOtp(String otpcode, String email) {
        otpService.checkOTPValidity(otpcode, email);
        return UserCreateResponse
                .builder()
                .user_email(email)
                //generate token using email as subject
                .token("token")
                .build();
    }
    private boolean checkIfUserHasPendingAccount(String email) {
        UserModel userByEmail = userRepository.getUserByEmail(email);
        return userByEmail.isVerified();
    }
}