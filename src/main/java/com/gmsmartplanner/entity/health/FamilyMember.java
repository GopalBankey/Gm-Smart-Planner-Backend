package com.gmsmartplanner.entity.health;

import com.gmsmartplanner.entity.BaseEntity;
import com.gmsmartplanner.entity.User;
import com.gmsmartplanner.enums.BloodGroup;
import com.gmsmartplanner.enums.health.FamilyRelation;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(
        name = "family_members",

        indexes = {

                @Index(
                        name = "idx_family_user",
                        columnList = "user_id"
                )
        }
)
@Getter
@Setter
public class FamilyMember
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
    // PROFILE IMAGE
    // =====================================

    @Column(
            name = "profile_image"
    )
    private String profileImage;

    // =====================================
    // FULL NAME
    // =====================================

    @Column(
            name = "full_name",
            nullable = false
    )
    private String fullName;

    // =====================================
    // RELATION
    // =====================================

    @Enumerated(EnumType.STRING)
    @Column(
            name = "relation",
            nullable = false
    )
    private FamilyRelation relation;

    // =====================================
    // BLOOD GROUP
    // =====================================

    @Enumerated(EnumType.STRING)
    @Column(
            name = "blood_group"
    )
    private BloodGroup bloodGroup;

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
            name = "mobile_number"
    )
    private String mobileNumber;

    // =====================================
    // DATE OF BIRTH
    // =====================================

    @Column(
            name = "date_of_birth"
    )
    private LocalDate dateOfBirth;

    // =====================================
    // ACTIVE
    // =====================================

    @Column(
            name = "is_active",
            nullable = false
    )
    private boolean active = true;

    private Integer age;

    @Column(columnDefinition = "TEXT")
    private String notes;
}