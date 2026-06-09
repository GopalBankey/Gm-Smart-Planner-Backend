package com.gmsmartplanner.entity.todo;

import com.gmsmartplanner.entity.BaseEntity;
import com.gmsmartplanner.enums.budget.RecurringType;
import com.gmsmartplanner.enums.todo.ReminderNotificationType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "todo_reminders",

        indexes = {

                @Index(
                        name = "idx_reminder_todo",
                        columnList = "todo_id"
                ),

                @Index(
                        name = "idx_reminder_time",
                        columnList = "reminder_time"
                ),

                @Index(
                        name = "idx_reminder_sent",
                        columnList = "sent"
                )
        }
)
@Getter
@Setter
public class TodoReminder
        extends BaseEntity {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;

    // =====================================
    // TODO
    // =====================================

    @ManyToOne(
            fetch = FetchType.LAZY,
            optional = false
    )
    @JoinColumn(
            name = "todo_id",
            nullable = false
    )
    private Todo todo;

    // =====================================
    // REMINDER TIME
    // =====================================

    @Column(
            nullable = false,
            name = "reminder_time"
    )
    private LocalDateTime reminderTime;

    // =====================================
    // NOTIFICATION TYPE
    // =====================================

    @Enumerated(EnumType.STRING)
    @Column(
            nullable = false,
            length = 20
    )
    private ReminderNotificationType
            notificationType =
            ReminderNotificationType.NORMAL;

    // =====================================
    // STATUS
    // =====================================

    @Column(nullable = false)
    private boolean sent = false;

    @Column(nullable = false)
    private boolean active = true;

    // =====================================
    // RECURRING
    // =====================================

    @Column(nullable = false)
    private boolean recurring = false;

    @Enumerated(EnumType.STRING)
    @Column(length = 30)
    private RecurringType recurringType;

    // =====================================
    // LAST SENT
    // =====================================

    private LocalDateTime lastSentAt;
}