package com.gmsmartplanner.dto.request.health;

import com.gmsmartplanner.enums.health.MedicineForm;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Getter
@Setter
public class CreateExtraMedicineRequestDTO {

    @NotBlank
    private String medicineName;

    private String dosageStrength;

    @NotNull
    private MedicineForm form;

    private String purpose;

    private String pillColor;

    private MultipartFile pillPhoto;

    @NotNull
    @Min(1)
    private Integer count;

    @NotNull
    private LocalDate expiryDate;

    private String companyName;
}