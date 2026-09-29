package com.gmsmartplanner.service.health;

import com.gmsmartplanner.dto.request.health.CreatePrescriptionRequestDTO;
import com.gmsmartplanner.dto.request.health.UpdatePrescriptionRequestDTO;
import com.gmsmartplanner.dto.response.health.PrescriptionResponseDTO;

import java.util.List;

public interface PrescriptionService {

    PrescriptionResponseDTO
    createPrescription(
            String username,
            CreatePrescriptionRequestDTO dto
    );

    List<PrescriptionResponseDTO>
    getPrescriptions(
            String username
    );

    PrescriptionResponseDTO
    getPrescription(
            String username,
            Long prescriptionId
    );

    PrescriptionResponseDTO
    updatePrescription(
            String username,
            Long prescriptionId,
            UpdatePrescriptionRequestDTO dto
    );

    void deletePrescription(
            String username,
            Long prescriptionId
    );
}