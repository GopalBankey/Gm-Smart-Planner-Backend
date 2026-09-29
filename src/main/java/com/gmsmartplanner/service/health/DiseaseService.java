package com.gmsmartplanner.service.health;

import com.gmsmartplanner.dto.request.health.CreateDiseaseRequestDTO;
import com.gmsmartplanner.dto.request.health.UpdateDiseaseRequestDTO;
import com.gmsmartplanner.dto.response.health.DiseaseResponseDTO;

import java.util.List;

public interface DiseaseService {

    DiseaseResponseDTO
    createDisease(
            String username,
            CreateDiseaseRequestDTO dto
    );

    List<DiseaseResponseDTO>
    getDiseases(
            String username
    );

    DiseaseResponseDTO
    getDisease(
            String username,
            Long diseaseId
    );

    DiseaseResponseDTO
    updateDisease(
            String username,
            Long diseaseId,
            UpdateDiseaseRequestDTO dto
    );

    void deleteDisease(
            String username,
            Long diseaseId
    );
}