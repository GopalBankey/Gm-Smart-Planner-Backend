package com.gmsmartplanner.mapper;

import com.gmsmartplanner.dto.response.NotificationResponseDTO;
import com.gmsmartplanner.dto.response.todo.ReminderResponseDTO;
import com.gmsmartplanner.entity.Notification;
import com.gmsmartplanner.entity.Reminder;
import org.springframework.stereotype.Component;

@Component
public class ReminderNotificationMapper {

    // =====================================
    // REMINDER
    // =====================================

    public ReminderResponseDTO
    mapToReminderResponse(

            Reminder reminder

    ) {

        return ReminderResponseDTO
                .builder()

                .id(
                        reminder.getId()
                )

                .referenceId(
                        reminder.getReferenceId()
                )

                .referenceType(
                        reminder.getReferenceType()
                )

                .reminderTime(
                        reminder.getReminderTime()
                )

                .notificationType(
                        reminder.getNotificationType()
                )

                .sent(
                        reminder.getSent()
                )

                .active(
                        reminder.getActive()
                )

                .recurring(
                        reminder.getRecurring()
                )

                .recurringType(
                        reminder.getRecurringType()
                )

                .lastSentAt(
                        reminder.getLastSentAt()
                )

                .createdAt(
                        reminder.getCreatedAt()
                )

                .updatedAt(
                        reminder.getUpdatedAt()
                )

                .build();
    }

    // =====================================
    // NOTIFICATION
    // =====================================

    public NotificationResponseDTO
    mapToNotificationResponse(

            Notification notification

    ) {

        return NotificationResponseDTO
                .builder()

                .id(
                        notification.getId()
                )

                .type(
                        notification.getType()
                )

                .referenceId(
                        notification.getReferenceId()
                )

                .referenceType(
                        notification.getReferenceType()
                )

                .title(
                        notification.getTitle()
                )

                .message(
                        notification.getMessage()
                )

                .read(
                        notification.getRead()
                )

                .sent(
                        notification.getSent()
                )

                .deleted(
                        notification.getDeleted()
                )

                .imageUrl(
                        notification.getImageUrl()
                )

                .action(
                        notification.getAction()
                )

                .createdAt(
                        notification.getCreatedAt()
                )

                .updatedAt(
                        notification.getUpdatedAt()
                )

                .build();
    }
}