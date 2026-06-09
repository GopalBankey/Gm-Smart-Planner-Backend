package com.gmsmartplanner.entity.health;

import com.gmsmartplanner.entity.BaseEntity;
import com.gmsmartplanner.entity.User;
import com.gmsmartplanner.enums.health.AppointmentStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(
        name = "appointments",

        indexes = {

                @Index(
                        name = "idx_appointment_user",
                        columnList = "user_id"
                ),

                @Index(
                        name = "idx_appointment_date",
                        columnList = "appointment_date"
                ),

                @Index(
                        name = "idx_appointment_status",
                        columnList = "appointment_status"
                )
        }
)
@Getter
@Setter
public class Appointment
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
            name = "user_id",
            nullable = false
    )
    private User user;

    // =====================================
    // DOCTOR
    // =====================================

    @ManyToOne(
            fetch =
                    FetchType.LAZY
    )
    @JoinColumn(
            name = "doctor_id",
            nullable = false
    )
    private Doctor doctor;

    // =====================================
    // HOSPITAL
    // =====================================

    @ManyToOne(
            fetch =
                    FetchType.LAZY
    )
    @JoinColumn(
            name = "hospital_id",
            nullable = false
    )
    private Hospital hospital;

    // =====================================
    // DATE
    // =====================================

    @Column(
            name = "appointment_date",
            nullable = false
    )
    private LocalDate appointmentDate;

    // =====================================
    // TIME
    // =====================================

    @Column(
            name = "appointment_time",
            nullable = false
    )
    private LocalTime appointmentTime;

    // =====================================
    // NOTE
    // =====================================

    @Column(
            name = "visit_note",
            columnDefinition = "TEXT"
    )
    private String visitNote;

    // =====================================
    // FOLLOW UP
    // =====================================

    @Column(
            name = "follow_up_reminder",
            nullable = false
    )
    private boolean followUpReminder;

    // =====================================
    // STATUS
    // =====================================

    @Enumerated(
            EnumType.STRING
    )
    @Column(
            name = "appointment_status",
            nullable = false
    )
    private AppointmentStatus
            status =
            AppointmentStatus.UPCOMING;

    // =====================================
    // ACTIVE
    // =====================================

    @Column(
            name = "is_active",
            nullable = false
    )
    private boolean active =
            true;
}