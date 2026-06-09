package com.gmsmartplanner.dto.response.health;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalTime;

@Getter
@Builder
public class HospitalResponseDTO {

    private Long id;

    private String hospitalName;

    private String countryCode;

    private String mobileNumber;

    private String openingTime;

    private String closingTime;

    private String address;
}