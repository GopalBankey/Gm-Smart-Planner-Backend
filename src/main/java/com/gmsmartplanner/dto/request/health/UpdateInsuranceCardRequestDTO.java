package com.gmsmartplanner.dto.request.health;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Getter
@Setter
public class UpdateInsuranceCardRequestDTO {

    // ==========================================
    // INSURANCE / TPA NAME
    // ==========================================

    private String insuranceTpaName;


    // ==========================================
    // CARD / INSURANCE NUMBER
    // ==========================================

    private String cardInsuranceNumber;


    // ==========================================
    // CARD HOLDER NAME
    // ==========================================

    private String cardHolderName;


    // ==========================================
    // VALID TILL
    // ==========================================

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