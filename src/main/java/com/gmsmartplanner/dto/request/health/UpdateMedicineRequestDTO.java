package com.gmsmartplanner.dto.request.health;

import com.gmsmartplanner.enums.health.MealType;
import com.gmsmartplanner.enums.health.MedicineForm;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class UpdateMedicineRequestDTO {

    private String medicineName;

    private String dosage;

    private MedicineForm form;

    private String purpose;

    private MealType mealType;

    private LocalDate startDate;

    private LocalDate endDate;

    private String pillColor;

    private MultipartFile pillPhoto;

    private String companyName;

    private Long doctorId;

    private Long hospitalId;

    private Integer currentStock;

    private LocalDate expiryDate;

    private Boolean refillReminder;

    private String notes;

    private List<MedicineScheduleRequestDTO>
            schedules;
}