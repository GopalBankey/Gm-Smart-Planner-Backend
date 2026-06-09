package com.gmsmartplanner.mapper.health;

import com.gmsmartplanner.dto.response.health.MedicineRefillReminderResponseDTO;
import com.gmsmartplanner.entity.health.Medicine;
import com.gmsmartplanner.entity.health.MedicineRefillReminder;
import org.springframework.stereotype.Component;

@Component
public class MedicineRefillReminderMapper {

    // =====================================
    // RESPONSE
    // =====================================

    public MedicineRefillReminderResponseDTO
    toResponse(

            MedicineRefillReminder reminder

    ) {

        Medicine medicine =

                reminder
                        .getMedicine();

        return MedicineRefillReminderResponseDTO

                .builder()

                .medicineId(
                        medicine.getId()
                )

                .medicineName(
                        medicine.getMedicineName()
                )

                .currentStock(
                        medicine.getCurrentStock()
                )

                .nextReminderDate(
                        reminder.getNextReminderDate()
                )

                .nextReminderTime(
                        reminder.getNextReminderTime()
                )

                .build();
    }
}