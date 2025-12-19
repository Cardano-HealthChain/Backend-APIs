package com.cardano.healthchain.cardano.healthchain.utils.otp;

import com.cardano.healthchain.cardano.healthchain.utils.otp.dtos.OtpDataResponse;

public interface OtpRepositoryI {
    OtpDataResponse getOtpByCodeAndEmail(String code, String user_email);
    void insertOtpForUser(String user_id, String code);
}
