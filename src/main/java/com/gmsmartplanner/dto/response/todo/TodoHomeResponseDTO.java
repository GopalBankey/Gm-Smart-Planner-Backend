package com.gmsmartplanner.dto.response.todo;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class TodoHomeResponseDTO {

    private List<TodoResponseDTO> allTasks;

    private List<TodoResponseDTO> todayTasks;

    private List<TodoResponseDTO> upcomingTasks;

    private List<TodoResponseDTO> completedTasks;

    private List<TodoResponseDTO> overdueTasks;
}