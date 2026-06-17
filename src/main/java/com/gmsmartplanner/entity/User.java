package com.gmsmartplanner.entity;

import com.gmsmartplanner.enums.BloodGroup;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(
        name = "users",

        indexes = {

                @Index(
                        name = "idx_user_email",
                        columnList = "email"
                ),

                @Index(
                        name = "idx_user_mobile",
                        columnList = "mobile_number"
                )
        }
)
@Getter
@Setter
public class User extends BaseEntity {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;

    // =====================================
    // BASIC DETAILS
    // =====================================

    @Column(length = 50)
    private String name;

    @Column(
            unique = true,
            length = 100
    )
    private String email;

    @Column(
            unique = true,
            length = 10,
            name = "mobile_number"
    )
    private String mobileNumber;

    @Column(length = 500, name = "profile_image_url")
    private String profileImageUrl;

    @Column(
            name = "country_code",
            nullable = false
    )
    private String countryCode = "+91";

    // =====================================
    // PROFILE DETAILS
    // =====================================

    private LocalDate dob;

    private Integer age;

    @Enumerated(EnumType.STRING)
    @Column(name = "blood_group")
    private BloodGroup bloodGroup;

    @Column(length = 500)
    private String note;

    // =====================================
    // STATUS
    // =====================================

    @Column(nullable = false,name = "profile_completed")
    private boolean profileCompleted = false;
}