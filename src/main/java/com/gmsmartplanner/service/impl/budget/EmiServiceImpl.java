package com.gmsmartplanner.service.impl.budget;

import com.gmsmartplanner.dto.request.budget.CreateEmiRequestDTO;
import com.gmsmartplanner.dto.request.budget.UpdateEmiRequestDTO;
import com.gmsmartplanner.dto.response.budget.EmiResponseDTO;
import com.gmsmartplanner.entity.Reminder;
import com.gmsmartplanner.entity.User;
import com.gmsmartplanner.entity.budget.Emi;
import com.gmsmartplanner.enums.NotificationReferenceType;
import com.gmsmartplanner.enums.budget.RecurringType;
import com.gmsmartplanner.enums.todo.ReminderNotificationType;
import com.gmsmartplanner.exception.ResourceNotFoundException;
import com.gmsmartplanner.mapper.budget.EmiMapper;
import com.gmsmartplanner.entity.emi.EmiCategory ;
import com.gmsmartplanner.repository.ReminderRepository;
import com.gmsmartplanner.repository.budget.EmiCategoryRepository;
import com.gmsmartplanner.repository.budget.EmiRepository;
import com.gmsmartplanner.service.UserHelperService;
import com.gmsmartplanner.service.budget.EmiService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class EmiServiceImpl
        implements EmiService {

    private final EmiRepository
            emiRepository;

    private final EmiCategoryRepository
            categoryRepository;

    private final EmiMapper
            emiMapper;

    private final UserHelperService
            userHelperService;

    private final ReminderRepository
            reminderRepository;

    // =====================================
    // CREATE EMI
    // =====================================

    @Override
    public EmiResponseDTO createEmi(

            String username,

            CreateEmiRequestDTO dto

    ) {

        User user =

                userHelperService
                        .getCurrentUser(
                                username
                        );

        EmiCategory category =

                categoryRepository

                        .findByIdAndActiveTrue(
                                dto.getCategoryId()
                        )

                        .orElseThrow(

                                () ->

                                        new ResourceNotFoundException(
                                                "EMI category not found"
                                        )
                        );

        Emi emi =

                emiMapper
                        .createEmi(
                                dto
                        );

        emi.setUser(
                user
        );

        emi.setCategory(
                category
        );

        Emi saved =

                emiRepository
                        .save(
                                emi
                        );
        createEmiReminder(
                saved
        );

        return emiMapper
                .mapToResponse(
                        saved
                );
    }

    // =====================================
    // GET ALL EMIS
    // =====================================

    @Override
    @Transactional(readOnly = true)
    public List<EmiResponseDTO>
    getEmis(

            String username

    ) {

        User user =

                userHelperService
                        .getCurrentUser(
                                username
                        );

        return emiRepository

                .findAllByUserAndActiveTrue(
                        user
                )

                .stream()

                .map(
                        emiMapper::mapToResponse
                )

                .toList();
    }

    // =====================================
    // GET EMI
    // =====================================

    @Override
    @Transactional(readOnly = true)
    public EmiResponseDTO
    getEmi(

            String username,

            Long emiId

    ) {

        return emiMapper
                .mapToResponse(

                        getEmiEntity(

                                username,

                                emiId
                        )
                );
    }

    // =====================================
    // UPDATE EMI
    // =====================================

    @Override
    public EmiResponseDTO
    updateEmi(

            String username,

            Long emiId,

            UpdateEmiRequestDTO dto

    ) {

        Emi emi =

                getEmiEntity(

                        username,

                        emiId
                );

        emiMapper
                .updateEmi(

                        emi,

                        dto
                );

        // =====================================
        // UPDATE CATEGORY
        // =====================================

        if (

                dto.getCategoryId()
                        != null

        ) {

            EmiCategory category =

                    categoryRepository

                            .findByIdAndActiveTrue(
                                    dto.getCategoryId()
                            )

                            .orElseThrow(

                                    () ->

                                            new ResourceNotFoundException(
                                                    "EMI category not found"
                                            )
                            );

            emi.setCategory(
                    category
            );
        }

        Emi updated =

                emiRepository
                        .save(
                                emi
                        );

        deleteEmiReminder(
                updated.getId()
        );

        createEmiReminder(
                updated
        );


        return emiMapper
                .mapToResponse(
                        updated
                );
    }

    // =====================================
    // DELETE EMI
    // =====================================

    @Override
    public void deleteEmi(

            String username,

            Long emiId

    ) {

        Emi emi =

                getEmiEntity(

                        username,

                        emiId
                );

        deleteEmiReminder(
                emiId
        );

        emi.setActive(
                false
        );

        emiRepository.save(
                emi
        );
    }

    // =====================================
    // GET EMI ENTITY
    // =====================================

    private Emi getEmiEntity(

            String username,

            Long emiId

    ) {

        User user =

                userHelperService
                        .getCurrentUser(
                                username
                        );

        return emiRepository

                .findByIdAndUserAndActiveTrue(

                        emiId,

                        user
                )

                .orElseThrow(

                        () ->

                                new ResourceNotFoundException(
                                        "EMI not found"
                                )
                );
    }

    private void createEmiReminder(

            Emi emi

    ) {

        LocalDateTime reminderTime =

                emi.getEmiDueDate()
                        .atTime(
                                9,
                                0
                        );

        if (

                reminderTime.isBefore(
                        LocalDateTime.now()
                )

        ) {

            return;
        }

        Reminder reminder =
                new Reminder();

        reminder.setUser(
                emi.getUser()
        );

        reminder.setReferenceId(
                emi.getId()
        );

        reminder.setReferenceType(

                NotificationReferenceType
                        .EMI
        );

        reminder.setReminderTime(
                reminderTime
        );

        reminder.setNotificationType(

                ReminderNotificationType
                        .NORMAL
        );

        reminder.setRecurring(
                true
        );

        reminder.setRecurringType(
                RecurringType.MONTHLY
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



    private void deleteEmiReminder(

            Long emiId

    ) {

        reminderRepository
                .deleteAllByReferenceIdAndReferenceType(

                        emiId,

                        NotificationReferenceType
                                .EMI
                );
    }

}