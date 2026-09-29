package com.gmsmartplanner.dto.request.health;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class CreateDiseaseRequestDTO {

    @NotBlank
    private String diseaseName;

    @NotNull
    private LocalDate diseaseSince;

    private List<Long> medicineIds;

    private Long doctorId;

    private Long hospitalId;

    private String referencePerson;
}