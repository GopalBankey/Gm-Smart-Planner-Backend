package com.gmsmartplanner.dto.request.health;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateDoctorRequestDTO {

    private String doctorName;

    private String countryCode;

    private String mobileNumber;

    private String specialization;
}