package com.cardano.healthchain.cardano.healthchain.user;

import com.cardano.healthchain.cardano.healthchain.user.dtos.*;

public interface UserRepositoryI {
    UserCreateResponse createUser(UserCreateRequest userCreateRequest);

    void updateUserProfilePersonalDetails(UserUpdateProfilePersonalDetails userUpdateProfilePersonalDetails);

    void updateUserProfileHealthInformation(UserUpdateProfileHealthInformation userUpdateProfileHealthInformation);

    void updateUserProfileEmergencyContact(UserUpdateEmergencyInformation userUpdateEmergencyInformation);

    void updateUserProfileLocationData(UserUpdateLocationData userUpdateLocationData);
}
