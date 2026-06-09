package com.gmsmartplanner.dto.response.health;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DoctorResponseDTO {

    private Long id;

    private String doctorName;

    private String countryCode;

    private String mobileNumber;

    private String specialization;
}