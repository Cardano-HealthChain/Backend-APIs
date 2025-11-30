package com.cardano.healthchain.cardano.healthchain.utils.web3Wallet.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WalletConnectionStatus {
    public Boolean ConnectionStatus;
    public String ConnectionMessage;
}
