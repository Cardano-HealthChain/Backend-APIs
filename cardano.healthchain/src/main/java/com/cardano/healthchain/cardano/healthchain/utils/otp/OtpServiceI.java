package com.cardano.healthchain.cardano.healthchain.utils.otp;

public interface OtpServiceI {
    public String generateOTP(int length);
    public void checkOTPValidity(String otpcode, String user_email);
    public void sendOtpMessageToEmail(String user_email, String otpcode);
}
