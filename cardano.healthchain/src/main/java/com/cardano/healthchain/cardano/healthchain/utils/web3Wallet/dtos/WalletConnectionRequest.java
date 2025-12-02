package com.cardano.healthchain.cardano.healthchain.utils.web3Wallet.dtos;

import lombok.Builder;
import lombok.Data;

public class WalletConnectionRequest {
    public String walletAddress;
    public String did;
    public String connectionStatus;

    public WalletConnectionRequest(String walletAddress, String did, String connectionStatus) {
        this.walletAddress = walletAddress;
        this.did = did;
        this.connectionStatus = connectionStatus;
    }

    public String getWalletAddress() {
        return walletAddress;
    }

    public void setWalletAddress(String walletAddress) {
        this.walletAddress = walletAddress;
    }

    public String getDid() {
        return did;
    }

    public void setDid(String did) {
        this.did = did;
    }

    public String getConnectionStatus() {
        return connectionStatus;
    }

    public void setConnectionStatus(String connectionStatus) {
        this.connectionStatus = connectionStatus;
    }
}