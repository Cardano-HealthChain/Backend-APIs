package com.cardano.healthchain.cardano.healthchain.user;

import com.cardano.healthchain.cardano.healthchain.user.dtos.*;
import com.cardano.healthchain.cardano.healthchain.utils.web3Wallet.dtos.WalletConnectionRequest;
import com.cardano.healthchain.cardano.healthchain.utils.web3Wallet.dtos.WalletConnectionStatus;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepositoryI userRepository;
    public UserService(UserRepositoryI userRepository) {
        this.userRepository = userRepository;
    }
    public UserCreateResponse createUser(UserCreateRequest userCreateRequest) {
        return userRepository.createUser(userCreateRequest);
    }
    public boolean updateUserProfileWithPersonalDetails(UserUpdateProfilePersonalDetails userUpdateProfilePersonalDetails) {
        userRepository.updateUserProfilePersonalDetails(userUpdateProfilePersonalDetails);
        return true;
    }
    public boolean updateUserProfileWithHealthInformation(UserUpdateProfileHealthInformation userUpdateProfileHealthInformation) {
        userRepository.updateUserProfileHealthInformation(userUpdateProfileHealthInformation);
        return true;
    }
    public boolean updateUserProfileWithEmergencyContact(UserUpdateEmergencyInformation userUpdateEmergencyInformation) {
        userRepository.updateUserProfileEmergencyContact(userUpdateEmergencyInformation);
        return true;
    }
    public boolean updateUserProfileWithLocationData(UserUpdateLocationData userUpdateLocationData) {
        userRepository.updateUserProfileLocationData(userUpdateLocationData);
        return true;
    }
}