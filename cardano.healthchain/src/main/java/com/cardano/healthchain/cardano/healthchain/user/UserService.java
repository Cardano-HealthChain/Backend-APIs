package com.cardano.healthchain.cardano.healthchain.user;

import com.cardano.healthchain.cardano.healthchain.exceptions.PendingUserException;
import com.cardano.healthchain.cardano.healthchain.user.dtos.*;
import com.cardano.healthchain.cardano.healthchain.utils.JwtService;
import com.cardano.healthchain.cardano.healthchain.utils.otp.OtpServiceEmailImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public String createUser(UserCreateRequest userCreateRequest) {
        if(this.checkIfUserHasPendingAccount(userCreateRequest.getEmail())){
            otpServiceEmailImpl.sendOtpMessageToEmail(userCreateRequest.getEmail(), otpServiceEmailImpl.generateOTP(6));
            throw new PendingUserException("Email has already been used but not verified, OTP resent!");
        };
        userCreateRequest.setPassword(passwordEncoder.encode(userCreateRequest.getPassword()));
        userRepository.createUser(userCreateRequest);
        otpServiceEmailImpl.sendOtpMessageToEmail(userCreateRequest.getEmail(), otpServiceEmailImpl.generateOTP(6));
        logger.info(String.format("User with email: %s created, otp sent for verification",userCreateRequest.getEmail()));
        return String.format("OTP was sent to: %s for verification",userCreateRequest.getEmail());
    }
    @Transactional
    public void updateUserProfileWithPersonalDetails(UserUpdateProfilePersonalDetails userUpdateProfilePersonalDetails, String email) {
        userRepository.updateUserProfilePersonalDetails(userUpdateProfilePersonalDetails, email);
        logger.info("Personal data profile completion stage is successful");
    }
    @Transactional
    public void updateUserProfileWithHealthInformation(UserUpdateProfileHealthInformation userUpdateProfileHealthInformation, String email) {
        userRepository.updateUserProfileHealthInformation(userUpdateProfileHealthInformation, email);
        logger.info("Health information data profile completion stage is successful");
    }
    @Transactional
    public void updateUserProfileWithEmergencyContact(UserUpdateEmergencyInformation userUpdateEmergencyInformation, String email) {
        userRepository.updateUserProfileEmergencyContact(userUpdateEmergencyInformation, email);
        logger.info("Emergency information profile completion stage is successful");
    }
    @Transactional
    public void updateUserProfileWithLocationData(UserUpdateLocationData userUpdateLocationData, String email) {
        userRepository.updateUserProfileLocationData(userUpdateLocationData,email);
        logger.info("Location data profile completion stage is successful");
    }
    public UserCreateResponse validateUserOtp(String otpcode, String email) {
        otpServiceEmailImpl.checkOTPValidity(otpcode, email);
        userRepository.verifyUserAccount(email);
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
    public int getProfileCompletionLevel(String user_email) {
        UserModel user = userRepository.getUserByEmail(user_email);
        if (user == null) return 0;

        int totalFields = 14; // fields in the sections you care about
        int filled = 0;

        // Signup Captured Details
        if (user.getFirstName() != null && !user.getFirstName().isEmpty()) filled++;
        if (user.getLastName() != null && !user.getLastName().isEmpty()) filled++;
        if (user.getPhoneNumber() != null && !user.getPhoneNumber().isEmpty()) filled++;
        if (user.getDob() != null) filled++;

        // Basic Health Information
        if (user.getGender() != null && !user.getGender().isEmpty()) filled++;
        if (user.getAddress() != null && !user.getAddress().isEmpty()) filled++;
        if (user.getBloodType() != null && !user.getBloodType().isEmpty()) filled++;
        if (user.getGenotype() != null && !user.getGenotype().isEmpty()) filled++;
        if (user.getKnownAllergies() != null && !user.getKnownAllergies().isEmpty()) filled++;
        if (user.getPreExistingConditions() != null && !user.getPreExistingConditions().isEmpty()) filled++;

        // Emergency Contact Information
        if (user.getEmergencyContactName() != null && !user.getEmergencyContactName().isEmpty()) filled++;
        if (user.getEmergencyContactPhone() != null && !user.getEmergencyContactPhone().isEmpty()) filled++;
        if (user.getEmergencyContactRelationship() != null && !user.getEmergencyContactRelationship().isEmpty()) filled++;

        // Location Details
        if (user.getNationality() != null && !user.getNationality().isEmpty()) filled++;
        if (user.getStateOfOrigin() != null && !user.getStateOfOrigin().isEmpty()) filled++;

        // Calculate completion percentage
        int completionPercentage = (filled * 100) / totalFields;
        // Update completion_level in DB
        return completionPercentage;
    }

    public void deleteAccount(String email) {
        userRepository.deleteUserByEmail(email);
    }
}