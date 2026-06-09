package com.gmsmartplanner.dto.response.todo;

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

    private LocalDateTime reminderTime;

    private ReminderNotificationType
            notificationType;

    private boolean sent;

    private boolean active;

    private boolean recurring;

    private RecurringType recurringType;

    private LocalDateTime lastSentAt;
}