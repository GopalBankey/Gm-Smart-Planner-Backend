package com.gmsmartplanner.dto.request.todo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateSubTaskRequestDTO {

    // NULL = NEW SUB TASK
    private Long id;

    private String title;

    private Boolean completed;
}