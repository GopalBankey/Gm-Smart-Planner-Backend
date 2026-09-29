package com.gmsmartplanner.entity.health;

import com.gmsmartplanner.entity.BaseEntity;
import com.gmsmartplanner.entity.User;
import com.gmsmartplanner.enums.health.AllergySeverity;
import com.gmsmartplanner.enums.health.ReactionType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(
        name = "allergies"
)
@Getter
@Setter
public class Allergy
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
    // ALLERGIC TO / MEDICINE NAME
    // =====================================

    @Column(
            name =
                    "allergic_to",

            nullable =
                    false
    )
    private String allergicTo;

    // =====================================
    // REACTION / PROBLEM
    // =====================================

    @Enumerated(
            EnumType.STRING
    )
    @Column(
            name =
                    "reaction",

            nullable =
                    false
    )
    private ReactionType reaction;

    // =====================================
    // OTHER REACTION
    // =====================================

    @Column(
            name =
                    "other_reaction"
    )
    private String otherReaction;

    // =====================================
    // SEVERITY
    // =====================================

    @Enumerated(
            EnumType.STRING
    )
    @Column(
            name =
                    "severity",

            nullable =
                    false
    )
    private AllergySeverity severity;

    // =====================================
    // DOCTOR'S ADVICE
    // =====================================

    @Column(
            name =
                    "doctor_advice",

            columnDefinition =
                    "TEXT"
    )
    private String doctorAdvice;

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