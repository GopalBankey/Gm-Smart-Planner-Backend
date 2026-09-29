package com.gmsmartplanner.dto.response.health;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
public class DiseaseResponseDTO {

    private Long id;

    private String diseaseName;

    private LocalDate diseaseSince;

    private List<MedicineResponseDTO> medicines;

    private DoctorResponseDTO doctor;

    private HospitalResponseDTO hospital;

    private String referencePerson;

    private boolean active;
}