package com.gmsmartplanner.dto.response.health;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Builder
public class MedicineRefillReminderResponseDTO {

    // =====================================
    // MEDICINE
    // =====================================

    private Long medicineId;

    private String medicineName;

    private Integer currentStock;

    // =====================================
    // REMINDER
    // =====================================

    private LocalDate nextReminderDate;

    private LocalTime nextReminderTime;
}