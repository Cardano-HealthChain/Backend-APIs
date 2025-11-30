package com.cardano.healthchain.cardano.healthchain.utils.web3Wallet.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WalletConnectionRequest {
    public String walletAddress;
    public String did;
    public String connectionStatus;
}