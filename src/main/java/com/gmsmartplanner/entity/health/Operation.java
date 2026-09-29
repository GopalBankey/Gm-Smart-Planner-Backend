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
        name = "operations"
)
@Getter
@Setter
public class Operation
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
    // OPERATION / SURGERY NAME
    // =====================================

    @Column(
            name =
                    "operation_name",

            nullable =
                    false
    )
    private String operationName;

    // =====================================
    // DATE
    // =====================================

    @Column(
            name =
                    "operation_date",

            nullable =
                    false
    )
    private LocalDate date;

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
    // REASON FOR OPERATION
    // =====================================

    @Column(
            name =
                    "reason",

            columnDefinition =
                    "TEXT"
    )
    private String reason;

    // =====================================
    // RELATED DOCUMENTS
    // =====================================

    @ElementCollection
    @CollectionTable(
            name =
                    "operation_documents",

            joinColumns =
            @JoinColumn(
                    name =
                            "operation_id"
            )
    )
    @Column(
            name =
                    "document_url"
    )
    private List<String>
            relatedDocuments =
            new ArrayList<>();

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