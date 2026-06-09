package com.gmsmartplanner.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResendOtpRequestDTO {

    @NotBlank
    private String mobileNumber;
}