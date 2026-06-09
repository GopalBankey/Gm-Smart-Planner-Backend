package com.gmsmartplanner.entity.todo;

import com.gmsmartplanner.entity.BaseEntity;
import com.gmsmartplanner.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(
        name = "todo_comments",

        indexes = {

                @Index(
                        name = "idx_todo_comment_todo",
                        columnList = "todo_id"
                ),

                @Index(
                        name = "idx_todo_comment_user",
                        columnList = "user_id"
                )
        }
)
@Getter
@Setter
public class TodoComment
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
    // COMMENTED BY
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
    // COMMENT
    // =====================================

    @Column(
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String comment;

    // =====================================
    // STATUS
    // =====================================

    @Column(nullable = false)
    private boolean edited = false;

    @Column(nullable = false)
    private boolean deleted = false;
}