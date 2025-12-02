package com.cardano.healthchain.cardano.healthchain.utils.otp.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
public class OtpResponse {
    private String otpId;
    private String email;
    private String otpCode;
    private boolean used;
    private LocalDateTime expiresAt;
    public OtpResponse(String otpId, String email, String otpCode, boolean used, LocalDateTime expiresAt) {
        this.otpId = otpId;
        this.email = email;
        this.otpCode = otpCode;
        this.used = used;
        this.expiresAt = expiresAt;
    }
    public String getOtpId() {
        return otpId;
    }

    public void setOtpId(String otpId) {
        this.otpId = otpId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOtpCode() {
        return otpCode;
    }

    public void setOtpCode(String otpCode) {
        this.otpCode = otpCode;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }
}