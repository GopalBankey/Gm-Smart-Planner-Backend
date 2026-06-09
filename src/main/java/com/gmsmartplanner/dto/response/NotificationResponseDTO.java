package com.gmsmartplanner.dto.response;

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

    private NotificationType type;

    private String title;

    private String message;

    private boolean read;

    private boolean sent;

    private LocalDateTime createdAt;
}