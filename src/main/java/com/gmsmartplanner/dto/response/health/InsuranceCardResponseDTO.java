package com.gmsmartplanner.dto.response.health;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class InsuranceCardResponseDTO {

    // ==========================================
    // PRIMARY KEY
    // ==========================================

    private Long id;


    // ==========================================
    // INSURANCE / TPA DETAILS
    // ==========================================

    private String insuranceTpaName;

    private String cardInsuranceNumber;

    private String cardHolderName;


    // ==========================================
    // VALID TILL
    // ==========================================

    @JsonFormat(
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

    private String cardPhoto;


    // ==========================================
    // ACTIVE STATUS
    // ==========================================

    private boolean active;
}