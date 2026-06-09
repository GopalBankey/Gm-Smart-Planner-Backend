package com.gmsmartplanner.service;

import com.gmsmartplanner.dto.response.NotificationResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NotificationService {

    Page<NotificationResponseDTO>
    getNotifications(

            String username,

            Pageable pageable
    );

    void markNotificationAsRead(

            String username,

            Long notificationId
    );

    long getUnreadNotificationCount(
            String username
    );
}