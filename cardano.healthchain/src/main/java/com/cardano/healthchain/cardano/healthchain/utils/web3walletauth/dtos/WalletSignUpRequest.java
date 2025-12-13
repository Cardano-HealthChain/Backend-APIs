package com.cardano.healthchain.cardano.healthchain.utils.web3walletauth.dtos;

public class WalletSignUpRequest {
    private String walletAddress;
    private String stakeAddress;
    private String publicKey;
    private String signature;
    private String message;
    private String role;
    public WalletSignUpRequest() {
    }
    public WalletSignUpRequest(String walletAddress, String stakeAddress, String publicKey, String signature, String message, String role) {
        this.walletAddress = walletAddress;
        this.stakeAddress = stakeAddress;
        this.publicKey = publicKey;
        this.signature = signature;
        this.message = message;
        this.role = role;
    }
    public String getWalletAddress() {
        return walletAddress;
    }

    public void setWalletAddress(String walletAddress) {
        this.walletAddress = walletAddress;
    }

    public String getStakeAddress() {
        return stakeAddress;
    }

    public void setStakeAddress(String stakeAddress) {
        this.stakeAddress = stakeAddress;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "WalletSignUpRequest{" +
                "walletAddress='" + walletAddress + '\'' +
                ", stakeAddress='" + stakeAddress + '\'' +
                ", publicKey='" + publicKey + '\'' +
                ", signature='" + signature + '\'' +
                ", message='" + message + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}