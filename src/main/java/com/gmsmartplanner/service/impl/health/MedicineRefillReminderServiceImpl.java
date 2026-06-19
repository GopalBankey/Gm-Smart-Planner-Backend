package com.gmsmartplanner.service.impl.health;

import com.gmsmartplanner.dto.request.health.UpdateMedicineRefillReminderRequestDTO;
import com.gmsmartplanner.dto.response.health.MedicineRefillReminderResponseDTO;
import com.gmsmartplanner.entity.User;
import com.gmsmartplanner.entity.health.Medicine;
import com.gmsmartplanner.entity.health.MedicineRefillReminder;
import com.gmsmartplanner.exception.ResourceNotFoundException;
import com.gmsmartplanner.mapper.health.MedicineRefillReminderMapper;
import com.gmsmartplanner.repository.health.MedicineRefillReminderRepository;
import com.gmsmartplanner.repository.health.MedicineRepository;
import com.gmsmartplanner.service.UserHelperService;
import com.gmsmartplanner.service.health.MedicineRefillReminderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.gmsmartplanner.entity.Reminder;
import com.gmsmartplanner.enums.NotificationReferenceType;
import com.gmsmartplanner.repository.ReminderRepository;

import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
@Transactional
public class MedicineRefillReminderServiceImpl
        implements MedicineRefillReminderService {

    private final MedicineRepository
            medicineRepository;

    private final MedicineRefillReminderRepository
            repository;

    private final MedicineRefillReminderMapper
            mapper;

    private final UserHelperService
            userHelperService;

    private final ReminderRepository
            reminderRepository;

    // =====================================
    // GET
    // =====================================

    @Override
    @Transactional(readOnly = true)
    public MedicineRefillReminderResponseDTO
    getRefillReminder(

            String username,

            Long medicineId

    ) {

        MedicineRefillReminder reminder =

                getReminder(
                        username,
                        medicineId
                );

        return mapper.toResponse(
                reminder
        );
    }

    // =====================================
    // UPDATE
    // =====================================

    @Override
    public MedicineRefillReminderResponseDTO
    updateRefillReminder(

            String username,

            Long medicineId,

            UpdateMedicineRefillReminderRequestDTO dto

    ) {

        MedicineRefillReminder reminder =

                getReminder(
                        username,
                        medicineId
                );

        Medicine medicine =
                reminder
                        .getMedicine();

        // STOCK UPDATE

        if (

                dto.getAddedStock()

                        != null

                        &&

                        dto.getAddedStock() > 0

        ) {

            medicine.setCurrentStock(

                    medicine.getCurrentStock()

                            +

                            dto.getAddedStock()
            );

            medicineRepository
                    .save(
                            medicine
                    );
        }

        // DATE

        if (

                dto.getReminderDate()

                        != null

        ) {

            reminder.setNextReminderDate(

                    dto.getReminderDate()
            );
        }

        // TIME

        if (

                dto.getReminderTime()

                        != null

        ) {

            reminder.setNextReminderTime(

                    dto.getReminderTime()
            );
        }

        MedicineRefillReminder saved =

                repository
                        .save(
                                reminder
                        );

        updateRefillSchedule(
                saved
        );

        return mapper.toResponse(
                saved
        );
    }

    // =====================================
    // ENTITY
    // =====================================

    private MedicineRefillReminder
    getReminder(

            String username,

            Long medicineId

    ) {

        User user =

                userHelperService
                        .getCurrentUser(
                                username
                        );

        Medicine medicine =

                medicineRepository

                        .findByIdAndUserAndActiveTrue(

                                medicineId,

                                user
                        )

                        .orElseThrow(

                                () ->

                                        new ResourceNotFoundException(

                                                "Medicine not found"
                                        )
                        );

        return repository

                .findByMedicine(
                        medicine
                )

                .orElseThrow(

                        () ->

                                new ResourceNotFoundException(

                                        "Refill reminder not found"
                                )
                );
    }

    private void updateRefillSchedule(

            MedicineRefillReminder refill

    ) {

        reminderRepository

                .deleteAllByReferenceIdAndReferenceType(

                        refill
                                .getMedicine()
                                .getId(),

                        NotificationReferenceType
                                .REPORT
                );

        if (

                refill.getNextReminderDate()
                        == null

                        ||

                        refill.getNextReminderTime()
                                == null

        ) {

            return;
        }

        Reminder reminder =
                new Reminder();

        reminder.setUser(

                refill
                        .getMedicine()
                        .getUser()
        );

        reminder.setReferenceId(

                refill
                        .getMedicine()
                        .getId()
        );

        // REFILL TYPE

        reminder.setReferenceType(

                NotificationReferenceType
                        .REPORT
        );

        reminder.setReminderTime(

                LocalDateTime.of(

                        refill.getNextReminderDate(),

                        refill.getNextReminderTime()
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