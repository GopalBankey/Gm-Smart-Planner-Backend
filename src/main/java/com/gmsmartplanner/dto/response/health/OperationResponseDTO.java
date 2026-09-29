package com.gmsmartplanner.dto.response.health;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
public class OperationResponseDTO {

    private Long id;

    private String operationName;

    private LocalDate date;

    private DoctorResponseDTO doctor;

    private HospitalResponseDTO hospital;

    private String reason;

    private List<String> relatedDocuments;

    private boolean active;
}