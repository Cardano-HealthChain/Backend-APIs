package com.cardano.healthchain.cardano.healthchain.utils.web3walletauth;

import java.time.LocalDateTime;
import java.util.Optional;

public interface WalletRepositoryI {
    void saveChallenge(String walletAddress, String challenge, LocalDateTime expiresAt);
    Optional<String> getChallenge(String walletAddress);
    void deleteChallenge(String walletAddress);
}