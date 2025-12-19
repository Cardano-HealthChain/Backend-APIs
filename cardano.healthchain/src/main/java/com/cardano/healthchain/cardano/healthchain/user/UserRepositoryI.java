package com.cardano.healthchain.cardano.healthchain.user;

import com.cardano.healthchain.cardano.healthchain.user.dtos.*;

import java.util.Optional;
import java.util.UUID;

public interface UserRepositoryI {
    UUID createUser(UserCreateRequest userCreateRequest);
    UserDataProfileResponse getUserByEmail(String user_email);
    UserDataProfileResponse getUserById(String user_id);
    void updateUserProfilePersonalDetails(UserUpdateProfilePersonalDetailsRequest userUpdateProfilePersonalDetailsRequest, String user_id);
    void updateUserProfileHealthInformation(UserUpdateProfileHealthInformationRequest userUpdateProfileHealthInformationRequest, String user_id);
    void updateUserProfileEmergencyContact(UserUpdateEmergencyInformationRequest userUpdateEmergencyInformationRequest, String user_id);
    void updateUserProfileLocationData(UserUpdateLocationDataRequest userUpdateLocationDataRequest, String user_id);
    void verifyUserAccount(String email);
    void deleteUserByEmail(String email);
    void deleteUserById(String user_id);
    void existsByWalletAddress(String walletAddress);
    Optional<UserDataProfileResponse> findByWalletAddress(String walletAddress);
    UUID createMinimalUserForWalletSignUp(UserDataProfileResponse user);
    void updateWalletInfo(UserDataProfileResponse user);
    void userAddToClinicsSharedRecordWith(String userId, String clinicId);
}