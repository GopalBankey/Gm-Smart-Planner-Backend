package com.gmsmartplanner.entity;

import com.gmsmartplanner.enums.NotificationReferenceType;
import com.gmsmartplanner.enums.NotificationType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(
        name = "notifications",

        indexes = {

                @Index(
                        name =
                                "idx_notification_user",

                        columnList =
                                "user_id"
                ),

                @Index(
                        name =
                                "idx_notification_type",

                        columnList =
                                "type"
                ),

                @Index(
                        name =
                                "idx_notification_reference",

                        columnList =
                                "reference_id"
                ),

                @Index(
                        name =
                                "idx_notification_read",

                        columnList =
                                "is_read"
                )
        }
)
@Getter
@Setter
public class Notification
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
                    FetchType.LAZY,

            optional =
                    false
    )

    @JoinColumn(

            name =
                    "user_id",

            nullable =
                    false
    )

    private User user;

    // =====================================
    // TYPE
    // =====================================

    @Enumerated(
            EnumType.STRING
    )

    @Column(

            nullable =
                    false,

            length =
                    50
    )

    private NotificationType type;

    // =====================================
    // REFERENCE
    // =====================================

    @Column(
            name =
                    "reference_id"
    )

    private Long referenceId;

    @Enumerated(
            EnumType.STRING
    )

    @Column(

            name =
                    "reference_type",

            length =
                    50
    )

    private NotificationReferenceType
            referenceType;

    // =====================================
    // CONTENT
    // =====================================

    @Column(

            nullable =
                    false,

            length =
                    150
    )

    private String title;

    @Column(

            nullable =
                    false,

            length =
                    500
    )

    private String message;

    // =====================================
    // OPTIONAL
    // =====================================

    @Column(
            length =
                    500
    )

    private String imageUrl;

    @Column(
            length =
                    255
    )

    private String action;

    // =====================================
    // STATUS
    // =====================================

    @Column(
            name =
                    "is_read",

            nullable =
                    false
    )

    private Boolean read =
            false;

    @Column(
            nullable =
                    false
    )

    private Boolean sent =
            false;

    @Column(
            nullable =
                    false
    )

    private Boolean deleted =
            false;
}