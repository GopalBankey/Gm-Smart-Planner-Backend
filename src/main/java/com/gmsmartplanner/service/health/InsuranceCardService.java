package com.gmsmartplanner.service.health;

import com.gmsmartplanner.dto.request.health.CreateInsuranceCardRequestDTO;
import com.gmsmartplanner.dto.request.health.UpdateInsuranceCardRequestDTO;
import com.gmsmartplanner.dto.response.health.InsuranceCardResponseDTO;

import java.util.List;

public interface InsuranceCardService {

    // ==========================================
    // CREATE
    // ==========================================

    InsuranceCardResponseDTO createInsuranceCard(
            String username,
            CreateInsuranceCardRequestDTO dto
    );


    // ==========================================
    // GET ALL
    // ==========================================

    List<InsuranceCardResponseDTO> getInsuranceCards(
            String username
    );


    // ==========================================
    // GET BY ID
    // ==========================================

    InsuranceCardResponseDTO getInsuranceCard(
            String username,
            Long insuranceCardId
    );


    // ==========================================
    // UPDATE
    // ==========================================

    InsuranceCardResponseDTO updateInsuranceCard(
            String username,
            Long insuranceCardId,
            UpdateInsuranceCardRequestDTO dto
    );


    // ==========================================
    // DELETE
    // ==========================================

    void deleteInsuranceCard(
            String username,
            Long insuranceCardId
    );
}