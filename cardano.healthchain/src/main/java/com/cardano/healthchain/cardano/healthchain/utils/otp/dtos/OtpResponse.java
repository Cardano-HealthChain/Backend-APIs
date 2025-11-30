package com.cardano.healthchain.cardano.healthchain.utils.otp.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class OtpResponse {
    private String otpId;
    private String email;
    private String otpCode;
    private boolean used;
    private LocalDateTime expiresAt;
}
