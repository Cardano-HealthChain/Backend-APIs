package com.cardano.healthchain.cardano.healthchain.utils.otp;

import com.cardano.healthchain.cardano.healthchain.utils.otp.dtos.OtpResponse;

public interface OtpRepositoryI {
    OtpResponse getOtpByCodeAndEmail(String code, String user_email);
    void insertOtpForUser(String user_id, String code);
}
