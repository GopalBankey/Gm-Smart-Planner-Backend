package com.gmsmartplanner.service.impl;

import com.gmsmartplanner.entity.Notification;
import com.gmsmartplanner.entity.User;
import com.gmsmartplanner.enums.NotificationReferenceType;
import com.gmsmartplanner.enums.NotificationType;
import com.gmsmartplanner.repository.NotificationRepository;
import com.gmsmartplanner.service.NotificationHelperService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationHelperServiceImpl
        implements NotificationHelperService {

    private final NotificationRepository
            notificationRepository;

    @Override
    public void createNotification(

            User user,

            Long referenceId,

            NotificationReferenceType referenceType,

            String title,

            String message,

            NotificationType type

    ) {

        Notification notification =
                new Notification();

        // =========================
        // USER
        // =========================

        notification.setUser(
                user
        );

        // =========================
        // REFERENCE
        // =========================

        notification.setReferenceId(
                referenceId
        );

        notification.setReferenceType(
                referenceType
        );

        // =========================
        // CONTENT
        // =========================

        notification.setTitle(
                title
        );

        notification.setMessage(
                message
        );

        notification.setType(
                type
        );

        // =========================
        // STATUS
        // =========================

        notification.setRead(
                false
        );

        notification.setSent(
                true
        );

        notification.setDeleted(
                false
        );

        notificationRepository
                .save(
                        notification
                );
    }
}