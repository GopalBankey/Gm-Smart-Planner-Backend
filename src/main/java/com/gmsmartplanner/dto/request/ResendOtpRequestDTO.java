package com.gmsmartplanner.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResendOtpRequestDTO {

    private String mobileNumber;

    private String email;
}