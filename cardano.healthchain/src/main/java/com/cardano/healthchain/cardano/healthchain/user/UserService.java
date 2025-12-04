package com.cardano.healthchain.cardano.healthchain.user;

import com.cardano.healthchain.cardano.healthchain.user.dtos.*;
import com.cardano.healthchain.cardano.healthchain.utils.JwtService;
import com.cardano.healthchain.cardano.healthchain.utils.otp.OtpServiceEmailImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class UserService {
    private final UserRepositoryI userRepository;
    private final OtpServiceEmailImpl otpServiceEmailImpl;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final Logger logger = LoggerFactory.getLogger(UserService.class);
    public UserService(UserRepositoryI userRepository, OtpServiceEmailImpl otpServiceEmailImpl, JwtService jwtService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.otpServiceEmailImpl = otpServiceEmailImpl;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }
    @Transactional
    public UserCreateResponse createUser(UserCreateRequest userCreateRequest) {
//        if(this.checkIfUserHasPendingAccount(userCreateRequest.getEmail())){
//            otpServiceEmailImpl.sendOtpMessageToEmail(userCreateRequest.getEmail(), otpServiceEmailImpl.generateOTP(6));
//            throw new PendingUserException("Email has already been used but not verified, OTP resent!");
//        };
        userCreateRequest.setPassword(passwordEncoder.encode(userCreateRequest.getPassword()));
        userRepository.createUser(userCreateRequest);
        return new UserCreateResponse(userCreateRequest.getEmail(),jwtService.generateToken(userCreateRequest.getEmail()));
//        otpServiceEmailImpl.sendOtpMessageToEmail(userCreateRequest.getEmail(), otpServiceEmailImpl.generateOTP(6));
//        logger.info("OTP email sent".concat(LocalDateTime.now().toString()));
//        return String.format("OTP was sent to: %s for verification",userCreateRequest.getEmail());
    }
    public UserCreateResponse validateUserOtp(String otpcode, String email) {
        otpServiceEmailImpl.checkOTPValidity(otpcode, email);
        userRepository.verifyUserAccount(email);
        logger.info(String.format("OTP validation was successful".concat(String.valueOf(LocalDateTime.now()))));
        return new UserCreateResponse(email,jwtService.generateToken(email));
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
    private boolean checkIfUserHasPendingAccount(String email) {
        UserModel userByEmail = userRepository.getUserByEmail(email);
        if(userByEmail == null) return false;
        return userByEmail.isVerified();
    }
    public int getProfileCompletionLevel(String user_email) {
        UserModel user = userRepository.getUserByEmail(user_email);
        if (user == null) return 0;

        int filled = 0;

// Signup Captured Details
        if (user.getFirst_name() != null && !user.getFirst_name().isEmpty()) filled++;
        if (user.getLast_name() != null && !user.getLast_name().isEmpty()) filled++;
        if (user.getPhone_number() != null && !user.getPhone_number().isEmpty()) filled++;
        if (user.getDob() != null) filled++;

// Basic Health Information
        if (user.getGender() != null && !user.getGender().isEmpty()) filled++;
        if (user.getAddress() != null && !user.getAddress().isEmpty()) filled++;
        if (user.getBloodType() != null && !user.getBloodType().isEmpty()) filled++;
        if (user.getGenotype() != null && !user.getGenotype().isEmpty()) filled++;
        if (user.getKnown_allergies() != null && !user.getKnown_allergies().isEmpty()) filled++;
        if (user.getPre_existing_conditions() != null && !user.getPre_existing_conditions().isEmpty()) filled++;

// Emergency Contact Information
        if (user.getEmergency_contact_name() != null && !user.getEmergency_contact_name().isEmpty()) filled++;
        if (user.getEmergency_contact_phone() != null && !user.getEmergency_contact_phone().isEmpty()) filled++;
        if (user.getEmergency_contact_rel() != null && !user.getEmergency_contact_rel().isEmpty()) filled++;

// Location Details
        if (user.getNationality() != null && !user.getNationality().isEmpty()) filled++;
        if (user.getState_of_origin() != null && !user.getState_of_origin().isEmpty()) filled++;

// MUST be 15
        int totalFields = 15;

        return (filled * 100) / totalFields;
    }

    public void deleteAccount(String email) {
        userRepository.deleteUserByEmail(email);
    }
}