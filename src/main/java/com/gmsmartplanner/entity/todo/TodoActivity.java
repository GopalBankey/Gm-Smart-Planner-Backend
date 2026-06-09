package com.gmsmartplanner.entity.todo;

import com.gmsmartplanner.entity.BaseEntity;
import com.gmsmartplanner.entity.User;
import com.gmsmartplanner.enums.todo.TodoActivityType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(
        name = "todo_activities",

        indexes = {

                @Index(
                        name = "idx_activity_todo",
                        columnList = "todo_id"
                ),

                @Index(
                        name = "idx_activity_user",
                        columnList = "user_id"
                ),

                @Index(
                        name = "idx_activity_type",
                        columnList = "activity_type"
                )
        }
)
@Getter
@Setter
public class TodoActivity
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
    // ACTION BY USER
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
    // ACTIVITY TYPE
    // =====================================

    @Enumerated(EnumType.STRING)
    @Column(
            nullable = false,
            length = 50
    )
    private TodoActivityType activityType;

    // =====================================
    // DESCRIPTION
    // =====================================

    @Column(
            nullable = false,
            length = 500
    )
    private String description;
}