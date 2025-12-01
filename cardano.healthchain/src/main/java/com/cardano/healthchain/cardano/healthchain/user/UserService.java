package com.cardano.healthchain.cardano.healthchain.user;

import com.cardano.healthchain.cardano.healthchain.exceptions.PendingUserException;
import com.cardano.healthchain.cardano.healthchain.user.dtos.*;
import com.cardano.healthchain.cardano.healthchain.utils.JwtService;
import com.cardano.healthchain.cardano.healthchain.utils.otp.OtpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepositoryI userRepository;
    private final OtpService otpService;
    private final JwtService jwtService;
    private final Logger logger = LoggerFactory.getLogger(UserService.class);
    public UserService(UserRepositoryI userRepository, OtpService otpService, JwtService jwtService) {
        this.userRepository = userRepository;
        this.otpService = otpService;
        this.jwtService = jwtService;
    }
    public String createUser(UserCreateRequest userCreateRequest) {
        if(this.checkIfUserHasPendingAccount(userCreateRequest.getEmail())){

            logger.info(String.format("User with email: %s wasn't verified otp resent",userCreateRequest.getEmail()));

            otpService.sendOtpMessageToEmail(userCreateRequest.getEmail(),otpService.generateOTP(4));
            throw new PendingUserException("Email has already been used but not verified, OTP resent!");
        };
        userRepository.createUser(userCreateRequest);
        otpService.sendOtpMessageToEmail(userCreateRequest.getEmail(),otpService.generateOTP(4));

        logger.info(String.format("User with email: %s created, otp sent for verification",userCreateRequest.getEmail()));

        return String.format("OTP was sent to: %s for verification",userCreateRequest.getEmail());
    }
    public void updateUserProfileWithPersonalDetails(UserUpdateProfilePersonalDetails userUpdateProfilePersonalDetails, String email) {
        userRepository.updateUserProfilePersonalDetails(userUpdateProfilePersonalDetails, email);
        logger.info("Personal data profile completion stage is successful");
    }
    public void updateUserProfileWithHealthInformation(UserUpdateProfileHealthInformation userUpdateProfileHealthInformation, String email) {
        userRepository.updateUserProfileHealthInformation(userUpdateProfileHealthInformation, email);
        logger.info("Health information data profile completion stage is successful");
    }
    public void updateUserProfileWithEmergencyContact(UserUpdateEmergencyInformation userUpdateEmergencyInformation, String email) {
        userRepository.updateUserProfileEmergencyContact(userUpdateEmergencyInformation, email);
        logger.info("Emergency information profile completion stage is successful");
    }
    public void updateUserProfileWithLocationData(UserUpdateLocationData userUpdateLocationData, String email) {
        userRepository.updateUserProfileLocationData(userUpdateLocationData,email);
        logger.info("Location data profile completion stage is successful");
    }
    public UserCreateResponse validateUserOtp(String otpcode, String email) {
        otpService.checkOTPValidity(otpcode, email);
        logger.info(String.format("OTP validation for email: %s was successful",email));
        return UserCreateResponse
                .builder()
                .user_email(email)
                .token(jwtService.generateToken(email))
                .build();
    }
    private boolean checkIfUserHasPendingAccount(String email) {
        UserModel userByEmail = userRepository.getUserByEmail(email);
        return userByEmail.isVerified();
    }
}