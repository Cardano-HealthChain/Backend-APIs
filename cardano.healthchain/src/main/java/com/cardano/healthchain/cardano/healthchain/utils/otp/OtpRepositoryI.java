package com.cardano.healthchain.cardano.healthchain.utils.otp;

public interface OtpRepositoryI {
    OtpResponse getOtpByCodeAndEmail(String code, String user_email);
    void insertOtpForUser(String user_id, String code);
}
