package com.gmsmartplanner.dto.response.todo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SharedUserResponseDTO {

    private Long userId;

    private String name;

    private String email;

    private String profileImageUrl;

    // =====================================
    // PERMISSIONS
    // =====================================

    private boolean canEdit;

    private boolean canComplete;

    private boolean canComment;
}