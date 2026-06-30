package com.gmsmartplanner.service.impl.health;

import com.gmsmartplanner.dto.request.health.CreateMedicineRequestDTO;
import com.gmsmartplanner.dto.request.health.MedicineScheduleRequestDTO;
import com.gmsmartplanner.dto.request.health.UpdateMedicineRequestDTO;
import com.gmsmartplanner.dto.response.health.MedicineResponseDTO;
import com.gmsmartplanner.entity.User;
import com.gmsmartplanner.entity.health.*;
import com.gmsmartplanner.enums.health.MedicineSlot;
import com.gmsmartplanner.exception.ResourceNotFoundException;
import com.gmsmartplanner.mapper.health.MedicineHistoryMapper;
import com.gmsmartplanner.mapper.health.MedicineMapper;
import com.gmsmartplanner.repository.health.*;
import com.gmsmartplanner.service.FileUploadService;
import com.gmsmartplanner.service.UserHelperService;
import com.gmsmartplanner.service.health.MedicineService;
import com.gmsmartplanner.entity.Reminder;
import com.gmsmartplanner.enums.NotificationReferenceType;
import com.gmsmartplanner.repository.ReminderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class MedicineServiceImpl
        implements MedicineService {

    private final MedicineRepository
            medicineRepository;

    private final MedicineMapper
            medicineMapper;

    private final UserHelperService
            userHelperService;

    private final FileUploadService
            fileUploadService;

    private final DoctorRepository
            doctorRepository;

    private final HospitalRepository
            hospitalRepository;

    private final MedicineHistoryRepository
            historyRepository;

    private final MedicineRefillReminderRepository
            refillReminderRepository;

    private final MedicineHistoryMapper
            historyMapper;

    private final ReminderRepository
            reminderRepository;

    // =====================================
    // CREATE
    // =====================================

    @Override
    public MedicineResponseDTO createMedicine(

            String username,

            CreateMedicineRequestDTO dto

    ) {

        User user =
                userHelperService
                        .getCurrentUser(
                                username
                        );

        Medicine medicine =
                medicineMapper
                        .createMedicine(
                                dto
                        );
        medicine.setLastActionBy(
                user
        );

        medicine.setUser(
                user
        );

        // =====================================
        // DOCTOR
        // =====================================

        setDoctor(

                medicine,

                dto.getDoctorId(),

                user
        );

        // =====================================
        // HOSPITAL
        // =====================================

        setHospital(

                medicine,

                dto.getHospitalId(),

                user
        );

        // =====================================
        // PHOTO
        // =====================================

        uploadPillPhoto(

                medicine,

                dto.getPillPhoto()
        );

        // =====================================
        // SCHEDULES
        // =====================================

        if (

                dto.getSchedules()

                        != null

        ) {

            medicine
                    .getSchedules()

                    .addAll(

                            medicineMapper
                                    .mapSchedules(

                                            dto.getSchedules(),

                                            medicine
                                    )
                    );
        }

        // =====================================
        // SAVE MEDICINE
        // =====================================

        Medicine saved =

                medicineRepository
                        .save(
                                medicine
                        );

// CREATE MEDICINE REMINDERS

        createMedicineReminders(
                saved
        );

        // =====================================
        // AUTO CREATE REFILL REMINDER
        // =====================================

        MedicineRefillReminder
                reminder =

                new MedicineRefillReminder();

        reminder.setUser(
                user
        );

        reminder.setMedicine(
                saved
        );

        reminder.setNextReminderDate(
                null
        );

        reminder.setNextReminderTime(
                null
        );

        refillReminderRepository
                .save(
                        reminder
                );


        historyRepository
                .save(

                        historyMapper
                                .createMedicineHistory(

                                        user,

                                        saved
                                )
                );

        // =====================================
        // RESPONSE
        // =====================================

        return medicineMapper
                .mapToResponse(
                        saved
                );
    }

    // =====================================
    // GET ALL
    // =====================================

    @Override
    public List<MedicineResponseDTO>
    getMedicines(

            String username

    ) {

        User user =
                userHelperService
                        .getCurrentUser(
                                username
                        );

        return medicineRepository

                .findAllByUserAndActiveTrueOrderByCreatedAtDesc(
                        user
                )

                .stream()

                .map(
                        medicineMapper
                                ::mapToResponse
                )

                .toList();
    }

    // =====================================
    // GET BY ID
    // =====================================

    @Override
    public MedicineResponseDTO getMedicine(

            String username,

            Long medicineId

    ) {

        return medicineMapper
                .mapToResponse(

                        getMedicineEntity(

                                username,

                                medicineId
                        )
                );
    }


    @Override
    @Transactional
    public MedicineResponseDTO updateMedicine(

            String username,

            Long medicineId,

            UpdateMedicineRequestDTO dto

    ) {

        Medicine medicine =
                getMedicineEntity(
                        username,
                        medicineId
                );

        // ==========================
        // UPDATE BASIC DETAILS
        // ==========================

        medicineMapper.updateMedicine(
                medicine,
                dto
        );

        medicine.setLastActionBy(

                userHelperService
                        .getCurrentUser(
                                username
                        )
        );

        setDoctor(
                medicine,
                dto.getDoctorId(),
                medicine.getUser()
        );

        setHospital(
                medicine,
                dto.getHospitalId(),
                medicine.getUser()
        );

        uploadPillPhoto(
                medicine,
                dto.getPillPhoto()
        );

        // ==========================
        // UPDATE SCHEDULES
        // ==========================

        if (

                dto.getSchedules()
                        != null

        ) {

            List<MedicineSchedule>
                    existingSchedules =
                    medicine.getSchedules();

            int existingCount =
                    existingSchedules.size();

            int newCount =
                    dto.getSchedules()
                            .size();

            // update existing rows
            for (

                    int i = 0;

                    i < Math.min(
                            existingCount,
                            newCount
                    );

                    i++

            ) {

                MedicineSchedule schedule =
                        existingSchedules.get(i);

                MedicineScheduleRequestDTO request =
                        dto.getSchedules().get(i);

                schedule.setSlotName(

                        MedicineSlot.valueOf(

                                request
                                        .getSlotName()

                                        .trim()

                                        .toUpperCase()
                        )
                );

                schedule.setTime(
                        request.getTime()
                );

                schedule.setDoseCount(
                        request.getDoseCount()
                );

                schedule.setActive(
                        true
                );
            }

            // add new rows only if count increased
            for (

                    int i = existingCount;

                    i < newCount;

                    i++

            ) {

                MedicineScheduleRequestDTO request =
                        dto.getSchedules().get(i);

                MedicineSchedule schedule =
                        new MedicineSchedule();

                schedule.setMedicine(
                        medicine
                );

                schedule.setSlotName(

                        MedicineSlot.valueOf(

                                request
                                        .getSlotName()

                                        .trim()

                                        .toUpperCase()
                        )
                );

                schedule.setTime(
                        request.getTime()
                );

                schedule.setDoseCount(
                        request.getDoseCount()
                );

                schedule.setActive(
                        true
                );

                existingSchedules.add(
                        schedule
                );
            }

            // deactivate extra schedules
            for (

                    int i = newCount;

                    i < existingCount;

                    i++

            ) {

                existingSchedules
                        .get(i)
                        .setActive(
                                false
                        );
            }
        }

        Medicine updated =

                medicineRepository
                        .save(
                                medicine
                        );
        createLowStockNotification(
                updated
        );

// RECREATE REMINDERS

        deleteMedicineReminders(
                updated.getId()
        );

        createMedicineReminders(
                updated
        );

        historyRepository
                .save(

                        historyMapper
                                .updateMedicineHistory(

                                        updated.getUser(),

                                        updated
                                )
                );

        return medicineMapper
                .mapToResponse(
                        updated
                );
    }


    // =====================================
    // DELETE
    // =====================================

    @Override
    public void deleteMedicine(

            String username,

            Long medicineId

    ) {

        Medicine medicine =
                getMedicineEntity(

                        username,

                        medicineId
                );

        medicine.setActive(
                false
        );

        Medicine deleted =

                medicineRepository
                        .save(
                                medicine
                        );

        historyRepository
                .save(

                        historyMapper
                                .deleteMedicineHistory(

                                        deleted.getUser(),

                                        deleted
                                )
                );
    }

    // =====================================
    // HELPERS
    // =====================================

    private void setDoctor(

            Medicine medicine,

            Long doctorId,

            User user

    ) {

        if (doctorId == null)
            return;

        Doctor doctor =

                doctorRepository
                        .findByIdAndUserAndActiveTrue(

                                doctorId,

                                user
                        )

                        .orElseThrow(() ->

                                new ResourceNotFoundException(
                                        "Doctor not found"
                                )
                        );

        medicine.setDoctor(
                doctor
        );
    }

    private void setHospital(

            Medicine medicine,

            Long hospitalId,

            User user

    ) {

        if (hospitalId == null)
            return;

        Hospital hospital =

                hospitalRepository
                        .findByIdAndUserAndActiveTrue(

                                hospitalId,

                                user
                        )

                        .orElseThrow(() ->

                                new ResourceNotFoundException(
                                        "Hospital not found"
                                )
                        );

        medicine.setHospital(
                hospital
        );
    }

    private void uploadPillPhoto(

            Medicine medicine,

            org.springframework.web.multipart.MultipartFile file

    ) {

        if (

                file != null

                        &&

                        !file.isEmpty()

        ) {

            medicine.setPillPhoto(

                    fileUploadService
                            .uploadImage(

                                    file,

                                    "medicine"
                            )
            );
        }
    }

    private Medicine getMedicineEntity(

            String username,

            Long medicineId

    ) {

        User user =
                userHelperService
                        .getCurrentUser(
                                username
                        );

        return medicineRepository

                .findByIdAndUserAndActiveTrue(

                        medicineId,

                        user
                )

                .orElseThrow(() ->

                        new ResourceNotFoundException(
                                "Medicine not found"
                        )
                );
    }
    private void createMedicineReminders(

            Medicine medicine

    ) {

        if (

                medicine.getSchedules()
                        == null

                        ||

                        medicine.getSchedules()
                                .isEmpty()

        ) {

            return;
        }

        for (

                MedicineSchedule schedule

                :

                medicine.getSchedules()

        ) {

            if (

                    !Boolean.TRUE.equals(

                            schedule.getActive()
                    )

            ) {

                continue;
            }

            LocalDate reminderDate =

                    medicine.getStartDate();

            LocalDateTime reminderDateTime =

                    reminderDate.atTime(

                            schedule.getTime()
                    );

            // IF TIME ALREADY PASSED
            // MOVE TO NEXT DAY

            if (

                    reminderDateTime

                            .isBefore(

                                    LocalDateTime
                                            .now()
                            )

            ) {

                reminderDate =
                        reminderDate.plusDays(
                                1
                        );

                reminderDateTime =

                        reminderDate.atTime(

                                schedule.getTime()
                        );
            }

            Reminder reminder =
                    new Reminder();

            reminder.setUser(
                    medicine.getUser()
            );

            reminder.setReferenceId(
                    medicine.getId()
            );

            reminder.setReferenceType(

                    NotificationReferenceType
                            .MEDICINE
            );

            reminder.setReminderTime(
                    reminderDateTime
            );

            reminder.setRecurring(
                    true
            );

            reminder.setSent(
                    false
            );

            reminder.setActive(
                    true
            );

            reminderRepository
                    .save(
                            reminder
                    );
        }
    }

    private void deleteMedicineReminders(

            Long medicineId

    ) {

        reminderRepository

                .deleteAllByReferenceIdAndReferenceType(

                        medicineId,

                        NotificationReferenceType
                                .MEDICINE
                );
    }private void createLowStockNotification(

            Medicine medicine

    ) {

        if (

                !medicine.isRefillReminder()

                        ||

                        medicine.getCurrentStock()
                                == null

                        ||

                        medicine.getCurrentStock()
                                > 5

        ) {

            return;
        }

        boolean exists =

                reminderRepository

                        .existsByReferenceIdAndReferenceTypeAndSentFalse(

                                medicine.getId(),

                                NotificationReferenceType
                                        .REPORT
                        );

        if (

                exists

        ) {

            return;
        }

        Reminder reminder =
                new Reminder();

        reminder.setUser(
                medicine.getUser()
        );

        reminder.setReferenceId(
                medicine.getId()
        );

        // LOW STOCK → REPORT

        reminder.setReferenceType(

                NotificationReferenceType
                        .REPORT
        );

        reminder.setReminderTime(

                LocalDateTime.now()
                        .plusMinutes(
                                1
                        )
        );

        reminder.setRecurring(
                false
        );

        reminder.setSent(
                false
        );

        reminder.setActive(
                true
        );

        reminderRepository
                .save(
                        reminder
                );
    }
}