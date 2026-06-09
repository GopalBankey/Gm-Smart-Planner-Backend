package com.gmsmartplanner.entity.todo;

import com.gmsmartplanner.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "todo_checklists",

        indexes = {

                @Index(
                        name = "idx_checklist_todo",
                        columnList = "todo_id"
                ),

                @Index(
                        name = "idx_checklist_completed",
                        columnList = "completed"
                )
        }
)
@Getter
@Setter
public class TodoChecklist
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
    // CHECKLIST ITEM
    // =====================================

    @Column(
            nullable = false,
            length = 300
    )
    private String title;

    // =====================================
    // STATUS
    // =====================================

    @Column(nullable = false)
    private boolean completed = false;

    private LocalDateTime completedAt;

    // =====================================
    // ORDERING
    // =====================================

    @Column(name = "display_order")
    private Integer displayOrder;

    // =====================================
    // SOFT DELETE
    // =====================================

    @Column(nullable = false)
    private boolean deleted = false;
}