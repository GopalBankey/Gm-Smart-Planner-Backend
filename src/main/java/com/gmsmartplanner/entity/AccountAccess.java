package com.gmsmartplanner.entity;

import com.gmsmartplanner.enums.AccessModule;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(
        name = "account_access",

        uniqueConstraints = {

                @UniqueConstraint(
                        columnNames = {
                                "owner_id",
                                "module"
                        }
                )
        },

        indexes = {

                @Index(
                        name =
                                "idx_owner",
                        columnList =
                                "owner_id"
                ),

                @Index(
                        name =
                                "idx_member",
                        columnList =
                                "member_id"
                )
        }
)
@Getter
@Setter
public class AccountAccess
        extends BaseEntity {

    @Id
    @GeneratedValue(
            strategy =
                    GenerationType.IDENTITY
    )
    private Long id;

    // =====================================
    // MODULE
    // =====================================

    @Enumerated(
            EnumType.STRING
    )

    @Column(
            nullable =
                    false,

            length =
                    30
    )

    private AccessModule module =
            AccessModule.HEALTH;

    // =====================================
    // OWNER
    // =====================================

    @ManyToOne(
            fetch =
                    FetchType.LAZY
    )

    @JoinColumn(
            name =
                    "owner_id",

            nullable =
                    false
    )

    private User owner;

    // =====================================
    // MEMBER
    // =====================================

    @ManyToOne(
            fetch =
                    FetchType.LAZY
    )

    @JoinColumn(
            name =
                    "member_id",

            nullable =
                    false
    )

    private User member;

    // =====================================
    // DISPLAY NAME
    // =====================================

    @Column(
            name =
                    "display_name",

            length =
                    100
    )

    private String displayName;

    // =====================================
    // ACCESS OTP
    // =====================================

    @Column(
            name =
                    "otp",

            length =
                    10
    )

    private String otp;

    @Column(
            name =
                    "otp_verified",

            nullable =
                    false
    )

    private Boolean otpVerified =
            false;

    // OPTIONAL
    // future rate limit

//    private LocalDateTime otpSentAt;

    // =====================================
    // PERMISSIONS
    // =====================================

    @Column(
            name =
                    "view_permission"
    )

    private Boolean viewPermission =
            true;

    @Column(
            name =
                    "create_permission"
    )

    private Boolean createPermission =
            false;

    @Column(
            name =
                    "update_permission"
    )

    private Boolean updatePermission =
            false;

    @Column(
            name =
                    "delete_permission"
    )

    private Boolean deletePermission =
            false;

    @Column(
            name =
                    "take_permission"
    )

    private Boolean takePermission =
            false;

    // =====================================
    // ACTIVE
    // =====================================

    @Column(
            name =
                    "active",

            nullable =
                    false
    )

    private Boolean active =
            true;
}