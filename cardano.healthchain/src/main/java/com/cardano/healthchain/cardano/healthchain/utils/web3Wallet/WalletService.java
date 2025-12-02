package com.cardano.healthchain.cardano.healthchain.utils.web3Wallet;

import com.cardano.healthchain.cardano.healthchain.utils.web3Wallet.dtos.WalletConnectionRequest;
import com.cardano.healthchain.cardano.healthchain.utils.web3Wallet.dtos.WalletConnectionStatus;
import org.springframework.stereotype.Service;

@Service
public class WalletService {
    private final WalletRepositoryI walletRepository;
    public WalletService(WalletRepositoryI walletRepository) {
        this.walletRepository = walletRepository;
    }
    public WalletConnectionStatus connectWallet(WalletConnectionRequest walletConnectionRequest, String userId) {
        walletRepository.connectWallet(walletConnectionRequest, userId);
        return new WalletConnectionStatus(true,String.format("Wallet Address: %s was connected to User with DID: %s",walletConnectionRequest.getWalletAddress(), walletConnectionRequest.getDid()));
    }
}
