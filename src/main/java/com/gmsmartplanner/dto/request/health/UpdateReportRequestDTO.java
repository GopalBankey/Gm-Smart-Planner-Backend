package com.gmsmartplanner.dto.request.health;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class UpdateReportRequestDTO {

    private String reportName;

    private String labName;

    private Long doctorId;

    private String countryCode;

    private String labMobileNumber;

    private String labAddress;

    private LocalDate reportDate;

    private LocalTime reportTime;

    private String notes;

    private MultipartFile file;
}