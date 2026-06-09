package com.gmsmartplanner.service.health;

import com.gmsmartplanner.dto.request.health.UpdateMedicineRefillReminderRequestDTO;
import com.gmsmartplanner.dto.response.health.MedicineRefillReminderResponseDTO;

public interface
MedicineRefillReminderService {

    // =====================================
    // GET
    // =====================================

    MedicineRefillReminderResponseDTO
    getRefillReminder(

            String username,

            Long medicineId
    );

    // =====================================
    // UPDATE
    // =====================================

    MedicineRefillReminderResponseDTO
    updateRefillReminder(

            String username,

            Long medicineId,

            UpdateMedicineRefillReminderRequestDTO dto
    );
}