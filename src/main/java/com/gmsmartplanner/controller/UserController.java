package com.gmsmartplanner.controller;

import com.gmsmartplanner.dto.request.EmailOtpVerifyDTO;
import com.gmsmartplanner.dto.request.MobileOnboardingDTO;
import com.gmsmartplanner.dto.request.OtpVerifyDTO;
import com.gmsmartplanner.dto.request.UpdateProfileDTO;
import com.gmsmartplanner.entity.User;
import com.gmsmartplanner.payload.ApiResponse;
import com.gmsmartplanner.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(
        value = "/api/v1/user",
        produces = MediaType.APPLICATION_JSON_VALUE
)
@Slf4j
public class UserController {

    private final UserService
            userService;

    // =========================================
    // START ONBOARDING
    // =========================================
    @PostMapping(
            value = "/onboarding/start",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<ApiResponse<String>>
    startOnboarding(

            Authentication authentication,

            @Valid
            @ModelAttribute
            MobileOnboardingDTO dto,

            @RequestPart(
                    required = false
            )
            MultipartFile imageFile

    ) {

        String username =
                authentication.getName();

        log.info(
                "Starting onboarding for user : {}",
                username
        );

        String response =
                userService.startOnboarding(

                        username,

                        dto,

                        imageFile
                );

        return ResponseEntity.status(
                HttpStatus.OK
        ).body(

                ApiResponse.<String>builder()

                        .success(true)

                        .message(
                                "Onboarding started successfully"
                        )

                        .data(response)

                        .build()
        );
    }

    // =========================================
    // VERIFY MOBILE OTP
    // =========================================
    @PostMapping("/verify-mobile-otp")
    public ResponseEntity<ApiResponse<User>>
    verifyMobileOtp(

            Authentication authentication,

            @Valid
            @RequestBody
            OtpVerifyDTO dto

    ) {

        String username =
                authentication.getName();

        log.info(
                "Verifying mobile OTP for user : {}",
                username
        );

        User user =
                userService.verifyMobileOtp(

                        username,

                        dto
                );

        return ResponseEntity.status(
                HttpStatus.OK
        ).body(

                ApiResponse.<User>builder()

                        .success(true)

                        .message(
                                "Mobile verified successfully"
                        )

                        .data(user)

                        .build()
        );
    }

    // =========================================
    // VERIFY EMAIL OTP
    // =========================================
    @PostMapping("/verify-email-otp")
    public ResponseEntity<ApiResponse<User>>
    verifyEmailOtp(

            Authentication authentication,

            @Valid
            @RequestBody
            EmailOtpVerifyDTO dto

    ) {

        String username =
                authentication.getName();

        log.info(
                "Verifying email OTP for user : {}",
                username
        );

        User user =
                userService.verifyEmailOtp(

                        username,

                        dto
                );

        return ResponseEntity.status(
                HttpStatus.OK
        ).body(

                ApiResponse.<User>builder()

                        .success(true)

                        .message(
                                "Email verified successfully"
                        )

                        .data(user)

                        .build()
        );
    }

    // =========================================
    // GET LOGGED-IN USER
    // =========================================
    @GetMapping("/me")
    public ResponseEntity<ApiResponse<User>>
    getLoggedInUser(

            Authentication authentication

    ) {

        String username =
                authentication.getName();

        log.info(
                "Fetching logged-in user : {}",
                username
        );

        User user =
                userService.getLoggedInUser(
                        username
                );

        return ResponseEntity.status(
                HttpStatus.OK
        ).body(

                ApiResponse.<User>builder()

                        .success(true)

                        .message(
                                "User fetched successfully"
                        )

                        .data(user)

                        .build()
        );
    }

    // =========================================
    // UPDATE PROFILE
    // =========================================
    @PatchMapping(
            value = "/profile",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<ApiResponse<User>>
    updateProfile(

            Authentication authentication,

            @ModelAttribute
            UpdateProfileDTO dto,

            @RequestPart(
                    required = false
            )
            MultipartFile imageFile

    ) {

        String username =
                authentication.getName();

        log.info(
                "Updating profile for user : {}",
                username
        );

        User updatedUser =
                userService.updateProfile(

                        username,

                        dto,

                        imageFile
                );

        return ResponseEntity.status(
                HttpStatus.OK
        ).body(

                ApiResponse.<User>builder()

                        .success(true)

                        .message(
                                "Profile updated successfully"
                        )

                        .data(updatedUser)

                        .build()
        );
    }
}