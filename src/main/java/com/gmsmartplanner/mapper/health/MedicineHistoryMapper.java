package com.gmsmartplanner.mapper.health;

import com.gmsmartplanner.dto.response.health.MedicineHistoryResponseDTO;
import com.gmsmartplanner.dto.response.health.MedicineScheduleResponseDTO;
import com.gmsmartplanner.entity.User;
import com.gmsmartplanner.entity.health.Medicine;
import com.gmsmartplanner.entity.health.MedicineHistory;
import com.gmsmartplanner.entity.health.MedicineSchedule;
import com.gmsmartplanner.enums.health.MedicineHistoryAction;
import com.gmsmartplanner.enums.health.MedicineHistoryStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Component
public class MedicineHistoryMapper {

    // =====================================
    // TAKE
    // =====================================

    public MedicineHistory
    takeMedicine(

            User user,

            Medicine medicine,

            MedicineSchedule schedule,

            Integer previousStock,

            Integer currentStock,

            Integer dose

    ) {

        MedicineHistory history =
                baseHistory(
                        medicine
                );

        history.setUser(
                user
        );

        history.setSchedule(
                schedule
        );

        history.setAction(
                MedicineHistoryAction.TAKEN
        );

        history.setStatus(
                MedicineHistoryStatus.TAKEN
        );

        history.setConsumedDose(
                dose
        );

        history.setPreviousStock(
                previousStock
        );

        history.setCurrentStock(
                currentStock
        );

        return history;
    }

    // =====================================
    // SKIP
    // =====================================

    public MedicineHistory
    skipMedicine(

            User user,

            Medicine medicine,

            MedicineSchedule schedule,

            Integer stock

    ) {

        MedicineHistory history =
                baseHistory(
                        medicine
                );

        history.setUser(
                user
        );

        history.setSchedule(
                schedule
        );

        history.setAction(
                MedicineHistoryAction.SKIPPED
        );

        history.setStatus(
                MedicineHistoryStatus.SKIPPED
        );

        history.setConsumedDose(
                0
        );

        history.setPreviousStock(
                stock
        );

        history.setCurrentStock(
                stock
        );

        return history;
    }

    // =====================================
    // MISSED
    // =====================================

    public MedicineHistory
    missedMedicine(

            User user,

            Medicine medicine,

            MedicineSchedule schedule

    ) {

        MedicineHistory history =
                baseHistory(
                        medicine
                );

        history.setUser(
                user
        );

        history.setSchedule(
                schedule
        );

        history.setAction(
                MedicineHistoryAction.SKIPPED
        );

        history.setStatus(
                MedicineHistoryStatus.MISSED
        );

        history.setConsumedDose(
                0
        );

        return history;
    }

    // =====================================
    // CREATE
    // =====================================

    public MedicineHistory
    createMedicineHistory(

            User user,

            Medicine medicine

    ) {

        MedicineHistory history =
                baseHistory(
                        medicine
                );

        history.setUser(
                user
        );

        history.setAction(
                MedicineHistoryAction.CREATED
        );

        history.setPreviousStock(
                0
        );

        history.setCurrentStock(
                medicine.getCurrentStock()
        );

        history.setConsumedDose(
                0
        );

        history.setSchedule(

                medicine
                        .getSchedules()

                        .stream()

                        .findFirst()

                        .orElse(
                                null
                        )
        );

        history.setNewData(
                "Medicine created"
        );

        return history;
    }

    // =====================================
    // UPDATE
    // =====================================

    public MedicineHistory
    updateMedicineHistory(

            User user,

            Medicine medicine

    ) {

        MedicineHistory history =
                baseHistory(
                        medicine
                );

        history.setUser(
                user
        );

        history.setAction(
                MedicineHistoryAction.UPDATED
        );

        history.setPreviousStock(
                medicine.getCurrentStock()
        );

        history.setCurrentStock(
                medicine.getCurrentStock()
        );

        history.setNewData(
                "Medicine updated"
        );

        return history;
    }

    // =====================================
    // DELETE
    // =====================================

