package com.gmsmartplanner.dto.response.health;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Builder
public class ReportResponseDTO {

    private Long id;

    private String reportName;

    private Long doctorId;

    private String doctorName;

    private String labName;

    private String countryCode;

    private String labMobileNumber;

    private String labAddress;

    private LocalDate reportDate;

    private LocalTime reportTime;

    private String notes;

    private String reportFile;
}