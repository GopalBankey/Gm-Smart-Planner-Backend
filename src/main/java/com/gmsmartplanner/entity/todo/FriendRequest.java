package com.gmsmartplanner.entity.todo;

import com.gmsmartplanner.entity.BaseEntity;
import com.gmsmartplanner.entity.User;
import com.gmsmartplanner.enums.todo.FriendRequestStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(
        name = "friend_requests",

        uniqueConstraints = {

                @UniqueConstraint(
                        columnNames = {
                                "sender_id",
                                "receiver_id"
                        }
                )
        },

        indexes = {

                @Index(
                        name = "idx_sender_id",
                        columnList = "sender_id"
                ),

                @Index(
                        name = "idx_receiver_id",
                        columnList = "receiver_id"
                )
        }
)
@Getter
@Setter
public class FriendRequest
        extends BaseEntity {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;

    // =====================================
    // SENDER
    // =====================================

    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(
            name = "sender_id",
            nullable = false
    )
    private User sender;

    // =====================================
    // RECEIVER
    // =====================================

    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(
            name = "receiver_id",
            nullable = false
    )
    private User receiver;

    // =====================================
    // STATUS
    // =====================================

    @Enumerated(
            EnumType.STRING
    )
    @Column(
            nullable = false,
            length = 20
    )
    private FriendRequestStatus status =
            FriendRequestStatus.PENDING;
}