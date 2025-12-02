package com.cardano.healthchain.cardano.healthchain.utils.web3Wallet.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

public class WalletConnectionStatus {
    public Boolean ConnectionStatus;
    public String ConnectionMessage;

    public WalletConnectionStatus(Boolean connectionStatus, String connectionMessage) {
        ConnectionStatus = connectionStatus;
        ConnectionMessage = connectionMessage;
    }

    public Boolean getConnectionStatus() {
        return ConnectionStatus;
    }

    public void setConnectionStatus(Boolean connectionStatus) {
        ConnectionStatus = connectionStatus;
    }

    public String getConnectionMessage() {
        return ConnectionMessage;
    }

    public void setConnectionMessage(String connectionMessage) {
        ConnectionMessage = connectionMessage;
    }
}