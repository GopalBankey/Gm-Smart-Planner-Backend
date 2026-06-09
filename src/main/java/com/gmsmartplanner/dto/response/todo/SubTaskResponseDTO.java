package com.gmsmartplanner.dto.response.todo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class SubTaskResponseDTO {

    private Long id;

    private String title;

    private boolean completed;

    private LocalDateTime completedAt;

    private Integer displayOrder;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}