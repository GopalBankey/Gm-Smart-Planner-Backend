package com.gmsmartplanner.dto.request.todo;

import com.gmsmartplanner.enums.todo.TodoPriority;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CreateTodoRequestDTO {

    // =====================================
    // BASIC DETAILS
    // =====================================

    @NotBlank(message = "Title is required")
    @Size(
            min = 2,
            max = 150,
            message = "Title must be between 2 and 150 characters"
    )
    private String title;

    @Size(
            max = 5000,
            message = "Description cannot exceed 5000 characters"
    )
    private String description;

    // =====================================
    // PRIORITY
    // =====================================

    private TodoPriority priority =
            TodoPriority.MEDIUM;

    // =====================================
    // TASK DATE & TIME
    // =====================================

    private LocalDateTime taskDateTime;

    // =====================================
    // SHARED USERS
    // =====================================

    private List<Long> sharedUserIds =
            new ArrayList<>();

    // =====================================
    // SUB TASKS
    // =====================================

    @Valid
    private List<CreateSubTaskRequestDTO>
            subTasks =
            new ArrayList<>();
}