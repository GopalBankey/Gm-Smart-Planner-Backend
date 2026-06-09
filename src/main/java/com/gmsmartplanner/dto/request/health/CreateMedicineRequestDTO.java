package com.gmsmartplanner.dto.request.health;

import com.gmsmartplanner.enums.health.MealType;
import com.gmsmartplanner.enums.health.MedicineForm;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class CreateMedicineRequestDTO {

    @NotBlank
    private String medicineName;

    @NotBlank
    private String dosage;

    @NotNull
    private MedicineForm form;

    private String purpose;

    private MealType mealType;

    @NotNull
    private LocalDate startDate;

    private LocalDate endDate;

    private String pillColor;

    private MultipartFile pillPhoto;

    private String companyName;

    private Long doctorId;

    private Long hospitalId;

    private Integer currentStock;

    private LocalDate expiryDate;

    private boolean refillReminder;

    private String notes;

    private List<MedicineScheduleRequestDTO>
            schedules;
}