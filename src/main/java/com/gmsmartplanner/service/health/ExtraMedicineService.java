package com.gmsmartplanner.service.health;

import com.gmsmartplanner.dto.request.health.CreateExtraMedicineRequestDTO;
import com.gmsmartplanner.dto.request.health.UpdateExtraMedicineRequestDTO;
import com.gmsmartplanner.dto.response.health.ExtraMedicineResponseDTO;

import java.util.List;

public interface ExtraMedicineService {

    // =====================================
    // CREATE
    // =====================================

    ExtraMedicineResponseDTO createMedicine(

            String username,

            CreateExtraMedicineRequestDTO dto
    );

    // =====================================
    // GET ALL
    // =====================================

    List<ExtraMedicineResponseDTO>
    getMedicines(

            String username
    );

    // =====================================
    // GET BY ID
    // =====================================

    ExtraMedicineResponseDTO getMedicine(

            String username,

            Long medicineId
    );

    // =====================================
    // UPDATE
    // =====================================

    ExtraMedicineResponseDTO updateMedicine(

            String username,

            Long medicineId,

            UpdateExtraMedicineRequestDTO dto
    );

    // =====================================
    // DELETE
    // =====================================

    void deleteMedicine(

            String username,

            Long medicineId
    );
}