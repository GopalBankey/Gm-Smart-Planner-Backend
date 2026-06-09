package com.gmsmartplanner.dto.response.health;

import com.gmsmartplanner.enums.health.MedicineHistoryStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MedicineCardResponseDTO {

    private Long
            medicineId;

    private String
            medicineName;

    private String
            dosage;

    private String
            medicineForm;

    private Integer
            stock;

    private String
            mealType;

    private String
            time;

    private MedicineHistoryStatus status;

    private String
            purpose;

    private String
            pillColor;

    private String
            pillPhoto;

    private String
            companyName;

    private Long medicineScheduleId;
}