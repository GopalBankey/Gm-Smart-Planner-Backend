package com.gmsmartplanner.entity.health;

import com.gmsmartplanner.entity.BaseEntity;
import com.gmsmartplanner.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(
        name = "health_reports"
)
@Getter
@Setter
public class HealthReport
        extends BaseEntity {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;

    // USER

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "user_id",
            nullable = false
    )
    private User user;

    // DOCTOR

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "doctor_id"
    )
    private Doctor doctor;

    // REPORT

    @Column(
            name = "report_name",
            nullable = false
    )
    private String reportName;

    private String labName;

    private String countryCode = "+91";

    private String labMobileNumber;

    private String labAddress;

    private LocalDate reportDate;

    private LocalTime reportTime;

    @Column(
            columnDefinition = "TEXT"
    )
    private String notes;

    // SINGLE FILE

    private String reportFile;

    @Column(nullable = false)
    private boolean active = true;
}