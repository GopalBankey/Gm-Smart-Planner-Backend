package com.gmsmartplanner.service.health;

import com.gmsmartplanner.dto.request.health.CreateAllergyRequestDTO;
import com.gmsmartplanner.dto.request.health.UpdateAllergyRequestDTO;
import com.gmsmartplanner.dto.response.health.AllergyResponseDTO;

import java.util.List;

public interface AllergyService {

    AllergyResponseDTO
    createAllergy(
            String username,
            CreateAllergyRequestDTO dto
    );

    List<AllergyResponseDTO>
    getAllergies(
            String username
    );

    AllergyResponseDTO
    getAllergy(
            String username,
            Long allergyId
    );

    AllergyResponseDTO
    updateAllergy(
            String username,
            Long allergyId,
            UpdateAllergyRequestDTO dto
    );

    void deleteAllergy(
            String username,
            Long allergyId
    );
}