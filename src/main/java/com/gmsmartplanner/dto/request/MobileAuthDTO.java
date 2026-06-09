package com.gmsmartplanner.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class MobileAuthDTO {

    @NotBlank(message = "Mobile number cannot be blank")

    @Pattern(
            regexp = "^[0-9]{10}$",
            message = "Mobile number must be exactly 10 digits"
    )
    private String mobileNumber;


    @NotBlank(message = "FCM token is required")
    private String fcmToken;
}