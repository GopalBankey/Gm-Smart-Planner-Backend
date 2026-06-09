package com.gmsmartplanner.dto.response.todo;

import com.gmsmartplanner.dto.response.UserResponseDTO;
import com.gmsmartplanner.enums.todo.TodoPriority;
import com.gmsmartplanner.enums.todo.TodoStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
public class TodoResponseDTO {

    private Long id;

    // =====================================
    // BASIC DETAILS
    // =====================================

    private String title;

    private String description;

    // =====================================
    // STATUS
    // =====================================

    private TodoStatus status;

    private TodoPriority priority;

    private boolean completed;

    private LocalDateTime completedAt;

    // =====================================
    // TASK DATE & TIME
    // =====================================

    private LocalDateTime taskDateTime;

    // =====================================
    // SHARED TASK
    // =====================================

    private String sharedTaskId;

    // =====================================
    // OWNER
    // =====================================

    private UserResponseDTO owner;

    // =====================================
    // SHARED USERS
    // =====================================

    private List<SharedUserResponseDTO>
            sharedUsers;

    // =====================================
    // SUB TASKS
    // =====================================

    private List<SubTaskResponseDTO>
            subTasks;

    // =====================================
    // AUDIT
    // =====================================

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}