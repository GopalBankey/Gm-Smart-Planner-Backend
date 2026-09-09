package com.gmsmartplanner.service.health;

import com.gmsmartplanner.dto.response.health.MedicineCardResponseDTO;
import com.gmsmartplanner.dto.response.health.MedicineDashboardResponseDTO;

public interface MedicineDashboardService {

    MedicineDashboardResponseDTO
    getDashboard(

            String username
    );

    // =====================================
// GET MISSED MEDICINE DETAILS
// =====================================

    MedicineCardResponseDTO
    getMissedMedicineDetails(

            String username,

            Long medicineId,

            Long scheduleId

    );
}