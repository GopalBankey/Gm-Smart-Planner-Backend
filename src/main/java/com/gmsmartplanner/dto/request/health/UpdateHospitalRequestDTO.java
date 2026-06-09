package com.gmsmartplanner.dto.request.health;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
public class UpdateHospitalRequestDTO {

    private String hospitalName;

    private String countryCode;

    private String mobileNumber;

    private String openingTime;

    private String closingTime;

    private String address;
}