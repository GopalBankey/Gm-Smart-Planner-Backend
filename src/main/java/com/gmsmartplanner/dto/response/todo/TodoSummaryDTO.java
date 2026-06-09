package com.gmsmartplanner.dto.response.todo;

import com.gmsmartplanner.enums.todo.TodoPriority;
import com.gmsmartplanner.enums.todo.TodoStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class TodoSummaryDTO {

    private Long id;

    private String title;

    private TodoStatus status;

    private TodoPriority priority;

    private boolean completed;

    private LocalDateTime taskDateTime;

    private LocalDateTime createdAt;
}