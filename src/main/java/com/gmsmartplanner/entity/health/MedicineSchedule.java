package com.gmsmartplanner.entity.health;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Entity
@Table(
        name =
                "medicine_schedules"
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

    @Column(
            name =
                    "slot_name"
    )
    private String slotName;

    @Column(
            name =
                    "schedule_time"
    )
    private LocalTime time;

    @Column(
            name =
                    "dose_count"
    )
    private Integer doseCount;

    @Column(
            name = "is_active",
            nullable = false
    )
    private Boolean active = true;

}