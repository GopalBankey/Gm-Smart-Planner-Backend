package com.gmsmartplanner.service;

import com.gmsmartplanner.dto.request.*;
import com.gmsmartplanner.dto.response.LoginResponseDTO;

public interface AuthService {

    // SEND OTP
    String initiateMobileAuth(
            MobileAuthDTO dto
    );

    // VERIFY OTP LOGIN
    LoginResponseDTO verifyMobileOtp(
            AuthOtpVerifyDTO dto
    );

    // REFRESH TOKEN
    LoginResponseDTO refreshToken(
            RefreshTokenDTO dto
    );

    // LOGOUT
    void logout(
            String refreshToken
    );


    LoginResponseDTO googleLogin(
            GoogleLoginDTO dto
    );

    String resendOtp(
            ResendOtpRequestDTO dto
    );
}