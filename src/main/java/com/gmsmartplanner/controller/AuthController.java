package com.gmsmartplanner.controller;

import com.gmsmartplanner.dto.request.*;
import com.gmsmartplanner.dto.response.LoginResponseDTO;
import com.gmsmartplanner.payload.ApiResponse;
import com.gmsmartplanner.service.AuthService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(
        value = "/api/v1/auth",
        produces = MediaType.APPLICATION_JSON_VALUE
)
@Slf4j
public class AuthController {

    private final AuthService authService;

    // =========================================
    // MOBILE LOGIN
    // =========================================
    @PostMapping("/mobile/login")
    public ResponseEntity<ApiResponse<String>>
    mobileLogin(

            @Valid
            @RequestBody
            MobileAuthDTO dto

    ) {

        log.info(
                "Mobile login initiated for mobile : {}",
                dto.getMobileNumber()
        );

        String otp =
                authService.initiateMobileAuth(dto);

        return ResponseEntity.status(
                HttpStatus.OK
        ).body(

                ApiResponse.<String>builder()
                        .success(true)
                        .message(
                                "OTP sent successfully"
                        )
                        .data(otp)
                        .build()
        );
    }

    // =========================================
    // VERIFY MOBILE LOGIN OTP
    // =========================================
    @PostMapping("/mobile/verify")
    public ResponseEntity<ApiResponse<LoginResponseDTO>>
    verifyMobileLoginOtp(

            @Valid
            @RequestBody
            AuthOtpVerifyDTO dto

    ) {

        log.info(
                "Verifying login OTP for mobile : {}",
                dto.getMobileNumber()
        );

        LoginResponseDTO response =
                authService.verifyMobileOtp(dto);

        return ResponseEntity.status(
                HttpStatus.OK
        ).body(

                ApiResponse.<LoginResponseDTO>builder()
                        .success(true)
                        .message(
                                "Mobile verified successfully"
                        )
                        .data(response)
                        .build()
        );
    }

    // =========================================
    // GOOGLE LOGIN
    // =========================================
    @PostMapping("/google/login")
    public ResponseEntity<ApiResponse<LoginResponseDTO>>
    googleLogin(

            @Valid
            @RequestBody
            GoogleLoginDTO dto

    ) {

        log.info(
                "Google login initiated for email : {}",
                dto.getEmail()
        );

        LoginResponseDTO response =
                authService.googleLogin(dto);

        return ResponseEntity.status(
                HttpStatus.OK
        ).body(

                ApiResponse.<LoginResponseDTO>builder()
                        .success(true)
                        .message(
                                "Google login successful"
                        )
                        .data(response)
                        .build()
        );
    }
// =========================================
// REFRESH TOKEN
// =========================================
    @PostMapping("/refresh-token")
    public ResponseEntity<ApiResponse<LoginResponseDTO>>
    refreshToken(

            @Valid
            @RequestBody
            RefreshTokenDTO dto

    ) {

        log.info(
                "Refreshing access token"
        );

        LoginResponseDTO response =
                authService.refreshToken(
                        dto
                );

        return ResponseEntity.status(
                HttpStatus.OK
        ).body(

                ApiResponse.<LoginResponseDTO>builder()
                        .success(true)
                        .message(
                                "Token refreshed successfully"
                        )
                        .data(response)
                        .build()
        );
    }

    // =========================================
    // LOGOUT
    // =========================================
    // =========================================
// LOGOUT
// =========================================

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<String>>
    logout(

            @Valid
            @RequestBody
            LogoutRequestDTO dto

    ) {

        log.info(
                "Logout initiated"
        );

        authService.logout(
                dto.getRefreshToken()
        );

        return ResponseEntity.status(
                HttpStatus.OK
        ).body(

                ApiResponse.<String>builder()

                        .success(true)

                        .message(
                                "Logout successful"
                        )

                        .data(
                                "Logged out successfully"
                        )

                        .build()
        );
    }

    // =========================================
// RESEND OTP
// =========================================

    @PostMapping(
            "/mobile/resend-otp"
    )

    public ResponseEntity<
            ApiResponse<String>
            >

    resendOtp(

            @Valid
            @RequestBody
            ResendOtpRequestDTO dto

    ) {

        return ResponseEntity.ok(

                ApiResponse

                        .<String>
                                builder()

                        .success(
                                true
                        )

                        .message(
                                "OTP resent successfully"
                        )

                        .data(

                                authService
                                        .resendOtp(
                                                dto
                                        )
                        )

                        .build()
        );
    }
}