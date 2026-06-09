package com.gmsmartplanner.service;

import com.gmsmartplanner.dto.request.EmailOtpVerifyDTO;
import com.gmsmartplanner.dto.request.MobileOnboardingDTO;
import com.gmsmartplanner.dto.request.OtpVerifyDTO;
import com.gmsmartplanner.dto.request.UpdateProfileDTO;
import com.gmsmartplanner.entity.User;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {

    // =========================================
    // START ONBOARDING
    // =========================================
    String startOnboarding(

            String username,

            MobileOnboardingDTO dto,

            MultipartFile imageFile
    );

    // =========================================
    // VERIFY MOBILE OTP
    // =========================================
    User verifyMobileOtp(

            String username,

            OtpVerifyDTO dto
    );

    // =========================================
    // VERIFY EMAIL OTP
    // =========================================
    User verifyEmailOtp(

            String username,

            EmailOtpVerifyDTO dto
    );

    // =========================================
    // GET LOGGED-IN USER
    // =========================================
    User getLoggedInUser(

            String username
    );

    // =========================================
    // UPDATE PROFILE
    // =========================================
    User updateProfile(

            String username,

            UpdateProfileDTO dto,

            MultipartFile imageFile
    );
}