    public MedicineHistory
    deleteMedicineHistory(

            User user,

            Medicine medicine

    ) {

        MedicineHistory history =
                baseHistory(
                        medicine
                );

        history.setUser(
                user
        );

        history.setAction(
                MedicineHistoryAction.DELETED
        );

        history.setPreviousStock(
                medicine.getCurrentStock()
        );

        history.setCurrentStock(
                medicine.getCurrentStock()
        );

        history.setNewData(
                "Medicine deleted"
        );

        return history;
    }

    // =====================================
    // BASE SNAPSHOT
    // =====================================

    private MedicineHistory
    baseHistory(

            Medicine medicine

    ) {

        MedicineHistory history =
                new MedicineHistory();

        history.setMedicine(
                medicine
        );

        history.setDate(
                LocalDate.now()
        );

        history.setTime(
                LocalTime.now()
        );

        history.setMedicineName(
                medicine.getMedicineName()
        );

        history.setDosage(
                medicine.getDosage()
        );

        history.setCompanyName(
                medicine.getCompanyName()
        );

        history.setMedicineForm(

                medicine.getForm() != null
                        ?

                        medicine.getForm().name()
                        :

                        null
        );

        history.setMealType(

                medicine.getMealType() != null
                        ?

                        medicine.getMealType().name()
                        :

                        null
        );

        history.setPurpose(
                medicine.getPurpose()
        );

        history.setPillPhoto(
                medicine.getPillPhoto()
        );

        history.setNote(
                medicine.getNotes()
        );

        history.setStartDate(
                medicine.getStartDate()
        );

        history.setEndDate(
                medicine.getEndDate()
        );

        history.setExpiryDate(
                medicine.getExpiryDate()
        );

        history.setDoctorName(

                medicine.getDoctor() != null
                        ?

                        medicine.getDoctor().getDoctorName()
                        :

                        null
        );

        history.setHospitalName(

                medicine.getHospital() != null
                        ?

                        medicine.getHospital().getHospitalName()
                        :

                        null
        );

        history.setActive(
                true
        );

        return history;
    }

    // =====================================
    // RESPONSE
    // =====================================

    public MedicineHistoryResponseDTO
    toResponse(

            MedicineHistory history

    ) {

        return MedicineHistoryResponseDTO

                .builder()

                .id(
                        history.getId()
                )

                .medicineId(

                        history.getMedicine() != null
                                ?

                                history.getMedicine().getId()
                                :

                                null
                )

                .schedules(

                        history.getMedicine() == null

                                ?

                                List.of()

                                :

                                history
                                        .getMedicine()
                                        .getSchedules()

                                        .stream()

                                        .map(

                                                schedule ->

                                                        MedicineScheduleResponseDTO
                                                                .builder()

                                                                .id(
                                                                        schedule.getId()
                                                                )

                                                                .slotName(
                                                                        schedule.getSlotName()
                                                                )

                                                                .time(
                                                                        schedule.getTime()
                                                                )

                                                                .doseCount(
                                                                        schedule.getDoseCount()
                                                                )

                                                                .enabled(
                                                                        schedule.getActive()
                                                                )

                                                                .build()
                                        )

                                        .toList()
                )

                .medicineName(
                        history.getMedicineName()
                )

                .companyName(
                        history.getCompanyName()
                )

                .dosage(
                        history.getDosage()
                )

                .medicineForm(
                        history.getMedicineForm()
                )

                .mealType(
                        history.getMealType()
                )

                .pillPhoto(
                        history.getPillPhoto()
                )

                .action(
                        history.getAction()
                )

                .status(
                        history.getStatus()
                )

                .date(
                        history.getDate()
                )

                .time(
                        history.getTime()
                )

                .previousStock(
                        history.getPreviousStock()
                )

                .currentStock(
                        history.getCurrentStock()
                )

                .consumedDose(
                        history.getConsumedDose()
                )

                .doctorName(
                        history.getDoctorName()
                )

                .hospitalName(
                        history.getHospitalName()
                )

                .oldData(
                        history.getOldData()
                )

                .newData(
                        history.getNewData()
                )

                .note(
                        history.getNote()
                )

                .build();
    }
}