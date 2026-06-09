package com.gmsmartplanner.service;

import com.gmsmartplanner.enums.NotificationType;

public interface FirebaseNotificationService {

    // =====================================
    // SEND TO SINGLE DEVICE
    // =====================================

    void sendToToken(

            String fcmToken,

            String title,

            String body
    );

    // =====================================
    // TASK REMINDER
    // =====================================

    void sendTaskReminder(

            String fcmToken,

            String taskTitle
    );

    // =====================================
    // TASK SHARED
    // =====================================

    void sendTaskShared(

            String fcmToken,

            String taskTitle,

            String sharedBy
    );

    // =====================================
    // COMMENT NOTIFICATION
    // =====================================

    void sendCommentNotification(

            String fcmToken,

            String taskTitle,

            String commentedBy
    );

    // =====================================
    // TASK COMPLETED
    // =====================================

    void sendTaskCompleted(

            String fcmToken,

            String taskTitle,

            String completedBy
    );

    // =====================================
    // COMMON NOTIFICATION
    // =====================================

    void sendNotification(

            String fcmToken,

            String title,

            String body,

            Long todoId,

            NotificationType type
    );
}