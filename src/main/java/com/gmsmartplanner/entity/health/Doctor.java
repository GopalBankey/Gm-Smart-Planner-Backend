package com.gmsmartplanner.entity.health;

import com.gmsmartplanner.entity.BaseEntity;
import com.gmsmartplanner.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(
        name = "doctors",
        indexes = {
                @Index(
                        name = "idx_doctor_user",
                        columnList = "user_id"
                )
        }
)
@Getter
@Setter
public class Doctor extends BaseEntity {

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
            name = "doctor_name",
            nullable = false
    )
    private String doctorName;

    @Column(
            name = "country_code",
            nullable = false
    )
    private String countryCode = "+91";

    @Column(
            name = "mobile_number",
            nullable = false
    )
    private String mobileNumber;

    @Column(nullable = false)
    private String specialization;

    @Column(nullable = false)
    private boolean active = true;
}