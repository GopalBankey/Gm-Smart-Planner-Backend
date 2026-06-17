package com.gmsmartplanner.entity;

import com.gmsmartplanner.enums.NotificationReferenceType;
import com.gmsmartplanner.enums.budget.RecurringType;
import com.gmsmartplanner.enums.todo.ReminderNotificationType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "reminders",

        indexes = {

                @Index(
                        name =
                                "idx_reminder_user",

                        columnList =
                                "user_id"
                ),

                @Index(
                        name =
                                "idx_reminder_reference",

                        columnList =
                                "reference_id"
                ),

                @Index(
                        name =
                                "idx_reminder_type",

                        columnList =
                                "reference_type"
                ),

                @Index(
                        name =
                                "idx_reminder_time",

                        columnList =
                                "reminder_time"
                ),

                @Index(
                        name =
                                "idx_reminder_sent",

                        columnList =
                                "sent"
                )
        }
)
@Getter
@Setter
public class Reminder
        extends BaseEntity {

    @Id
    @GeneratedValue(
            strategy =
                    GenerationType.IDENTITY
    )
    private Long id;

    // =====================================
    // USER
    // =====================================

    @ManyToOne(
            fetch =
                    FetchType.LAZY,

            optional =
                    false
    )

    @JoinColumn(

            name =
                    "user_id",

            nullable =
                    false
    )

    private User user;

    // =====================================
    // REFERENCE
    // =====================================

    @Column(
            name =
                    "reference_id",

            nullable =
                    false
    )

    private Long referenceId;

    @Enumerated(
            EnumType.STRING
    )

    @Column(

            name =
                    "reference_type",

            nullable =
                    false,

            length =
                    50
    )

    private NotificationReferenceType
            referenceType;

    // =====================================
    // REMINDER TIME
    // =====================================

    @Column(
            name =
                    "reminder_time",

            nullable =
                    false
    )

    private LocalDateTime
            reminderTime;

    // =====================================
    // NOTIFICATION TYPE
    // =====================================

    @Enumerated(
            EnumType.STRING
    )

    @Column(

            nullable =
                    false,

            length =
                    20
    )

    private ReminderNotificationType
            notificationType =

            ReminderNotificationType
                    .NORMAL;

    // =====================================
    // STATUS
    // =====================================

    @Column(
            nullable =
                    false
    )

    private Boolean sent =
            false;

    @Column(
            nullable =
                    false
    )

    private Boolean active =
            true;

    // =====================================
    // RECURRING
    // =====================================

    @Column(
            nullable =
                    false
    )

    private Boolean recurring =
            false;

    @Enumerated(
            EnumType.STRING
    )

    @Column(
            length =
                    30
    )

    private RecurringType
            recurringType;

    // =====================================
    // LAST SENT
    // =====================================

    private LocalDateTime
            lastSentAt;
}