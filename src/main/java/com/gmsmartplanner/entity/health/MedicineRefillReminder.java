package com.gmsmartplanner.entity.health;

import com.gmsmartplanner.entity.BaseEntity;
import com.gmsmartplanner.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(
        name =
                "medicine_refill_reminders"
)
@Getter
@Setter
public class MedicineRefillReminder
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
                    FetchType.LAZY
    )

    @JoinColumn(
            name =
                    "user_id"
    )

    private User user;

    // =====================================
    // MEDICINE
    // =====================================

    @OneToOne(
            fetch =
                    FetchType.LAZY
    )

    @JoinColumn(
            name =
                    "medicine_id",

            nullable =
                    false,

            unique =
                    true
    )

    private Medicine medicine;

    // =====================================
    // REMINDER DATE
    // =====================================

    @Column(
            name =
                    "next_reminder_date"
    )

    private LocalDate nextReminderDate;

    // =====================================
    // REMINDER TIME
    // =====================================

    @Column(
            name =
                    "next_reminder_time"
    )

    private LocalTime nextReminderTime;
}