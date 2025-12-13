package com.cardano.healthchain.cardano.healthchain.user;

import com.cardano.healthchain.cardano.healthchain.user.dtos.*;

import java.util.Optional;
import java.util.UUID;

public interface UserRepositoryI {
    UUID createUser(UserCreateRequest userCreateRequest);
    UserModel getUserByEmail(String user_email);
    UserModel getUserById(String user_id);
    void updateUserProfilePersonalDetails(UserUpdateProfilePersonalDetails userUpdateProfilePersonalDetails, String user_id);
    void updateUserProfileHealthInformation(UserUpdateProfileHealthInformation userUpdateProfileHealthInformation, String user_id);
    void updateUserProfileEmergencyContact(UserUpdateEmergencyInformation userUpdateEmergencyInformation, String user_id);
    void updateUserProfileLocationData(UserUpdateLocationData userUpdateLocationData, String user_id);
    void verifyUserAccount(String email);
    void deleteUserByEmail(String email);
    void deleteUserById(String user_id);
    void existsByWalletAddress(String walletAddress);
    Optional<UserModel> findByWalletAddress(String walletAddress);
    UUID createMinimalUserForWalletSignUp(UserModel user);
    void updateWalletInfo(UserModel user);
}