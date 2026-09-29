package com.gmsmartplanner.mapper.health;

import com.gmsmartplanner.dto.response.health.InsuranceCardResponseDTO;
import com.gmsmartplanner.entity.health.InsuranceCard;
import org.springframework.stereotype.Component;

@Component
public class InsuranceCardMapper {

    // ==========================================
    // ENTITY -> RESPONSE DTO
    // ==========================================

    public InsuranceCardResponseDTO toResponse(
            InsuranceCard insuranceCard
    ) {

        return InsuranceCardResponseDTO.builder()

                // ==========================================
                // BASIC DETAILS
                // ==========================================

                .id(
                        insuranceCard.getId()
                )

                // ==========================================
                // INSURANCE / TPA DETAILS
                // ==========================================

                .insuranceTpaName(
                        insuranceCard.getInsuranceTpaName()
                )

                .cardInsuranceNumber(
                        insuranceCard.getCardInsuranceNumber()
                )

                .cardHolderName(
                        insuranceCard.getCardHolderName()
                )

                // ==========================================
                // VALID TILL
                // ==========================================

                .validTill(
                        insuranceCard.getValidTill()
                )

                // ==========================================
                // NOTES
                // ==========================================

                .notes(
                        insuranceCard.getNotes()
                )

                // ==========================================
                // CARD PHOTO
                // ==========================================

                .cardPhoto(
                        insuranceCard.getCardPhoto()
                )

                // ==========================================
                // ACTIVE STATUS
                // ==========================================

                .active(
                        insuranceCard.isActive()
                )

                .build();
    }
}