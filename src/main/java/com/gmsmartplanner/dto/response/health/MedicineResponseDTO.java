package com.gmsmartplanner.dto.response.health;

import com.gmsmartplanner.enums.health.MealType;
import com.gmsmartplanner.enums.health.MedicineForm;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
public class MedicineResponseDTO {

    private Long id;

    private String medicineName;

    private String dosage;

    private MedicineForm form;

    private String purpose;

    private MealType mealType;

    private LocalDate startDate;

    private LocalDate endDate;

    private String pillColor;

    private String pillPhoto;

    private String companyName;

    private Long doctorId;

    private String doctorName;

    private Long hospitalId;

    private String hospitalName;

    private String hospitalAddress;


    private Integer currentStock;

    private Integer totalConsumed;

    private LocalDate expiryDate;

    private boolean refillReminder;

    private String notes;

    private List<MedicineScheduleResponseDTO>
            schedules;
}