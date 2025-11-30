package com.cardano.healthchain.cardano.healthchain.utils.otp;

import com.cardano.healthchain.cardano.healthchain.exceptions.OtpException;
import com.cardano.healthchain.cardano.healthchain.utils.MailService;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;

@Service
public class OtpService {
    private final OtpRepositoryI otpRepository;
    private final SecureRandom otpRandomGenerator;
    private final MailService mailService;
    public OtpService(OtpRepositoryI otpRepository, MailService mailService) {
        this.otpRepository = otpRepository;
        this.mailService = mailService;
        otpRandomGenerator = new SecureRandom("randomisation".getBytes());
    }
    public String generateOTP(int length){
        StringBuilder otp = new StringBuilder();
        for(int i = 0; i < length; i++){
            otp.append(otpRandomGenerator.nextInt(10));
        }
        return otp.toString();
    }
    public void checkOTPValidity(String otpcode, String user_email){
        OtpResponse otpByCode = otpRepository.getOtpByCodeAndEmail(otpcode, user_email);
        if(otpByCode.getExpires_at().isBefore(LocalDateTime.now()) ){
            throw new OtpException("OTP Expired");
        } else if (!otpByCode.getUser_id().equals(user_email)) {
            throw new OtpException("Invalid OTP");
        }
    }
    public void sendOtpMessageToEmail(String user_email, String otpcode){
        otpRepository.insertOtpForUser(user_email,otpcode);
        mailService.sendMail(
                user_email,
                "OTP CODE",
                String.format("click this link to verify otp: api/v1/user/otp/validate?otpcode=%s&user_email=%s", otpcode, user_email)
        );
    }
}
