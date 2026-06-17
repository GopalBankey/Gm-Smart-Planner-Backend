package com.gmsmartplanner.mapper.health;

import com.gmsmartplanner.dto.request.health.CreateMedicineRequestDTO;
import com.gmsmartplanner.dto.request.health.MedicineScheduleRequestDTO;
import com.gmsmartplanner.dto.request.health.UpdateMedicineRequestDTO;
import com.gmsmartplanner.dto.response.health.MedicineResponseDTO;
import com.gmsmartplanner.dto.response.health.MedicineScheduleResponseDTO;
import com.gmsmartplanner.entity.health.Medicine;
import com.gmsmartplanner.entity.health.MedicineSchedule;
import com.gmsmartplanner.enums.health.MedicineSlot;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MedicineMapper {

    // =====================================
    // CREATE
    // =====================================

    public Medicine createMedicine(

            CreateMedicineRequestDTO dto

    ) {

        Medicine medicine =
                new Medicine();

        medicine.setMedicineName(
                dto.getMedicineName()
        );

        medicine.setDosage(
                dto.getDosage()
        );

        medicine.setForm(
                dto.getForm()
        );

        medicine.setPurpose(
                dto.getPurpose()
        );

        medicine.setMealType(
                dto.getMealType()
        );

        medicine.setStartDate(
                dto.getStartDate()
        );

        medicine.setEndDate(
                dto.getEndDate()
        );

        medicine.setPillColor(
                dto.getPillColor()
        );

        medicine.setCompanyName(
                dto.getCompanyName()
        );

        medicine.setCurrentStock(
                dto.getCurrentStock()
        );

        medicine.setExpiryDate(
                dto.getExpiryDate()
        );

        medicine.setRefillReminder(
                dto.isRefillReminder()
        );

        medicine.setNotes(
                dto.getNotes()
        );

        medicine.setActive(
                true
        );

        return medicine;
    }

    // =====================================
    // UPDATE
    // =====================================

    public void updateMedicine(

            Medicine medicine,

            UpdateMedicineRequestDTO dto

    ) {

        if (dto.getMedicineName() != null)
            medicine.setMedicineName(
                    dto.getMedicineName()
            );

        if (dto.getDosage() != null)
            medicine.setDosage(
                    dto.getDosage()
            );

        if (dto.getForm() != null)
            medicine.setForm(
                    dto.getForm()
            );

        if (dto.getPurpose() != null)
            medicine.setPurpose(
                    dto.getPurpose()
            );

        if (dto.getMealType() != null)
            medicine.setMealType(
                    dto.getMealType()
            );

        if (dto.getStartDate() != null)
            medicine.setStartDate(
                    dto.getStartDate()
            );

        if (dto.getEndDate() != null)
            medicine.setEndDate(
                    dto.getEndDate()
            );

        if (dto.getPillColor() != null)
            medicine.setPillColor(
                    dto.getPillColor()
            );

        if (dto.getCompanyName() != null)
            medicine.setCompanyName(
                    dto.getCompanyName()
            );

        if (dto.getCurrentStock() != null)
            medicine.setCurrentStock(
                    dto.getCurrentStock()
            );

        if (dto.getExpiryDate() != null)
            medicine.setExpiryDate(
                    dto.getExpiryDate()
            );

        if (dto.getRefillReminder() != null)
            medicine.setRefillReminder(
                    dto.getRefillReminder()
            );

        if (dto.getNotes() != null)
            medicine.setNotes(
                    dto.getNotes()
            );
    }

    // =====================================
    // SCHEDULE
    // =====================================

    public List<MedicineSchedule>
    mapSchedules(

            List<MedicineScheduleRequestDTO>
                    schedules,

            Medicine medicine

    ) {

        List<MedicineSchedule>
                response =
                new ArrayList<>();

        if (

                schedules == null

        ) {

            return response;
        }

        for (

                MedicineScheduleRequestDTO dto

                :

                schedules

        ) {

            MedicineSchedule schedule =
                    new MedicineSchedule();

            schedule.setMedicine(
                    medicine
            );

            // FIXED FOR ENUM

            schedule.setSlotName(

                    MedicineSlot.valueOf(

                            dto
                                    .getSlotName()

                                    .trim()

                                    .toUpperCase()
                    )
            );

            schedule.setTime(
                    dto.getTime()
            );

            schedule.setDoseCount(
                    dto.getDoseCount()
            );

            response.add(
                    schedule
            );
        }

        return response;
    }

    // =====================================
    // RESPONSE
    // =====================================

    public MedicineResponseDTO mapToResponse(

            Medicine medicine

    ) {

        return MedicineResponseDTO
                .builder()

                .id(
                        medicine.getId()
                )

                .medicineName(
                        medicine.getMedicineName()
                )

                .dosage(
                        medicine.getDosage()
                )

                .form(
                        medicine.getForm()
                )

                .purpose(
                        medicine.getPurpose()
                )

                .mealType(
                        medicine.getMealType()
                )

                .startDate(
                        medicine.getStartDate()
                )

                .endDate(
                        medicine.getEndDate()
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

                .doctorId(

                        medicine.getDoctor() != null
                                ?
                                medicine.getDoctor().getId()
                                :
                                null
                )

                .doctorName(

                        medicine.getDoctor() != null
                                ?
                                medicine.getDoctor().getDoctorName()
                                :
                                null
                )

                .hospitalId(

                        medicine.getHospital() != null
                                ?
                                medicine.getHospital().getId()
                                :
                                null
                )

                .hospitalName(

                        medicine.getHospital() != null
                                ?
                                medicine.getHospital().getHospitalName()
                                :
                                null
                )

                .hospitalAddress(

                        medicine.getHospital() != null
                                ?
                                medicine.getHospital().getAddress()
                                :
                                null
                )

                .currentStock(
                        medicine.getCurrentStock()
                )

                .totalConsumed(
                        medicine.getTotalConsumed()
                )

                .expiryDate(
                        medicine.getExpiryDate()
                )

                .refillReminder(
                        medicine.isRefillReminder()
                )

                .notes(
                        medicine.getNotes()
                )

                .schedules(

                        medicine
                                .getSchedules()

                                .stream()

                                .filter(

                                        s ->

                                                Boolean.TRUE.equals(
                                                        s.getActive()
                                                )
                                )

                                .map(

                                        s ->

                                                MedicineScheduleResponseDTO
                                                        .builder()

                                                        .id(
                                                                s.getId()
                                                        )

                                                        .slotName(
                                                                s.getSlotName()
                                                                        .name()
                                                        )

                                                        .time(
                                                                s.getTime()
                                                        )

                                                        .doseCount(
                                                                s.getDoseCount()
                                                        )

                                                        .enabled(

                                                                Boolean.TRUE.equals(

                                                                        s.getActive()
                                                                )
                                                        )

                                                        .build()
                                )

                                .toList()
                )

                .build();
    }
}