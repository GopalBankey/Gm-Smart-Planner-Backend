package com.gmsmartplanner.dto.request.todo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateSubTaskRequestDTO {

    @NotBlank(message = "Sub task title is required")
    @Size(
            min = 1,
            max = 300,
            message = "Sub task title must be between 1 and 300 characters"
    )
    private String title;
}