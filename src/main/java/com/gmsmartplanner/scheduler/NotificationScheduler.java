package com.gmsmartplanner.scheduler;

import com.gmsmartplanner.entity.AccountAccess;
import com.gmsmartplanner.entity.Reminder;
import com.gmsmartplanner.entity.User;
import com.gmsmartplanner.entity.UserAuth;
import com.gmsmartplanner.entity.health.Appointment;
import com.gmsmartplanner.enums.AccessModule;
import com.gmsmartplanner.enums.NotificationReferenceType;
import com.gmsmartplanner.enums.NotificationType;
import com.gmsmartplanner.enums.health.AppointmentStatus;
import com.gmsmartplanner.repository.AccountAccessRepository;
import com.gmsmartplanner.repository.ReminderRepository;
import com.gmsmartplanner.repository.UserAuthRepository;
import com.gmsmartplanner.repository.health.AppointmentRepository;
import com.gmsmartplanner.service.FirebaseNotificationService;
import com.gmsmartplanner.service.NotificationHelperService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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

    private void processReminders() {

        List<Reminder> reminders =

                reminderRepository

                        .findAllByActiveTrueAndSentFalseAndReminderTimeBefore(

                                LocalDateTime.now()
                        );

        for (

                Reminder reminder

                :

                reminders

        ) {

            try {

                User owner =
                        reminder
                                .getUser();

                String title =
                        "Reminder";

                String message =
                        "You have a scheduled reminder";

                NotificationType type =
                        resolveType(
                                reminder
                        );

                // =====================
                // OWNER
                // =====================

                // OWNER
// LOCAL TYPES → SKIP SERVER PUSH

// =====================
// OWNER
// =====================
// =====================
// OWNER
// ONLY TODO
// =====================

                if (

                        reminder
                                .getReferenceType()

                                ==

                                NotificationReferenceType
                                        .TODO

                ) {

                    sendNotification(

                            owner,

                            reminder,

                            title,

                            message,

                            type
                    );
                }

// =====================
// ACCESS MEMBER
// =====================

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

                if (

                        reminder
                                .getReferenceType()

                                !=

                                NotificationReferenceType
                                        .TODO

                                &&

                                reminder
                                        .getReferenceType()

                                        !=

                                        NotificationReferenceType
                                                .EXTRA_MEDICINE

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

                    sendNotification(

                            member,

                            reminder,

                            title,

                            owner.getName()
                                    +
                                    " has a scheduled reminder",

                            type
                    );
                }

                reminder.setSent(
                        true
                );

                reminder.setLastSentAt(

                        LocalDateTime.now()
                );

                reminderRepository
                        .save(
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
            }
        }
    }

    // =====================================
    // SEND
    // =====================================

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

            case MEDICINE ->

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

            case MEDICINE ->

                    NotificationType
                            .MEDICINE_REMINDER;

            case APPOINTMENT ->

                    NotificationType
                            .APPOINTMENT_REMINDER;

            default ->

                    NotificationType
                            .SYSTEM;
        };
    }

    // =====================================
    // APPOINTMENTS
    // =====================================

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
}