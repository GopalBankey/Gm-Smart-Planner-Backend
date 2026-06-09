package com.gmsmartplanner.entity.health;

import com.gmsmartplanner.entity.BaseEntity;
import com.gmsmartplanner.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Entity
@Table(
        name = "hospitals",

        indexes = {

                @Index(
                        name = "idx_hospital_user",
                        columnList = "user_id"
                )
        }
)
@Getter
@Setter
public class Hospital
        extends BaseEntity {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;

    // =====================================
    // USER
    // =====================================

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "user_id",
            nullable = false
    )
    private User user;

    // =====================================
    // HOSPITAL NAME
    // =====================================

    @Column(
            name = "hospital_name",
            nullable = false
    )
    private String hospitalName;

    // =====================================
    // COUNTRY CODE
    // =====================================

    @Column(
            name = "country_code",
            nullable = false
    )
    private String countryCode = "+91";

    // =====================================
    // MOBILE NUMBER
    // =====================================

    @Column(
            name = "mobile_number",
            nullable = false
    )
    private String mobileNumber;

    // =====================================
    // OPENING TIME
    // =====================================

    @Column(
            name = "opening_time",
            nullable = false
    )
    private String openingTime;

    // =====================================
    // CLOSING TIME
    // =====================================

    @Column(
            name = "closing_time",
            nullable = false
    )
    private String closingTime;

    // =====================================
    // ADDRESS
    // =====================================

    @Column(
            name = "hospital_address",
            columnDefinition = "TEXT",
            nullable = false
    )
    private String address;

    // =====================================
    // ACTIVE
    // =====================================

    @Column(
            name = "is_active",
            nullable = false
    )
    private boolean active = true;
}