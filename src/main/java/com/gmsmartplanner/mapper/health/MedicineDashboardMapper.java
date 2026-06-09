package com.gmsmartplanner.mapper.health;

import com.gmsmartplanner.dto.response.health.*;
import com.gmsmartplanner.entity.health.Medicine;
import com.gmsmartplanner.entity.health.MedicineRefillReminder;
import com.gmsmartplanner.entity.health.MedicineSchedule;
import com.gmsmartplanner.enums.health.MedicineHistoryStatus;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MedicineDashboardMapper {

    // =====================================
    // DASHBOARD
    // =====================================

    public MedicineDashboardResponseDTO
    toDashboard(

            List<
                    MissedMedicineResponseDTO
                    >
                    missed,

            List<
                    RefillSoonMedicineResponseDTO
                    >
                    refill,

            List<
                    SessionMedicineResponseDTO
                    >
                    sessions

    ) {

        return MedicineDashboardResponseDTO

                .builder()

                .missedMedicines(
                        missed
                )

                .refillSoonMedicines(
                        refill
                )

                .sessions(
                        sessions
                )

                .build();
    }

    // =====================================
    // MISSED
    // =====================================

    public MissedMedicineResponseDTO
    toMissed(

            Medicine medicine,

            MedicineSchedule schedule

    ) {

        return MissedMedicineResponseDTO

                .builder()

                .medicineId(
                        medicine.getId()
                )

                .medicineScheduleId(
                        schedule.getId()
                )

                .medicineName(
                        medicine.getMedicineName()
                )

                .pillPhoto(
                        medicine.getPillPhoto()
                )

                .scheduledTime(

                        schedule
                                .getTime()
                                .toString()
                )

                .build();
    }
    // =====================================
    // REFILL FROM REMINDER
    // =====================================

    public RefillSoonMedicineResponseDTO
    toRefill(

            MedicineRefillReminder reminder

    ) {

        Medicine medicine =

                reminder
                        .getMedicine();

        return RefillSoonMedicineResponseDTO

                .builder()

                .medicineId(
                        medicine.getId()
                )

                .medicineName(
                        medicine.getMedicineName()
                )

                .pillPhoto(
                        medicine.getPillPhoto()
                )

                .currentStock(
                        medicine.getCurrentStock()
                )

                .remainingDose(
                        medicine.getCurrentStock()
                )

                .build();
    }

    // =====================================
    // REFILL FROM MEDICINE
    // =====================================

    public RefillSoonMedicineResponseDTO
    toRefill(

            Medicine medicine

    ) {

        return RefillSoonMedicineResponseDTO

                .builder()

                .medicineId(
                        medicine.getId()
                )

                .medicineName(
                        medicine.getMedicineName()
                )

                .pillPhoto(
                        medicine.getPillPhoto()
                )

                .currentStock(
                        medicine.getCurrentStock()
                )

                .remainingDose(
                        medicine.getCurrentStock()
                )

                .build();
    }
    // =====================================
    // CARD
    // =====================================

    public MedicineCardResponseDTO toCard(

            Medicine medicine,

            MedicineSchedule schedule,

            MedicineHistoryStatus status
    ) {

        return MedicineCardResponseDTO

                .builder()

                .medicineId(
                        medicine.getId()
                )

                .medicineScheduleId(
                        schedule.getId()
                )

                .medicineName(
                        medicine.getMedicineName()
                )

                .dosage(
                        medicine.getDosage()
                )

                .medicineForm(

                        medicine
                                .getForm()
                                .name()
                )

                .stock(
                        medicine.getCurrentStock()
                )

                .mealType(

                        medicine
                                .getMealType()
                                != null

                                ?

                                medicine
                                        .getMealType()
                                        .name()

                                :

                                null
                )

                .time(

                        schedule
                                .getTime()
                                .toString()
                )

                .status(
                        status
                )

                .purpose(
                        medicine.getPurpose()
                )

                .pillColor(
                        medicine.getPillColor()
                )

                .pillPhoto(
                        medicine.getPillPhoto()
                )

                .companyName(
                        medicine.getCompanyName()
                )

                .build();
    }

    // =====================================
    // SESSION
    // =====================================

    public SessionMedicineResponseDTO
    toSession(

            String slot,

            Integer upcomingCount,

            List<
                    MedicineCardResponseDTO
                    >
                    cards

    ) {

        return SessionMedicineResponseDTO

                .builder()

                .session(
                        slot
                )

                .upcomingCount(
                        upcomingCount
                )

                .medicines(
                        cards
                )

                .build();
    }
}