package com.gmsmartplanner.scheduler;

import com.gmsmartplanner.entity.todo.TodoReminder;
import com.gmsmartplanner.entity.User;
import com.gmsmartplanner.entity.UserAuth;
import com.gmsmartplanner.enums.NotificationType;
import com.gmsmartplanner.repository.todo.TodoReminderRepository;
import com.gmsmartplanner.repository.UserAuthRepository;
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
public class ReminderScheduler {

    private final TodoReminderRepository
            todoReminderRepository;

    private final UserAuthRepository
            userAuthRepository;

    private final FirebaseNotificationService
            firebaseNotificationService;

    private final NotificationHelperService
            notificationHelperService;

    // =====================================
    // SEND PENDING REMINDERS
    // =====================================
    @Scheduled(fixedRate = 60000)
    @Transactional
    public void sendPendingReminders() {

        log.info(
                "Checking pending reminders..."
        );

        List<TodoReminder> reminders =
                todoReminderRepository
                        .findAllByActiveTrueAndSentFalseAndReminderTimeBefore(

                                LocalDateTime.now()
                        );

        if (reminders.isEmpty()) {

            log.info(
                    "No pending reminders found"
            );

            return;
        }

        for (TodoReminder reminder : reminders) {

            try {

                User owner =
                        reminder.getTodo()
                                .getOwner();

                UserAuth auth =
                        userAuthRepository
                                .findByUser(owner)
                                .orElse(null);

                if (auth == null) {

                    log.warn(
                            "UserAuth not found for user : {}",
                            owner.getId()
                    );

                    continue;
                }

                String fcmToken =
                        auth.getFcmToken();

                if (fcmToken == null
                        || fcmToken.isBlank()) {

                    log.warn(
                            "FCM token not found for user : {}",
                            owner.getId()
                    );

                    continue;
                }

                // =====================================
                // SEND PUSH NOTIFICATION
                // =====================================

                firebaseNotificationService
                        .sendNotification(

                                fcmToken,

                                "Task Reminder",

                                "Reminder for task : "
                                        + reminder.getTodo()
                                        .getTitle(),

                                reminder.getTodo()
                                        .getId(),

                                NotificationType.TODO_REMINDER
                        );

                // =====================================
                // SAVE NOTIFICATION
                // =====================================

                notificationHelperService
                        .createNotification(

                                owner,

                                reminder.getTodo(),

                                "Task Reminder",

                                "Reminder for task : "
                                        + reminder.getTodo()
                                        .getTitle(),

                                NotificationType.TODO_REMINDER
                        );

                // =====================================
                // MARK AS SENT
                // =====================================

                reminder.setSent(true);

                reminder.setLastSentAt(
                        LocalDateTime.now()
                );

                todoReminderRepository.save(
                        reminder
                );

                log.info(
                        "Reminder sent successfully for todo : {}",
                        reminder.getTodo()
                                .getId()
                );

            } catch (Exception e) {

                log.error(
                        "Failed to process reminder : {}",

                        reminder.getId(),

                        e
                );
            }
        }
    }
}