package com.gmsmartplanner.service.health;

import com.gmsmartplanner.dto.request.health.CreateMedicineRequestDTO;
import com.gmsmartplanner.dto.request.health.UpdateMedicineRequestDTO;
import com.gmsmartplanner.dto.response.health.MedicineResponseDTO;

import java.util.List;

public interface MedicineService {

    // =====================================
    // CREATE
    // =====================================

    MedicineResponseDTO createMedicine(

            String username,

            CreateMedicineRequestDTO dto
    );

    // =====================================
    // GET ALL
    // =====================================

    List<MedicineResponseDTO>
    getMedicines(

            String username
    );

    // =====================================
    // GET BY ID
    // =====================================

    MedicineResponseDTO getMedicine(

            String username,

            Long medicineId
    );

    // =====================================
    // UPDATE
    // =====================================

    MedicineResponseDTO updateMedicine(

            String username,

            Long medicineId,

            UpdateMedicineRequestDTO dto
    );

    // =====================================
    // DELETE
    // =====================================

    void deleteMedicine(

            String username,

            Long medicineId
    );
}