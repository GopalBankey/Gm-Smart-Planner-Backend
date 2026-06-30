package com.gmsmartplanner.entity.health;

import com.gmsmartplanner.entity.BaseEntity;
import com.gmsmartplanner.entity.User;
import com.gmsmartplanner.enums.health.MealType;
import com.gmsmartplanner.enums.health.MedicineForm;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
        name = "medicines"
)
@Getter
@Setter
public class Medicine
        extends BaseEntity {

    @Id
    @GeneratedValue(
            strategy =
                    GenerationType.IDENTITY
    )
    private Long id;

    @ManyToOne(
            fetch =
                    FetchType.LAZY
    )
    @JoinColumn(
            name =
                    "user_id"
    )
    private User user;

    @ManyToOne(
            fetch =
                    FetchType.LAZY
    )
    @JoinColumn(
            name =
                    "doctor_id"
    )
    private Doctor doctor;

    @ManyToOne(
            fetch =
                    FetchType.LAZY
    )
    @JoinColumn(
            name =
                    "hospital_id"
    )
    private Hospital hospital;

    @Column(
            name =
                    "medicine_name"
    )
    private String medicineName;

    @Column(
            name =
                    "dosage"
    )
    private String dosage;

    @Enumerated(
            EnumType.STRING
    )
    @Column(
            name =
                    "medicine_form"
    )
    private MedicineForm form;

    @Column(
            name =
                    "purpose",
            columnDefinition =
                    "TEXT"
    )
    private String purpose;

    @Enumerated(
            EnumType.STRING
    )
    @Column(
            name =
                    "meal_type"
    )
    private MealType mealType;

    @Column(
            name =
                    "start_date"
    )
    private LocalDate startDate;

    @Column(
            name =
                    "end_date"
    )
    private LocalDate endDate;

    @Column(
            name =
                    "pill_color"
    )
    private String pillColor;

    @Column(
            name =
                    "pill_photo"
    )
    private String pillPhoto;

    @Column(
            name =
                    "company_name"
    )
    private String companyName;

    @Column(
            name =
                    "current_stock"
    )
    private Integer currentStock;

    @Column(
            name =
                    "total_consumed"
    )
    private Integer totalConsumed =
            0;

    @Column(
            name =
                    "expiry_date"
    )
    private LocalDate expiryDate;

    @Column(
            name =
                    "refill_reminder"
    )
    private boolean refillReminder;

    @Column(
            name =
                    "notes",
            columnDefinition =
                    "TEXT"
    )
    private String notes;

    @Column(
            name =
                    "is_active"
    )
    private boolean active =
            true;


    @ManyToOne(
            fetch =
                    FetchType.LAZY
    )
    @JoinColumn(
            name =
                    "last_action_by"
    )
    private User
            lastActionBy;


    @OneToMany(

            mappedBy =
                    "medicine",

            cascade =
                    CascadeType.ALL
    )

    private List<MedicineSchedule>
            schedules =
            new ArrayList<>();
}