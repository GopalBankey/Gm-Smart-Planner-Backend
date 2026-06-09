package com.gmsmartplanner.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class AuthOtpVerifyDTO {

    @NotBlank(
            message = "Mobile number is required"
    )
    @Pattern(
            regexp = "^[0-9]{10}$",
            message = "Mobile number must be exactly 10 digits"
    )
    private String mobileNumber;

    @NotBlank(
            message = "OTP is required"
    )
    @Pattern(
            regexp = "^[0-9]{4}$",
            message = "OTP must be exactly 4 digits"
    )
    private String otp;
}