package com.gmsmartplanner.dto.request.todo;

import com.gmsmartplanner.enums.todo.TodoPriority;
import com.gmsmartplanner.enums.todo.TodoStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class UpdateTodoRequestDTO {

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

    // =====================================
    // TASK DATE & TIME
    // =====================================

    private LocalDateTime taskDateTime;

    // =====================================
    // SHARED USERS
    // =====================================

    private List<Long> sharedUserIds;

    // =====================================
    // SUB TASKS
    // =====================================

    private List<UpdateSubTaskRequestDTO>
            subTasks;
}