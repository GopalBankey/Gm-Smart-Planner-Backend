package com.gmsmartplanner.dto.response.health;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class PrescriptionResponseDTO {

    private Long id;

    private String prescriptionPhoto;

    private LocalDate prescriptionDate;

    private DoctorResponseDTO doctor;

    private HospitalResponseDTO hospital;

    private String relatedDisease;

    private boolean active;
}