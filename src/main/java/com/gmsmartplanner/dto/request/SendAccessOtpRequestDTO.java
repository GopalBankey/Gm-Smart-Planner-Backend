package com.gmsmartplanner.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SendAccessOtpRequestDTO {

    // =====================================
    // MOBILE
    // =====================================

    @NotBlank(
            message =
                    "Mobile number is required"
    )
    private String mobileNumber;

    // =====================================
    // DISPLAY NAME
    // =====================================

    @NotBlank(
            message =
                    "Display name is required"
    )
    private String displayName;

    private String countryCode;
}