package com.gmsmartplanner.service.health;

import com.gmsmartplanner.dto.response.health.MedicineHistoryResponseDTO;

import java.util.List;

public interface MedicineHistoryService {

    // =====================================
    // TAKE
    // =====================================

    MedicineHistoryResponseDTO
    takeMedicine(

            String username,

            Long medicineId,

            Long scheduleId
    );

    // =====================================
    // SKIP
    // =====================================

    MedicineHistoryResponseDTO
    skipMedicine(

            String username,

            Long medicineId,

            Long scheduleId
    );

    // =====================================
    // GET ALL
    // =====================================

    List<
            MedicineHistoryResponseDTO
            >

    getHistory(

            String username
    );

    // =====================================
    // GET MEDICINE HISTORY
    // =====================================

    List<
            MedicineHistoryResponseDTO
            >

    getMedicineHistory(

            String username,

            Long medicineId
    );
}