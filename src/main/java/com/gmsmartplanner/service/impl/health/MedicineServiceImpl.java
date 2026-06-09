package com.gmsmartplanner.service.impl.health;

import com.gmsmartplanner.dto.request.health.CreateMedicineRequestDTO;
import com.gmsmartplanner.dto.request.health.MedicineScheduleRequestDTO;
import com.gmsmartplanner.dto.request.health.UpdateMedicineRequestDTO;
import com.gmsmartplanner.dto.response.health.MedicineResponseDTO;
import com.gmsmartplanner.entity.User;
import com.gmsmartplanner.entity.health.*;
import com.gmsmartplanner.exception.ResourceNotFoundException;
import com.gmsmartplanner.mapper.health.MedicineHistoryMapper;
import com.gmsmartplanner.mapper.health.MedicineMapper;
import com.gmsmartplanner.repository.health.*;
import com.gmsmartplanner.service.FileUploadService;
import com.gmsmartplanner.service.UserHelperService;
import com.gmsmartplanner.service.health.MedicineService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
                        request.getSlotName()
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
                        request.getSlotName()
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
}