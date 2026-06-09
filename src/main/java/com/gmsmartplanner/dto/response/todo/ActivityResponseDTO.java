package com.gmsmartplanner.dto.response.todo;

import com.gmsmartplanner.dto.response.UserResponseDTO;
import com.gmsmartplanner.enums.todo.TodoActivityType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class ActivityResponseDTO {

    private Long id;

    private TodoActivityType activityType;

    private String description;

    private UserResponseDTO user;

    private LocalDateTime createdAt;
}