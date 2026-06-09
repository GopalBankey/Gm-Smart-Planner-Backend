package com.gmsmartplanner.dto.request.todo;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ShareTodoRequestDTO {

    @NotEmpty(message = "At least one user is required")
    private List<Long> userIds =
            new ArrayList<>();
}