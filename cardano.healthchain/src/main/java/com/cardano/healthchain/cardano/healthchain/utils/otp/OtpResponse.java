package com.cardano.healthchain.cardano.healthchain.utils.otp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class OtpResponse {
    private String opt_id;
    private String user_id;
    private boolean used;
    private LocalDateTime expires_at;
}
