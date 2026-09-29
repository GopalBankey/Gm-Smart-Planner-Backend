package com.gmsmartplanner.dto.request.health;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Getter
@Setter
public class CreateInsuranceCardRequestDTO {

    // ==========================================
    // INSURANCE / TPA NAME
    // ==========================================

    @NotBlank(
            message = "Insurance / TPA name is required"
    )
    private String insuranceTpaName;


    // ==========================================
    // CARD / INSURANCE NUMBER
    // ==========================================

    @NotBlank(
            message = "Card / Insurance number is required"
    )
    private String cardInsuranceNumber;


    // ==========================================
    // CARD HOLDER NAME
    // ==========================================

    @NotBlank(
            message = "Card holder name is required"
    )
    private String cardHolderName;


    // ==========================================
    // VALID TILL
    // ==========================================

    @NotNull(
            message = "Valid till is required"
    )
    @DateTimeFormat(
            pattern = "yyyy-MM-dd"
    )
    private LocalDate validTill;


    // ==========================================
    // NOTES
    // ==========================================

    private String notes;


    // ==========================================
    // CARD PHOTO
    // ==========================================

    private MultipartFile cardPhoto;
}