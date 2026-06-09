package com.gmsmartplanner.dto.request.health;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class UpdateMedicineRefillReminderRequestDTO {

    // =====================================
    // ADDED STOCK
    // =====================================

    private Integer addedStock;

    // =====================================
    // REMINDER DATE
    // =====================================

    private LocalDate reminderDate;

    // =====================================
    // REMINDER TIME
    // =====================================

    private LocalTime reminderTime;
}