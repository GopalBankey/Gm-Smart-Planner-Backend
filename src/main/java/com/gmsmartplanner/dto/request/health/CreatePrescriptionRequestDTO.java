package com.gmsmartplanner.dto.request.health;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Getter
@Setter
public class CreatePrescriptionRequestDTO {

    // =====================================
    // PRESCRIPTION PHOTO
    // =====================================

    @NotNull(
            message = "Prescription photo is required"
    )
    private MultipartFile prescriptionPhoto;


    // =====================================
    // PRESCRIPTION DATE
    // =====================================

    @NotNull(
            message = "Prescription date is required"
    )
    private LocalDate prescriptionDate;


    // =====================================
    // DOCTOR
    // =====================================

    @NotNull(
            message = "Doctor is required"
    )
    private Long doctorId;


    // =====================================
    // HOSPITAL
    // =====================================

    @NotNull(
            message = "Hospital is required"
    )
    private Long hospitalId;


    // =====================================
    // RELATED DISEASE
    // =====================================

    private String relatedDisease;
}