package com.gmsmartplanner.dto.response;

import com.gmsmartplanner.enums.NotificationReferenceType;
import com.gmsmartplanner.enums.NotificationType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class NotificationResponseDTO {

    private Long id;

    // =====================================
    // TYPE
    // =====================================

    private NotificationType
            type;

    // =====================================
    // REFERENCE
    // =====================================

    private Long
            referenceId;

    private NotificationReferenceType
            referenceType;

    // =====================================
    // CONTENT
    // =====================================

    private String
            title;

    private String
            message;

    private String
            imageUrl;

    private String
            action;

    // =====================================
    // STATUS
    // =====================================

    private Boolean
            read;

    private Boolean
            sent;

    private Boolean
            deleted;

    // =====================================
    // TRACKING
    // =====================================

    private LocalDateTime
            createdAt;

    private LocalDateTime
            updatedAt;
}