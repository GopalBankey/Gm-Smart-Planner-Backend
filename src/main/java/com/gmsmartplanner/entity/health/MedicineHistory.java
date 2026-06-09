package com.gmsmartplanner.entity.health;

import com.gmsmartplanner.enums.health.MedicineHistoryAction;
import com.gmsmartplanner.entity.BaseEntity;
import com.gmsmartplanner.entity.User;
import com.gmsmartplanner.enums.health.MedicineHistoryStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(
        name =
                "medicine_history"
)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MedicineHistory
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

    @ManyToOne(
            fetch =
                    FetchType.LAZY
    )

    @JoinColumn(
            name =
                    "medicine_id"
    )

    private Medicine medicine;

    // =====================================
    // SCHEDULE
    // =====================================

    @ManyToOne(
            fetch =
                    FetchType.LAZY
    )

    @JoinColumn(
            name =
                    "schedule_id"
    )

    private MedicineSchedule schedule;

    // =====================================
    // ACTION
    // =====================================

    @Enumerated(
            EnumType.STRING
    )

    @Column(
            name =
                    "action"
    )

    private MedicineHistoryAction
            action;

    // =====================================
    // DATE
    // =====================================

    @Column(
            name =
                    "history_date"
    )

    private LocalDate date;

    // =====================================
    // TIME
    // =====================================

    @Column(
            name =
                    "history_time"
    )

    private LocalTime time;

    // =====================================
    // STATUS
    // =====================================

    @Enumerated(
            EnumType.STRING
    )

    @Column(
            name =
                    "status"
    )

    private MedicineHistoryStatus
            status;

    // =====================================
    // STOCK
    // =====================================

    @Column(
            name =
                    "previous_stock"
    )

    private Integer previousStock;

    @Column(
            name =
                    "current_stock"
    )

    private Integer currentStock;

    @Column(


            name =
                    "pill_photo"
    )

    private String pillPhoto;

    // =====================================
    // DOSE
    // =====================================

    @Column(
            name =
                    "consumed_dose"
    )

    private Integer consumedDose;

    // =====================================
    // SNAPSHOT
    // =====================================

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

    @Column(
            name =
                    "medicine_form"
    )

    private String medicineForm;

    @Column(
            name =
                    "meal_type"
    )

    private String mealType;

    @Column(
            name =
                    "purpose",

            columnDefinition =
                    "TEXT"
    )

    private String purpose;

    @Column(
            name =
                    "company_name"
    )

    private String companyName;

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
                    "expiry_date"
    )

    private LocalDate expiryDate;

    // =====================================
    // DOCTOR SNAPSHOT
    // =====================================

    @Column(
            name =
                    "doctor_name"
    )

    private String doctorName;

    // =====================================
    // HOSPITAL SNAPSHOT
    // =====================================

    @Column(
            name =
                    "hospital_name"
    )

    private String hospitalName;

    // =====================================
    // UPDATE LOG
    // =====================================

    @Column(
            name =
                    "old_data",

            columnDefinition =
                    "TEXT"
    )

    private String oldData;

    @Column(
            name =
                    "new_data",

            columnDefinition =
                    "TEXT"
    )

    private String newData;

    // =====================================
    // NOTE
    // =====================================

    @Column(
            name =
                    "note",

            columnDefinition =
                    "TEXT"
    )

    private String note;

    // =====================================
    // ACTIVE
    // =====================================

    @Column(
            name =
                    "is_active"
    )

    private boolean active =
            true;
}