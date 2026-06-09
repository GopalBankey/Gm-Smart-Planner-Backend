package com.gmsmartplanner.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class EmailOtpVerifyDTO {

    @NotBlank(message = "OTP code cannot be blank")

    @Pattern(
            regexp = "^[0-9]{4}$",
            message = "OTP must be exactly 4 digits"
    )
    private String otp;
}