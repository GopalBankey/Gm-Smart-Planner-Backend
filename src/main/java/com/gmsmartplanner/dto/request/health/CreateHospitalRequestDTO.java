package com.gmsmartplanner.dto.request.health;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
public class CreateHospitalRequestDTO {

    @NotBlank
    private String hospitalName;

    private String countryCode;

    @NotBlank
    private String mobileNumber;

    @NotNull
    private String openingTime;

    @NotNull
    private String closingTime;

    @NotBlank
    private String address;
}