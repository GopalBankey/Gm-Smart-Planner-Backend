package com.gmsmartplanner.entity.todo;

import com.gmsmartplanner.entity.BaseEntity;
import com.gmsmartplanner.entity.User;
import com.gmsmartplanner.enums.todo.TodoPriority;
import com.gmsmartplanner.enums.todo.TodoStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "todos",

        indexes = {

                @Index(
                        name = "idx_todo_owner",
                        columnList = "owner_id"
                ),

                @Index(
                        name = "idx_todo_status",
                        columnList = "status"
                ),

                @Index(
                        name = "idx_todo_priority",
                        columnList = "priority"
                ),

                @Index(
                        name = "idx_todo_task_date_time",
                        columnList = "task_date_time"
                ),

                @Index(
                        name = "idx_todo_completed",
                        columnList = "completed"
                ),

                @Index(
                        name = "idx_todo_shared_task_id",
                        columnList = "shared_task_id"
                )
        }
)
@Getter
@Setter
public class Todo
        extends BaseEntity {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;

    // =====================================
    // BASIC DETAILS
    // =====================================

    @Column(
            nullable = false,
            length = 150
    )
    private String title;

    @Column(
            columnDefinition = "TEXT"
    )
    private String description;

    // =====================================
    // STATUS
    // =====================================

    @Enumerated(EnumType.STRING)
    @Column(
            nullable = false,
            length = 20
    )
    private TodoStatus status =
            TodoStatus.PENDING;

    @Enumerated(EnumType.STRING)
    @Column(
            nullable = false,
            length = 20
    )
    private TodoPriority priority =
            TodoPriority.MEDIUM;

    @Column(nullable = false)
    private boolean completed = false;

    private LocalDateTime completedAt;

    // =====================================
    // TASK DATE & TIME
    // =====================================

    @Column(name = "task_date_time")
    private LocalDateTime taskDateTime;

    // =====================================
    // SHARED TASK ID
    // =====================================

    @Column(
            nullable = false,
            unique = true,
            length = 100
    )
    private String sharedTaskId;

    // =====================================
    // SOFT DELETE
    // =====================================

    @Column(nullable = false)
    private boolean deleted = false;

    // =====================================
    // OWNER
    // =====================================

    @ManyToOne(
            fetch = FetchType.LAZY,
            optional = false
    )
    @JoinColumn(
            name = "owner_id",
            nullable = false
    )
    private User owner;
}