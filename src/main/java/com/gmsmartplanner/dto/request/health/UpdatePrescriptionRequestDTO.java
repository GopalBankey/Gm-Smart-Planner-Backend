package com.gmsmartplanner.dto.request.health;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Getter
@Setter
public class UpdatePrescriptionRequestDTO {

    private MultipartFile prescriptionPhoto;

    private LocalDate prescriptionDate;

    private Long doctorId;

    private Long hospitalId;

    private String relatedDisease;
}