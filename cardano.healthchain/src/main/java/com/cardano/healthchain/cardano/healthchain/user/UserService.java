package com.cardano.healthchain.cardano.healthchain.user;

import com.cardano.healthchain.cardano.healthchain.user.dtos.*;
import com.cardano.healthchain.cardano.healthchain.utils.Healthchain_Roles_Enum;
import com.cardano.healthchain.cardano.healthchain.security.JwtService;
import com.cardano.healthchain.cardano.healthchain.utils.audit.enums.ActorTypeEnum;
import com.cardano.healthchain.cardano.healthchain.utils.audit.AuditService;
import com.cardano.healthchain.cardano.healthchain.utils.otp.OtpServiceEmailImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepositoryI userRepository;
    private final OtpServiceEmailImpl otpServiceEmailImpl;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuditService auditService;
    private final Logger logger = LoggerFactory.getLogger(UserService.class);
    public UserService(UserRepositoryI userRepository, OtpServiceEmailImpl otpServiceEmailImpl, JwtService jwtService, PasswordEncoder passwordEncoder, AuditService auditService) {
        this.userRepository = userRepository;
        this.otpServiceEmailImpl = otpServiceEmailImpl;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.auditService = auditService;
    }
    @Transactional
    public UserCreateResponse createUserWithEmail(UserCreateRequest userCreateRequest) {
//        MVP won't require OTP, OTP logic commented out to implement later
//        if(this.checkIfUserHasPendingAccount(userCreateRequest.getEmail())){
//            otpServiceEmailImpl.sendOtpMessageToEmail(userCreateRequest.getEmail(), otpServiceEmailImpl.generateOTP(6));
//            throw new PendingUserException("Email has already been used but not verified, OTP resent!");
//        };
        userCreateRequest.setPassword(passwordEncoder.encode(userCreateRequest.getPassword()));
        UUID user_id = userRepository.createUser(userCreateRequest);
//        otpServiceEmailImpl.sendOtpMessageToEmail(userCreateRequest.getEmail(), otpServiceEmailImpl.generateOTP(6));
//        logger.info("OTP email sent".concat(LocalDateTime.now().toString()));
//        return String.format("OTP was sent to: %s for verification",userCreateRequest.getEmail());
        return new UserCreateResponse(
                userCreateRequest.getEmail(),
                jwtService.generateTokenWithEntityId(
                        user_id.toString(),
                        Healthchain_Roles_Enum.RESIDENT.name().toLowerCase(),
                        Map.of()
                )
        );
    }
    public UserCreateResponse validateUserOtp(String otpcode, String email) {
        otpServiceEmailImpl.checkOTPValidity(otpcode, email);
        userRepository.verifyUserAccount(email);
        UserDataProfileResponse userByEmail = userRepository.getUserByEmail(email);
        logger.info(String.format("OTP validation was successful".concat(String.valueOf(LocalDateTime.now()))));
        return new UserCreateResponse(
                email,
                jwtService.generateTokenWithEntityId(
                        userByEmail.getUser_id().toString(),
                        Healthchain_Roles_Enum.RESIDENT.toString().toLowerCase(),
                        Map.of()
                )
        );
    }
    @Transactional
    public void updateUserProfileWithPersonalDetails(UserUpdateProfilePersonalDetailsRequest userUpdateProfilePersonalDetailsRequest, String user_id) {
        userRepository.updateUserProfilePersonalDetails(userUpdateProfilePersonalDetailsRequest, user_id);
        auditService.logAuditEvent(ActorTypeEnum.RESIDENT,user_id,"Profile Update","Update Profile with Personal details");
        logger.info("Personal data profile completion stage is successful");
        logger.info(userUpdateProfilePersonalDetailsRequest.getFirstname().concat(" ").concat(userUpdateProfilePersonalDetailsRequest.getLastname()));
    }
    @Transactional
    public void updateUserProfileWithHealthInformation(UserUpdateProfileHealthInformationRequest userUpdateProfileHealthInformationRequest, String user_id) {
        userRepository.updateUserProfileHealthInformation(userUpdateProfileHealthInformationRequest, user_id);
        auditService.logAuditEvent(ActorTypeEnum.RESIDENT,user_id,"Profile Update","Update Profile with health information");
        logger.info("Health information data profile completion stage is successful");
    }
    @Transactional
    public void updateUserProfileWithEmergencyContact(UserUpdateEmergencyInformationRequest userUpdateEmergencyInformationRequest, String user_id) {
        userRepository.updateUserProfileEmergencyContact(userUpdateEmergencyInformationRequest, user_id);
        auditService.logAuditEvent(ActorTypeEnum.RESIDENT,user_id,"Profile Update","Update Profile with emergency information");
        logger.info("Emergency information profile completion stage is successful");
    }
    @Transactional
    public void updateUserProfileWithLocationData(UserUpdateLocationDataRequest userUpdateLocationDataRequest, String user_id) {
        userRepository.updateUserProfileLocationData(userUpdateLocationDataRequest,user_id);
        auditService.logAuditEvent(ActorTypeEnum.RESIDENT,user_id,"Profile Update","Update Profile with location data");
        logger.info("Location data profile completion stage is successful");
    }
    private boolean checkIfUserHasPendingAccount(String user_id) {
        UserDataProfileResponse userById = userRepository.getUserById(user_id);
        if(userById == null) return false;
        return userById.isVerified();
    }
    public int getProfileCompletionLevel(String user_id) {
        UserDataProfileResponse user = userRepository.getUserById(user_id);
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
        if (user.getBlood_type() != null && !user.getBlood_type().isEmpty()) filled++;
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

    public void deleteAccount(String user_id) {
        userRepository.deleteUserById(user_id);
    }

    public UserDataProfileResponse getUserByEmail(String user_email) {
        return userRepository.getUserByEmail(user_email);
    }
    public UserDataProfileResponse getUserById(String user_id) {
        return userRepository.getUserById(user_id);
    }

    public ArrayList<UserDataProfileResponse> getUsersSimilarToSearchTerm(String searchTerm, int page) {
        return userRepository.getUsersSimilarToSearchTerm(searchTerm,page);
    }
}