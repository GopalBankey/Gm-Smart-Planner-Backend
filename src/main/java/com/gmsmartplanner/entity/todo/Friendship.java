package com.gmsmartplanner.entity.todo;

import com.gmsmartplanner.entity.BaseEntity;
import com.gmsmartplanner.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(
        name = "friendships",

        uniqueConstraints = {

                @UniqueConstraint(
                        columnNames = {
                                "user_id",
                                "friend_id"
                        }
                )
        },

        indexes = {

                @Index(
                        name = "idx_friend_user",
                        columnList = "user_id"
                ),

                @Index(
                        name = "idx_friend_friend",
                        columnList = "friend_id"
                )
        }
)
@Getter
@Setter
public class Friendship
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
            fetch = FetchType.LAZY
    )
    @JoinColumn(
            name = "user_id",
            nullable = false
    )
    private User user;

    // =====================================
    // FRIEND
    // =====================================

    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(
            name = "friend_id",
            nullable = false
    )
    private User friend;
}