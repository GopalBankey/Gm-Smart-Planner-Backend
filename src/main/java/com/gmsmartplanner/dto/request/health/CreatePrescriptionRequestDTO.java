package com.gmsmartplanner.dto.request.health;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Getter
@Setter
public class CreatePrescriptionRequestDTO {

    private MultipartFile prescriptionPhoto;

    @NotNull
    private LocalDate prescriptionDate;

    @NotNull
    private Long doctorId;

    @NotNull
    private Long hospitalId;

    private String relatedDisease;
}