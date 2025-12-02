package com.cardano.healthchain.cardano.healthchain.user;

import com.cardano.healthchain.cardano.healthchain.user.dtos.*;

public interface UserRepositoryI {
    void createUser(UserCreateRequest userCreateRequest);
    UserModel getUserByEmail(String user_email);
    void updateUserProfilePersonalDetails(UserUpdateProfilePersonalDetails userUpdateProfilePersonalDetails, String user_id);
    void updateUserProfileHealthInformation(UserUpdateProfileHealthInformation userUpdateProfileHealthInformation, String user_id);
    void updateUserProfileEmergencyContact(UserUpdateEmergencyInformation userUpdateEmergencyInformation, String user_id);
    void updateUserProfileLocationData(UserUpdateLocationData userUpdateLocationData, String user_id);
    void verifyUserAccount(String email);
    void deleteUserByEmail(String email);
}
