package com.gmsmartplanner.service;

public interface EmailService {

    // =========================================
    // SEND EMAIL OTP
    // =========================================
    void sendOtpEmail(

            String toEmail,

            String otp
    );
}