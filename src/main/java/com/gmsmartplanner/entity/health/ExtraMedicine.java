package com.gmsmartplanner.entity.health;

import com.gmsmartplanner.entity.BaseEntity;
import com.gmsmartplanner.entity.User;
import com.gmsmartplanner.enums.health.MedicineForm;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(
        name = "extra_medicines"
)
@Getter
@Setter
public class ExtraMedicine
        extends BaseEntity {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "user_id",
            nullable = false
    )
    private User user;

    @Column(
            name = "medicine_name",
            nullable = false
    )
    private String medicineName;

    @Column(
            name = "dosage_strength"
    )
    private String dosageStrength;

    @Enumerated(
            EnumType.STRING
    )
    @Column(
            name = "medicine_form"
    )
    private MedicineForm form;

    @Column(
            name = "purpose"
    )
    private String purpose;

    @Column(
            name = "pill_color"
    )
    private String pillColor;

    @Column(
            name = "pill_photo"
    )
    private String pillPhoto;

    @Column(
            name = "medicine_count"
    )
    private Integer count;

    @Column(
            name = "expiry_date"
    )
    private LocalDate expiryDate;

    @Column(
            name = "company_name"
    )
    private String companyName;

    @Column(
            name = "is_active"
    )
    private boolean active = true;
}