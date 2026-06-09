package com.gmsmartplanner.dto.response.health;

import com.gmsmartplanner.enums.health.MedicineForm;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class ExtraMedicineResponseDTO {

    private Long id;

    private String medicineName;

    private String dosageStrength;

    private MedicineForm form;

    private String purpose;

    private String pillColor;

    private String pillPhoto;

    private Integer count;

    private LocalDate expiryDate;

    private String companyName;
}