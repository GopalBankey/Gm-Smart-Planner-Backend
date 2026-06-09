package com.gmsmartplanner.dto.response.health;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RefillSoonMedicineResponseDTO {

    private Long
            medicineId;

    private String
            medicineName;

    private String
            pillPhoto;

    private Integer
            currentStock;

    private Integer
            remainingDose;
}