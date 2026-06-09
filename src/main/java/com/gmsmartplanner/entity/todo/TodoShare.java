package com.gmsmartplanner.entity.todo;

import com.gmsmartplanner.entity.BaseEntity;
import com.gmsmartplanner.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(
        name = "todo_shares",

        uniqueConstraints = {

                @UniqueConstraint(
                        name = "uk_todo_shared_user",
                        columnNames = {
                                "todo_id",
                                "shared_with_user_id"
                        }
                )
        },

        indexes = {

                @Index(
                        name = "idx_todo_share_todo",
                        columnList = "todo_id"
                ),

                @Index(
                        name = "idx_todo_share_user",
                        columnList = "shared_with_user_id"
                )
        }
)
@Getter
@Setter
public class TodoShare
        extends BaseEntity {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;

    // =====================================
    // TODO
    // =====================================

    @ManyToOne(
            fetch = FetchType.LAZY,
            optional = false
    )
    @JoinColumn(
            name = "todo_id",
            nullable = false
    )
    private Todo todo;

    // =====================================
    // SHARED WITH USER
    // =====================================

    @ManyToOne(
            fetch = FetchType.LAZY,
            optional = false
    )
    @JoinColumn(
            name = "shared_with_user_id",
            nullable = false
    )
    private User sharedWithUser;

    // =====================================
    // PERMISSIONS
    // =====================================

    @Column(nullable = false)
    private boolean canEdit = false;

    @Column(nullable = false)
    private boolean canComplete = true;

    @Column(nullable = false)
    private boolean canComment = true;

    // =====================================
    // STATUS
    // =====================================

    @Column(nullable = false)
    private boolean active = true;
}