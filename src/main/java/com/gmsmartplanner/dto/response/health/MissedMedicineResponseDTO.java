package com.gmsmartplanner.dto.response.health;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MissedMedicineResponseDTO {

    // =====================================
    // MEDICINE
    // =====================================

    private Long
            medicineId;

    private Long
            medicineScheduleId;

    private String
            medicineName;

    private String
            pillPhoto;

    // =====================================
    // TIME
    // =====================================

    private String
            scheduledTime;
}