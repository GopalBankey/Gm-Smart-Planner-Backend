package com.gmsmartplanner.entity.health;

import com.gmsmartplanner.enums.health.MedicineSlot;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Entity
@Table(
        name = "medicine_schedules",

        indexes = {

                @Index(
                        name = "idx_schedule_medicine",
                        columnList = "medicine_id"
                ),

                @Index(
                        name = "idx_schedule_slot",
                        columnList = "slot_name"
                )
        }
)
@Getter
@Setter
public class MedicineSchedule {

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
                    "medicine_id"
    )
    private Medicine medicine;

    // =====================================
    // SLOT
    // =====================================

    @Enumerated(
            EnumType.STRING
    )
    @Column(
            name =
                    "slot_name",

            nullable =
                    false,

            length =
                    20
    )
    private MedicineSlot slotName;

    // =====================================
    // TIME
    // =====================================

    @Column(
            name =
                    "schedule_time",

            nullable =
                    false
    )
    private LocalTime time;

    // =====================================
    // DOSE
    // =====================================

    @Column(
            name =
                    "dose_count"
    )
    private Integer doseCount;

    // =====================================
    // STATUS
    // =====================================

    @Column(
            name =
                    "is_active",

            nullable =
                    true
    )
    private Boolean active = true;
}