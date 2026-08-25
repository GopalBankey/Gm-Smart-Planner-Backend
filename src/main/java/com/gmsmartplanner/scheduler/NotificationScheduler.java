package com.gmsmartplanner.scheduler;

import com.gmsmartplanner.entity.AccountAccess;
import com.gmsmartplanner.entity.Reminder;
import com.gmsmartplanner.entity.User;
import com.gmsmartplanner.entity.UserAuth;
import com.gmsmartplanner.entity.budget.Emi;
import com.gmsmartplanner.entity.health.Appointment;
import com.gmsmartplanner.enums.AccessModule;
import com.gmsmartplanner.enums.NotificationReferenceType;
import com.gmsmartplanner.enums.NotificationType;
import com.gmsmartplanner.enums.budget.RecurringType;
import com.gmsmartplanner.enums.health.AppointmentStatus;
import com.gmsmartplanner.repository.AccountAccessRepository;
import com.gmsmartplanner.repository.ReminderRepository;
import com.gmsmartplanner.repository.UserAuthRepository;
import com.gmsmartplanner.repository.budget.EmiRepository;
import com.gmsmartplanner.repository.health.AppointmentRepository;
import com.gmsmartplanner.repository.health.MedicineRepository;
import com.gmsmartplanner.repository.todo.TodoRepository;
import com.gmsmartplanner.service.FirebaseNotificationService;
import com.gmsmartplanner.service.NotificationHelperService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class NotificationScheduler {

    private final ReminderRepository
            reminderRepository;

    private final AppointmentRepository
            appointmentRepository;

    private final UserAuthRepository
            userAuthRepository;

    private final AccountAccessRepository
            accountAccessRepository;

    private final FirebaseNotificationService
            firebaseNotificationService;

    private final NotificationHelperService
            notificationHelperService;

    private final MedicineRepository
            medicineRepository;

    private final TodoRepository
            todoRepository;

    private final EmiRepository
            emiRepository;

    // =====================================
    // MAIN
    // =====================================

    @Scheduled(
            fixedRate = 60000
    )

    @Transactional
    public void runScheduler() {

        processReminders();

        processAppointments();
    }

    // =====================================
    // REMINDERS
    // =====================================

    // =====================================
// REMINDERS
// =====================================

    private void processReminders() {

        List<Reminder> reminders =

                reminderRepository
                        .findAllByActiveTrueAndSentFalseAndReminderTimeBefore(

                                LocalDateTime.now()
                        );

        for (

                Reminder reminder :

                reminders

        ) {

            try {

                // =====================================
                // EMI VALIDATION
                // =====================================

                if (
                        reminder.getReferenceType()
                                == NotificationReferenceType.EMI
                ) {

                    if (!validateEmiReminder(reminder)) {

                        reminder.setActive(false);

                        reminder.setSent(true);

                        reminderRepository.save(reminder);

                        continue;
                    }
                }
                // =====================================
                // OWNER
                // =====================================

                User owner =
                        reminder.getUser();

                String title =
                        buildTitle(
                                reminder
                        );

                String message =
                        buildMessage(

                                reminder,

                                owner,

                                false
                        );

                NotificationType type =
                        resolveType(
                                reminder
                        );

                AccountAccess access =

                        accountAccessRepository

                                .findByOwnerAndModule(

                                        owner,

                                        AccessModule
                                                .HEALTH
                                )

                                .orElse(
                                        null
                                );

                // =====================================
                // OWNER NOTIFICATION
                // =====================================

                if (

                        reminder.getReferenceType()
                                ==
                                NotificationReferenceType.TODO

                                ||

                                reminder.getReferenceType()
                                        ==
                                        NotificationReferenceType.REPORT

                                ||

                                reminder.getReferenceType()
                                        ==
                                        NotificationReferenceType.EMI

                ) {

                    sendNotification(

                            owner,

                            reminder,

                            title,

                            message,

                            type
                    );
                }

                // =====================================
                // SHARED USER NOTIFICATION
                // =====================================

                if (

                        reminder.getReferenceType()
                                !=
                                NotificationReferenceType.TODO

                                &&

                                reminder.getReferenceType()
                                        !=
                                        NotificationReferenceType.EXTRA_MEDICINE

                                &&

                                access != null

                                &&

                                Boolean.TRUE.equals(
                                        access.getOtpVerified()
                                )

                                &&

                                hasAccessPermission(
                                        access,
                                        reminder
                                )

                ) {

                    User member =
                            access.getMember();

                    // =====================================
                    // MEDICINE / REPORT
                    // =====================================

                    if (

                            reminder.getReferenceType()
                                    ==
                                    NotificationReferenceType.MEDICINE

                                    ||

                                    reminder.getReferenceType()
                                            ==
                                            NotificationReferenceType.REPORT

                    ) {

                        var medicine =

                                medicineRepository

                                        .findById(

                                                reminder
                                                        .getReferenceId()
                                        )

                                        .orElse(
                                                null
                                        );

                        if (

                                medicine != null

                                        &&

                                        medicine.getLastActionBy()
                                                != null

                                        &&

                                        medicine
                                                .getLastActionBy()
                                                .getId()
                                                .equals(
                                                        member.getId()
                                                )

                        ) {

                            continue;
                        }
                    }

                    // =====================================
                    // APPOINTMENT
                    // =====================================

                    if (

                            reminder.getReferenceType()
                                    ==
                                    NotificationReferenceType.APPOINTMENT

                    ) {

                        var appointment =

                                appointmentRepository

                                        .findById(

                                                reminder
                                                        .getReferenceId()
                                        )

                                        .orElse(
                                                null
                                        );

                        if (

                                appointment != null

                                        &&

                                        appointment.getLastActionBy()
                                                != null

                                        &&

                                        appointment
                                                .getLastActionBy()
                                                .getId()
                                                .equals(
                                                        member.getId()
                                                )

                        ) {

                            continue;
                        }
                    }

                    sendNotification(

                            member,

                            reminder,

                            title,

                            buildMessage(

                                    reminder,

                                    owner,

                                    true
                            ),

                            type
                    );
                }

                // =====================================
                // UPDATE REMINDER
                // =====================================

                reminder.setLastSentAt(
                        LocalDateTime.now()
                );

                // =====================================
                // EMI MONTHLY RECURRING
                // =====================================

                if (

                        reminder.getReferenceType()
                                ==
                                NotificationReferenceType.EMI

                                &&

                                reminder.getRecurring()

                                &&

                                reminder.getRecurringType()
                                        ==
                                        RecurringType.MONTHLY

                ) {

                    moveEmiReminderToNextMonth(
                            reminder
                    );

                } else {

                    // =====================================
                    // NORMAL ONE-TIME REMINDER
                    // =====================================

                    reminder.setSent(
                            true
                    );
                }

                reminderRepository.save(
                        reminder
                );
            }

            catch (

                    Exception e

            ) {

                log.error(

                        "Reminder failed : {}",

                        reminder.getId(),

                        e
                );

                log.error(

                        "FCM ERROR",

                        e
                );
            }
        }
    }


    private void sendNotification(

            User user,

            Reminder reminder,

            String title,

            String message,

            NotificationType type

    ) {

        log.info(
                "Sending notification -> user={} ref={} type={}",

                user.getId(),

                reminder.getReferenceId(),

                reminder.getReferenceType()
        );

        UserAuth auth =

                userAuthRepository

                        .findByUser(
                                user
                        )

                        .orElse(
                                null
                        );

        if (

                auth == null

                        ||

                        auth.getFcmToken()
                                == null

                        ||

                        auth.getFcmToken()
                                .isBlank()

        ) {

            return;
        }

        firebaseNotificationService
                .sendNotification(

                        auth.getFcmToken(),

                        title,

                        message,

                        reminder
                                .getReferenceId(),

                        type
                );

        notificationHelperService
                .createNotification(

                        user,

                        reminder
                                .getReferenceId(),

                        reminder
                                .getReferenceType(),

                        title,

                        message,

                        type
                );
    }

    // =====================================
    // ACCESS CHECK
    // =====================================
    private boolean
    hasAccessPermission(

            AccountAccess access,

            Reminder reminder

    ) {

        return switch (

                reminder
                        .getReferenceType()

                ) {

            case MEDICINE,
                 REPORT ->

                    Boolean.TRUE.equals(

                            access
                                    .getTakePermission()
                    );

            case APPOINTMENT ->

                    Boolean.TRUE.equals(

                            access
                                    .getViewPermission()
                    );

            default ->

                    false;
        };
    }

    // =====================================
    // TYPE
    // =====================================

    private NotificationType
    resolveType(

            Reminder reminder

    ) {

        return switch (

                reminder
                        .getReferenceType()

                ) {

            case TODO ->

                    NotificationType
                            .TODO_REMINDER;

            case MEDICINE,
                 REPORT ->

                    NotificationType
                            .MEDICINE_REMINDER;
            case APPOINTMENT ->

                    NotificationType
                            .APPOINTMENT_REMINDER;
            case EMI ->

                    NotificationType
                            .EMI_REMINDER;

            default ->

                    NotificationType
                            .SYSTEM;
        };
    }

    private String
    buildTitle(

            Reminder reminder

    ) {

        return switch (

                reminder
                        .getReferenceType()

                ) {

            case TODO ->

                    "Task Reminder";

            case MEDICINE ->

                    "Medicine Reminder";

            case REPORT ->

                    "Medicine Refill Alert";

            case APPOINTMENT ->

                    "Appointment Reminder";
            case EMI ->

                    "EMI Reminder";

            default ->

                    "Reminder";
        };
    }

    // =====================================
// MESSAGE
// =====================================

    private String buildMessage(

            Reminder reminder,

            User owner,

            boolean accessUser

    ) {

        String prefix =

                accessUser

                        ?

                        owner.getName()
                                + "'s "

                        :

                        "";

        try {

            switch (

                    reminder.getReferenceType()

            ) {

                // =====================================
                // MEDICINE
                // =====================================

                case MEDICINE -> {

                    var medicine =

                            medicineRepository

                                    .findById(

                                            reminder
                                                    .getReferenceId()
                                    )

                                    .orElse(
                                            null
                                    );

                    if (
                            medicine != null
                    ) {

                        return prefix

                                +

                                medicine
                                        .getMedicineName()

                                +

                                " medicine time";
                    }
                }

                // =====================================
                // REPORT
                // =====================================

                case REPORT -> {

                    var medicine =

                            medicineRepository

                                    .findById(

                                            reminder
                                                    .getReferenceId()
                                    )

                                    .orElse(
                                            null
                                    );

                    if (
                            medicine != null
                    ) {

                        return prefix

                                +

                                medicine
                                        .getMedicineName()

                                +

                                " stock is low. Please refill";
                    }
                }

                // =====================================
                // APPOINTMENT
                // =====================================

                case APPOINTMENT -> {

                    var appointment =

                            appointmentRepository

                                    .findById(

                                            reminder
                                                    .getReferenceId()
                                    )

                                    .orElse(
                                            null
                                    );

                    if (
                            appointment != null
                                    &&
                                    appointment.getDoctor() != null
                    ) {

                        return prefix

                                +

                                appointment
                                        .getDoctor()
                                        .getDoctorName()

                                +

                                " appointment scheduled";
                    }
                }

                // =====================================
                // TODO
                // =====================================

                case TODO -> {

                    var todo =

                            todoRepository

                                    .findById(

                                            reminder
                                                    .getReferenceId()
                                    )

                                    .orElse(
                                            null
                                    );

                    if (
                            todo != null
                    ) {

                        return prefix

                                +

                                todo.getTitle();
                    }
                }

                // =====================================
                // EMI
                // =====================================

                case EMI -> {

                    Emi emi =

                            emiRepository

                                    .findById(

                                            reminder
                                                    .getReferenceId()
                                    )

                                    .orElse(
                                            null
                                    );

                    if (
                            emi != null
                    ) {

                        LocalDate today =
                                LocalDate.now();

                        LocalDate dueDate =
                                emi.getEmiDueDate();

                        long daysRemaining =

                                ChronoUnit.DAYS.between(

                                        today,

                                        dueDate
                                );

                        if (
                                daysRemaining == 0
                        ) {

                            return prefix

                                    +

                                    emi.getEmiName()

                                    +

                                    " EMI payment of ₹"

                                    +

                                    emi.getEmiAmount()

                                    +

                                    " is due today";
                        }

                        if (
                                daysRemaining == 1
                        ) {

                            return prefix

                                    +

                                    emi.getEmiName()

                                    +

                                    " EMI payment of ₹"

                                    +

                                    emi.getEmiAmount()

                                    +

                                    " is due tomorrow";
                        }

                        if (
                                daysRemaining == 2
                        ) {

                            return prefix

                                    +

                                    emi.getEmiName()

                                    +

                                    " EMI payment of ₹"

                                    +

                                    emi.getEmiAmount()

                                    +

                                    " is due in 2 days";
                        }

                        return prefix

                                +

                                emi.getEmiName()

                                +

                                " EMI payment reminder";
                    }
                }

                default -> {
                    // No specific message
                }
            }

        }

        catch (
                Exception ignored
        ) {

            // Keep existing scheduler behavior.
        }

        return prefix
                +
                "scheduled reminder";
    }


    private void processAppointments() {

        List<Appointment>
                appointments =

                appointmentRepository
                        .findAllByActiveTrue();

        LocalDateTime now =
                LocalDateTime.now();

        for (

                Appointment appointment

                :

                appointments

        ) {

            if (

                    appointment
                            .getStatus()

                            ==

                            AppointmentStatus
                                    .COMPLETED

                            ||

                            appointment
                                    .getStatus()

                                    ==

                                    AppointmentStatus
                                            .MISSED

            ) {

                continue;
            }

            LocalDateTime dateTime =

                    LocalDateTime.of(

                            appointment
                                    .getAppointmentDate(),

                            appointment
                                    .getAppointmentTime()
                    );

            if (

                    dateTime
                            .isBefore(
                                    now
                            )

            ) {

                appointment.setStatus(

                        AppointmentStatus
                                .MISSED
                );
            }
        }

        appointmentRepository
                .saveAll(
                        appointments
                );
    }

    // =====================================
// VALIDATE EMI REMINDER
// =====================================

// =====================================
// VALIDATE EMI REMINDER
// =====================================

// =====================================
// VALIDATE EMI REMINDER
// =====================================

    private boolean validateEmiReminder(

            Reminder reminder

    ) {

        Emi emi =

                emiRepository

                        .findById(
                                reminder.getReferenceId()
                        )

                        .orElse(
                                null
                        );

        // =====================================
        // EMI DELETED / INACTIVE
        // =====================================

        if (
                emi == null
                        ||
                        !emi.isActive()
        ) {

            return false;
        }

        LocalDate dueDate =
                emi.getEmiDueDate();

        LocalDate reminderDate =
                reminder.getReminderTime()
                        .toLocalDate();

        // =====================================
        // EXPECTED REMINDER DATES
        // =====================================

        LocalDate twoDaysBefore =
                dueDate.minusDays(2);

        LocalDate oneDayBefore =
                dueDate.minusDays(1);

        // =====================================
        // VALIDATE REMINDER DATE
        // =====================================

        return reminderDate.equals(
                twoDaysBefore
        )

                ||

                reminderDate.equals(
                        oneDayBefore
                )

                ||

                reminderDate.equals(
                        dueDate
                );
    }

    // =====================================
// MOVE EMI REMINDER TO NEXT MONTH
// =====================================

    private void moveEmiReminderToNextMonth(

            Reminder reminder

    ) {

        Emi emi =

                emiRepository

                        .findById(
                                reminder.getReferenceId()
                        )

                        .orElse(
                                null
                        );

        // =====================================
        // EMI DELETED
        // =====================================

        if (
                emi == null
                        ||
                        !emi.isActive()
        ) {

            reminder.setActive(
                    false
            );

            reminder.setSent(
                    true
            );

            return;
        }

        LocalDate dueDate =
                emi.getEmiDueDate();

        LocalDate currentReminderDate =
                reminder.getReminderTime()
                        .toLocalDate();

        // =====================================
        // IDENTIFY REMINDER OFFSET
        // =====================================

        long offsetDays =

                ChronoUnit.DAYS.between(

                        currentReminderDate,

                        dueDate
                );

        LocalDate nextDueDate =
                dueDate.plusMonths(1);

        // =====================================
        // NEXT REMINDER DATE
        // =====================================

        LocalDate nextReminderDate =

                nextDueDate.minusDays(
                        offsetDays
                );

        reminder.setReminderTime(

                nextReminderDate.atTime(
                        9,
                        0
                )
        );

        reminder.setSent(
                false
        );

        reminder.setActive(
                true
        );
    }
}