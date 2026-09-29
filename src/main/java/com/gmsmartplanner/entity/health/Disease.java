package com.gmsmartplanner.entity.health;

import com.gmsmartplanner.entity.BaseEntity;
import com.gmsmartplanner.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
        name = "diseases"
)
@Getter
@Setter
public class Disease
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
    // DISEASE NAME
    // =====================================

    @Column(
            name =
                    "disease_name",

            nullable =
                    false
    )
    private String diseaseName;

    // =====================================
    // DISEASE SINCE
    // =====================================

    @Column(
            name =
                    "disease_since"
    )
    private LocalDate diseaseSince;

    // =====================================
    // MEDICINES
    // =====================================

    @ManyToMany(
            fetch =
                    FetchType.LAZY
    )
    @JoinTable(

            name =
                    "disease_medicines",

            joinColumns =

            @JoinColumn(
                    name =
                            "disease_id"
            ),

            inverseJoinColumns =

            @JoinColumn(
                    name =
                            "medicine_id"
            )
    )
    private List<Medicine>
            medicines =
            new ArrayList<>();

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
    // REFERENCE PERSON
    // =====================================

    @Column(
            name =
                    "reference_person"
    )
    private String referencePerson;

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