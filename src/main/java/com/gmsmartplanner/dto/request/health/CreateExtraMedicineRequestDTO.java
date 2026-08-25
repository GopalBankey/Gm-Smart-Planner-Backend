package com.gmsmartplanner.dto.request.health;

import com.gmsmartplanner.enums.health.MedicineForm;
import jakarta.validation.constraints.NotBlank;
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

    @NotBlank
    private MedicineForm form;

    private String purpose;

    private String pillColor;

    private MultipartFile pillPhoto;

    @NotBlank
    private Integer count;

    @NotBlank
    private LocalDate expiryDate;

    private String companyName;
}