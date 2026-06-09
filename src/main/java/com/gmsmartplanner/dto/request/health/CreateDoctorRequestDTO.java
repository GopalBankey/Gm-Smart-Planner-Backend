package com.gmsmartplanner.dto.request.health;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateDoctorRequestDTO {

    @NotBlank(
            message = "Doctor name is required"
    )
    private String doctorName;

    private String countryCode;

    @NotBlank(
            message = "Mobile number is required"
    )
    private String mobileNumber;

    @NotBlank(
            message = "Specialization is required"
    )
    private String specialization;
}