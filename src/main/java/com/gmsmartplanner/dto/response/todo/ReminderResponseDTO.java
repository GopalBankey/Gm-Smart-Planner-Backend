package com.gmsmartplanner.dto.response.todo;

import com.gmsmartplanner.enums.NotificationReferenceType;
import com.gmsmartplanner.enums.budget.RecurringType;
import com.gmsmartplanner.enums.todo.ReminderNotificationType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class ReminderResponseDTO {

    private Long id;

    // =====================================
    // REFERENCE
    // =====================================

    private Long referenceId;

    private NotificationReferenceType
            referenceType;

    // =====================================
    // REMINDER
    // =====================================

    private LocalDateTime
            reminderTime;

    private ReminderNotificationType
            notificationType;

    // =====================================
    // STATUS
    // =====================================

    private Boolean
            sent;

    private Boolean
            active;

    private Boolean
            recurring;

    private RecurringType
            recurringType;

    // =====================================
    // TRACKING
    // =====================================

    private LocalDateTime
            lastSentAt;

    private LocalDateTime
            createdAt;

    private LocalDateTime
            updatedAt;
}