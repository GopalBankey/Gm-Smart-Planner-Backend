package com.gmsmartplanner.entity;

import com.gmsmartplanner.enums.LoginType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(
        name = "user_auth",

        indexes = {

                @Index(
                        name = "idx_firebase_uid",
                        columnList = "firebaseUid"
                ),

                @Index(
                        name = "idx_login_type",
                        columnList = "loginType"
                )
        }
)
@Getter
@Setter
public class UserAuth extends BaseEntity {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;

    // =====================================
    // RELATION
    // =====================================

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "user_id",
            nullable = false,
            unique = true
    )
    private User user;

    // =====================================
    // MOBILE OTP
    // =====================================

    @Column(length = 10)
    private String otp;

    @Column(
            nullable = false,
            name = "otp_verified"
    )
    private boolean otpVerified = false;

    // =====================================
    // GOOGLE LOGIN
    // =====================================

    @Column(
            length = 255,
            name = "firebase_uid"
    )
    private String firebaseUid;

    @Enumerated(EnumType.STRING)
    @Column(
            length = 20,
            name = "login_type"
    )
    private LoginType loginType;

    // =====================================
    // EMAIL VERIFICATION
    // =====================================

    @Column(
            nullable = false,
            name = "email_verified"
    )
    private boolean emailVerified = false;

    @Column(
            length = 10,
            name = "email_otp"
    )
    private String emailOtp;

    @Column(
            nullable = false,
            name = "email_otp_verified"
    )
    private boolean emailOtpVerified = false;

    // =====================================
    // JWT
    // =====================================

    @Column(
            length = 1000,
            name = "jwt_token"
    )
    private String jwtToken;

    // =====================================
    // FCM
    // =====================================

    @Column(
            length = 500,
            name = "fcm_token"
    )
    private String fcmToken;
}