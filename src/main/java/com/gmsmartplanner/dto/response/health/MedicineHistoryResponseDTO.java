package com.gmsmartplanner.dto.response.health;

import com.gmsmartplanner.enums.health.MedicineHistoryAction;
import com.gmsmartplanner.enums.health.MedicineHistoryStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@Builder
public class MedicineHistoryResponseDTO {

    private Long id;

    // =====================================
    // MEDICINE
    // =====================================

    private Long medicineId;

    private List<
            MedicineScheduleResponseDTO
            >
            schedules;

    private String medicineName;

    private String companyName;

    private String dosage;

    private String medicineForm;

    private String mealType;

    private String pillPhoto;

    // =====================================
    // ACTION
    // =====================================

    private MedicineHistoryAction action;

    private MedicineHistoryStatus status;

    // =====================================
    // DATE TIME
    // =====================================

    private LocalDate date;

    private LocalTime time;

    // =====================================
    // STOCK
    // =====================================

    private Integer previousStock;

    private Integer currentStock;

    private Integer consumedDose;

    // =====================================
    // SNAPSHOT
    // =====================================

    private String doctorName;

    private String hospitalName;

    // =====================================
    // CHANGE
    // =====================================

    private String oldData;

    private String newData;

    // =====================================
    // NOTE
    // =====================================

    private String note;
}