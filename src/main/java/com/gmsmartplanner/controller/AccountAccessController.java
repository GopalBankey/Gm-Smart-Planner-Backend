package com.gmsmartplanner.controller;

import com.gmsmartplanner.dto.request.SendAccessOtpRequestDTO;
import com.gmsmartplanner.dto.request.UpdateAccessPermissionRequestDTO;
import com.gmsmartplanner.dto.request.VerifyAccessOtpRequestDTO;
import com.gmsmartplanner.dto.response.AccountAccessResponseDTO;
import com.gmsmartplanner.dto.response.OwnerAccessResponseDTO;
import com.gmsmartplanner.payload.ApiResponse;
import com.gmsmartplanner.service.AccountAccessService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(
        value =
                "/api/v1/access",

        produces =
                MediaType.APPLICATION_JSON_VALUE
)
@Slf4j
public class
AccountAccessController {

    private final AccountAccessService
            service;

    // =====================================
    // SEND OTP
    // =====================================

    @PostMapping(
            "/send-otp"
    )

    public ResponseEntity<
            ApiResponse<AccountAccessResponseDTO>
            >

    sendOtp(

            Authentication auth,

            @Valid
            @RequestBody
            SendAccessOtpRequestDTO dto

    ) {

        return ResponseEntity.status(

                HttpStatus.OK

        ).body(

                ApiResponse

                        .<AccountAccessResponseDTO>
                                builder()

                        .success(
                                true
                        )

                        .message(
                                "OTP sent successfully"
                        )

                        .data(

                                service
                                        .sendOtp(

                                                auth.getName(),

                                                dto
                                        )
                        )

                        .build()
        );
    }

    // =====================================
    // RESEND OTP
    // =====================================

    @PostMapping(
            "/resend-otp"
    )

    public ResponseEntity<
            ApiResponse<AccountAccessResponseDTO>
            >

    resendOtp(

            Authentication auth,

            @RequestParam
            String mobileNumber

    ) {

        return ResponseEntity.ok(

                ApiResponse

                        .<AccountAccessResponseDTO>
                                builder()

                        .success(
                                true
                        )

                        .message(
                                "OTP resent successfully"
                        )

                        .data(

                                service
                                        .resendOtp(

                                                auth.getName(),

                                                mobileNumber
                                        )
                        )

                        .build()
        );
    }

    // =====================================
    // VERIFY OTP
    // =====================================

    @PostMapping(
            "/verify"
    )

    public ResponseEntity<
            ApiResponse<AccountAccessResponseDTO>
            >

    verifyOtp(

            Authentication auth,

            @Valid
            @RequestBody
            VerifyAccessOtpRequestDTO dto

    ) {

        return ResponseEntity.ok(

                ApiResponse

                        .<AccountAccessResponseDTO>
                                builder()

                        .success(
                                true
                        )

                        .message(
                                "Access verified"
                        )

                        .data(

                                service
                                        .verifyOtp(

                                                auth.getName(),

                                                dto
                                        )
                        )

                        .build()
        );
    }

    // =====================================
    // UPDATE
    // =====================================

    @PutMapping(
            "/{id}/permission"
    )

    public ResponseEntity<
            ApiResponse<AccountAccessResponseDTO>
            >

    update(

            Authentication auth,

            @PathVariable
            Long id,

            @RequestBody
            UpdateAccessPermissionRequestDTO dto

    ) {

        return ResponseEntity.ok(

                ApiResponse

                        .<AccountAccessResponseDTO>
                                builder()

                        .success(
                                true
                        )

                        .message(
                                "Permission updated"
                        )

                        .data(

                                service
                                        .updatePermission(

                                                auth.getName(),

                                                id,

                                                dto
                                        )
                        )

                        .build()
        );
    }


    // =====================================
// OWNER ACCESS
// =====================================

    @GetMapping(
            "/owner"
    )

    public ResponseEntity<
            ApiResponse<
                    OwnerAccessResponseDTO
                    >
            >

    getOwnerAccess(

            Authentication auth

    ) {

        return ResponseEntity.ok(

                ApiResponse

                        .<OwnerAccessResponseDTO>
                                builder()

                        .success(
                                true
                        )

                        .message(
                                "Owner access fetched"
                        )

                        .data(

                                service
                                        .getOwnerAccess(

                                                auth.getName()
                                        )
                        )

                        .build()
        );
    }


    // =====================================
    // LIST
    // =====================================

    @GetMapping

    public ResponseEntity<
            ApiResponse<
                    List<
                            AccountAccessResponseDTO
                            >
                    >
            >

    list(

            Authentication auth

    ) {

        return ResponseEntity.ok(

                ApiResponse

                        .<List<AccountAccessResponseDTO>>
                                builder()

                        .success(
                                true
                        )

                        .message(
                                "Access fetched"
                        )

                        .data(

                                service
                                        .getMyAccess(

                                                auth.getName()
                                        )
                        )

                        .build()
        );
    }

    // =====================================
    // REMOVE
    // =====================================

    @DeleteMapping(
            "/{id}"
    )

    public ResponseEntity<
            ApiResponse<String>
            >

    remove(

            Authentication auth,

            @PathVariable
            Long id

    ) {

        service.removeAccess(

                auth.getName(),

                id
        );

        return ResponseEntity.ok(

                ApiResponse

                        .<String>
                                builder()

                        .success(
                                true
                        )

                        .message(
                                "Access removed"
                        )

                        .data(
                                "Removed"
                        )

                        .build()
        );
    }
}