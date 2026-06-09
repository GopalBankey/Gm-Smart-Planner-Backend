package com.gmsmartplanner.service.impl;

import com.gmsmartplanner.enums.NotificationType;
import com.gmsmartplanner.service.FirebaseNotificationService;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class FirebaseNotificationServiceImpl
        implements FirebaseNotificationService {

    // =====================================
    // SEND TO TOKEN
    // =====================================
    @Override
    public void sendToToken(

            String fcmToken,

            String title,

            String body

    ) {

        try {

            Notification notification =
                    Notification.builder()

                            .setTitle(title)

                            .setBody(body)

                            .build();

            Message message =
                    Message.builder()

                            .setToken(fcmToken)

                            .setNotification(
                                    notification
                            )

                            .build();

            String response =
                    FirebaseMessaging.getInstance()
                            .send(message);

            log.info(
                    "Notification sent successfully : {}",
                    response
            );

        } catch (FirebaseMessagingException e) {

            log.warn(
                    "FCM notification failed. Token may be invalid : {}",
                    fcmToken
            );

            // Optional:
            // Remove token from database if UNREGISTERED

        }
        catch (Exception e) {

            log.error(
                    "Unexpected notification error",
                    e
            );
        }
    }

    // =====================================
    // TASK REMINDER
    // =====================================
    @Override
    public void sendTaskReminder(

            String fcmToken,

            String taskTitle

    ) {

        sendToToken(

                fcmToken,

                "Task Reminder",

                "Reminder for task : "
                        + taskTitle
        );
    }

    // =====================================
    // TASK SHARED
    // =====================================
    @Override
    public void sendTaskShared(

            String fcmToken,

            String taskTitle,

            String sharedBy

    ) {

        sendToToken(

                fcmToken,

                "Task Shared",

                sharedBy
                        + " shared a task with you : "
                        + taskTitle
        );
    }

    // =====================================
    // COMMENT NOTIFICATION
    // =====================================
    @Override
    public void sendCommentNotification(

            String fcmToken,

            String taskTitle,

            String commentedBy

    ) {

        sendToToken(

                fcmToken,

                "New Comment",

                commentedBy
                        + " commented on task : "
                        + taskTitle
        );
    }

    // =====================================
    // TASK COMPLETED
    // =====================================
    @Override
    public void sendTaskCompleted(

            String fcmToken,

            String taskTitle,

            String completedBy

    ) {

        sendToToken(

                fcmToken,

                "Task Completed",

                completedBy
                        + " completed task : "
                        + taskTitle
        );
    }

    // =====================================
    // COMMON NOTIFICATION
    // =====================================
    @Override
    public void sendNotification(

            String fcmToken,

            String title,

            String body,

            Long todoId,

            NotificationType type

    ) {

        try {

            Message message =
                    Message.builder()

                            .setToken(fcmToken)

                            .setNotification(

                                    Notification.builder()

                                            .setTitle(title)

                                            .setBody(body)

                                            .build()
                            )

                            // =====================================
                            // DATA PAYLOAD
                            // =====================================

                            .putData(
                                    "todoId",
                                    String.valueOf(todoId)
                            )

                            .putData(
                                    "type",
                                    type.name()
                            )

                            .build();

            String response =
                    FirebaseMessaging
                            .getInstance()
                            .send(message);

            log.info(
                    "Notification sent successfully : {}",
                    response
            );

        } catch (FirebaseMessagingException e) {

            log.warn(
                    "FCM notification failed. Token may be invalid : {}",
                    fcmToken
            );

            // Optional:
            // Remove token from database if UNREGISTERED

        }
        catch (Exception e) {

            log.error(
                    "Unexpected notification error",
                    e
            );
        }
    }
}