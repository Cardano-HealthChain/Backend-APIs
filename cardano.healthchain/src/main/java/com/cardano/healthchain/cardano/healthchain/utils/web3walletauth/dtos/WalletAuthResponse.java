package com.cardano.healthchain.cardano.healthchain.utils.web3walletauth.dtos;

public class WalletAuthResponse {
    private boolean success;
    private String token;
    private String role;
    private String walletAddress;
    private String message;

    public WalletAuthResponse() {
    }

    public WalletAuthResponse(boolean success, String token, String role, String walletAddress, String message) {
        this.success = success;
        this.token = token;
        this.role = role;
        this.walletAddress = walletAddress;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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

    @Override
    public String toString() {
        return "WalletAuthResponse{" +
                "success=" + success +
                ", token='" + token + '\'' +
                ", role='" + role + '\'' +
                ", walletAddress='" + walletAddress + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
