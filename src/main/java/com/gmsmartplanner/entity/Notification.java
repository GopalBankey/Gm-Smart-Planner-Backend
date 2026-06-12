package com.gmsmartplanner.entity.todo;

import com.gmsmartplanner.entity.BaseEntity;
import com.gmsmartplanner.entity.User;
import com.gmsmartplanner.enums.NotificationType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(
        name = "notifications",

        indexes = {

                @Index(
                        name = "idx_notification_user",
                        columnList = "user_id"
                ),

                @Index(
                        name = "idx_notification_todo",
                        columnList = "todo_id"
                ),

                @Index(
                        name = "idx_notification_read",
                        columnList = "read"
                ),

                @Index(
                        name = "idx_notification_type",
                        columnList = "type"
                )
        }
)
@Getter
@Setter
public class Notification
        extends BaseEntity {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;

    // =====================================
    // USER
    // =====================================

    @ManyToOne(
            fetch = FetchType.LAZY,
            optional = false
    )
    @JoinColumn(
            name = "user_id",
            nullable = false
    )
    private User user;

    // =====================================
    // TODO
    // =====================================

    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "todo_id")
    private Todo todo;

    // =====================================
    // NOTIFICATION TYPE
    // =====================================

    @Enumerated(EnumType.STRING)
    @Column(
            nullable = false,
            length = 50
    )
    private NotificationType type;

    // =====================================
    // TITLE
    // =====================================

    @Column(
            nullable = false,
            length = 150
    )
    private String title;

    // =====================================
    // MESSAGE
    // =====================================

    @Column(
            nullable = false,
            length = 500
    )
    private String message;

    // =====================================
    // STATUS
    // =====================================

    @Column(nullable = false)
    private boolean read = false;

    @Column(nullable = false)
    private boolean sent = false;

    @Column(nullable = false)
    private boolean deleted = false;
}