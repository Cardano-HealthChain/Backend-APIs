package com.cardano.healthchain.cardano.healthchain.utils.web3Wallet;

import com.cardano.healthchain.cardano.healthchain.utils.web3Wallet.dtos.WalletConnectionRequest;

public interface WalletRepositoryI {
    void connectWallet(WalletConnectionRequest walletConnectionRequest, String userId);
}
