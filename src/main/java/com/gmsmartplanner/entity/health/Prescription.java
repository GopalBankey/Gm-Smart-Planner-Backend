package com.gmsmartplanner.entity.health;

import com.gmsmartplanner.entity.BaseEntity;
import com.gmsmartplanner.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(
        name = "prescriptions"
)
@Getter
@Setter
public class Prescription
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
    // PRESCRIPTION PHOTO
    // =====================================

    @Column(
            name =
                    "prescription_photo"
    )
    private String prescriptionPhoto;

    // =====================================
    // PRESCRIPTION DATE
    // =====================================

    @Column(
            name =
                    "prescription_date",

            nullable =
                    false
    )
    private LocalDate prescriptionDate;

    // =====================================
    // DOCTOR
    // =====================================

    @ManyToOne(
            fetch =
                    FetchType.LAZY
    )
    @JoinColumn(
            name =
                    "doctor_id"
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
            name =
                    "hospital_id"
    )
    private Hospital hospital;

    // =====================================
    // RELATED DISEASE
    // =====================================

    @Column(
            name =
                    "related_disease"
    )
    private String relatedDisease;

    // =====================================
    // ACTIVE
    // =====================================

    @Column(
            name =
                    "is_active",

            nullable =
                    false
    )
    private boolean active = true;
}