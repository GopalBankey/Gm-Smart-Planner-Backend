package com.gmsmartplanner.dto.request.health;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class CreateReportRequestDTO {

    @NotBlank
    private String reportName;

    private String labName;

    private Long doctorId;

    private String countryCode;

    private String labMobileNumber;

    private String labAddress;

    @NotNull
    private LocalDate reportDate;

    @NotNull
    private LocalTime reportTime;

    private String notes;

    // SINGLE FILE

    @NotNull
    private MultipartFile file;
}