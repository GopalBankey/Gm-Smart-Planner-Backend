package com.gmsmartplanner.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VerifyAccessOtpRequestDTO {

    // =====================================
    // MOBILE
    // =====================================

    @NotBlank(
            message =
                    "Mobile number is required"
    )
    private String mobileNumber;

    // =====================================
    // OTP
    // =====================================

    @NotBlank(
            message =
                    "OTP is required"
    )
    private String otp;
}