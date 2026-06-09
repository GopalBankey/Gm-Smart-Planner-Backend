package com.gmsmartplanner.dto.response.todo;

import com.gmsmartplanner.dto.response.UserResponseDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class CommentResponseDTO {

    private Long id;

    private String comment;

    private boolean edited;

    private boolean deleted;

    // =====================================
    // COMMENTED BY
    // =====================================

    private UserResponseDTO user;

    // =====================================
    // AUDIT
    // =====================================

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}