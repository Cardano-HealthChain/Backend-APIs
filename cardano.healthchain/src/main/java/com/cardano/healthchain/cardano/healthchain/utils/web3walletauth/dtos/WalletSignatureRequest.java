package com.cardano.healthchain.cardano.healthchain.utils.web3walletauth.dtos;

public class WalletSignatureRequest {
    private String walletAddress;
    private String message;
    private String signature;
    private String publicKey;

    public WalletSignatureRequest() {
    }

    public WalletSignatureRequest(String walletAddress, String message, String signature, String publicKey) {
        this.walletAddress = walletAddress;
        this.message = message;
        this.signature = signature;
        this.publicKey = publicKey;
    }

    public String getWalletAddress() {
        return walletAddress;
    }

    public void setWalletAddress(String walletAddress) {
        this.walletAddress = walletAddress;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }
}
