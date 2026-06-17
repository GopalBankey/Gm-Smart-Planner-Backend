package com.gmsmartplanner.service.impl;

import com.gmsmartplanner.dto.response.NotificationResponseDTO;
import com.gmsmartplanner.entity.Notification;
import com.gmsmartplanner.entity.User;
import com.gmsmartplanner.exception.InvalidRequestException;
import com.gmsmartplanner.exception.ResourceNotFoundException;
import com.gmsmartplanner.mapper.ReminderNotificationMapper;
import com.gmsmartplanner.mapper.todo.TodoMapper;
import com.gmsmartplanner.repository.NotificationRepository;
import com.gmsmartplanner.repository.UserRepository;
import com.gmsmartplanner.service.NotificationService;
import com.gmsmartplanner.service.UserHelperService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class NotificationServiceImpl
        implements NotificationService {

    private final NotificationRepository
            todoNotificationRepository;

    private final UserRepository
            userRepository;

    private final ReminderNotificationMapper
            reminderNotificationMapper;

    private final UserHelperService
            userHelperService;

    // =====================================
    // GET NOTIFICATIONS
    // =====================================
    @Override
    @Transactional(readOnly = true)
    public Page<NotificationResponseDTO>
    getNotifications(

            String username,

            Pageable pageable

    ) {

        User user =
                userHelperService
                        .getCurrentUser(
                                username
                        );

        return todoNotificationRepository
                .findAllByUserAndDeletedFalseOrderByCreatedAtDesc(

                        user,

                        pageable
                )
                .map(reminderNotificationMapper::mapToNotificationResponse);
    }

    // =====================================
    // MARK AS READ
    // =====================================
    @Override
    public void markNotificationAsRead(

            String username,

            Long notificationId

    ) {

        User user =
                userHelperService
                        .getCurrentUser(
                                username
                        );
        Notification notification =
                todoNotificationRepository
                        .findById(notificationId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Notification not found"
                                )
                        );

        // VALIDATE OWNER
        if (!notification.getUser()
                .getId()
                .equals(user.getId())) {

            throw new InvalidRequestException(
                    "Invalid notification access"
            );
        }

        notification.setRead(true);

        todoNotificationRepository.save(
                notification
        );
    }

    // =====================================
    // GET UNREAD COUNT
    // =====================================
    @Override
    @Transactional(readOnly = true)
    public long getUnreadNotificationCount(
            String username
    ) {

        User user =
                userHelperService
                        .getCurrentUser(
                                username
                        );

        return todoNotificationRepository
                .countByUserAndReadFalseAndDeletedFalse(
                        user
                );
    }



    @Override
    public void markAllNotificationsAsRead(

            String username

    ) {

        User user =
                userHelperService
                        .getCurrentUser(
                                username
                        );

        var notifications =
                todoNotificationRepository
                        .findAllByUserAndReadFalseAndDeletedFalse(
                                user
                        );

        notifications.forEach(notification ->
                notification.setRead(true)
        );

        todoNotificationRepository
                .saveAll(
                        notifications
                );

    }


}