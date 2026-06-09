package com.gmsmartplanner.service.impl;

import com.gmsmartplanner.dto.response.NotificationResponseDTO;
import com.gmsmartplanner.entity.todo.TodoNotification;
import com.gmsmartplanner.entity.User;
import com.gmsmartplanner.exception.InvalidRequestException;
import com.gmsmartplanner.exception.ResourceNotFoundException;
import com.gmsmartplanner.mapper.todo.TodoMapper;
import com.gmsmartplanner.repository.todo.TodoNotificationRepository;
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

    private final TodoNotificationRepository
            todoNotificationRepository;

    private final UserRepository
            userRepository;

    private final TodoMapper
            todoMapper;

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
                .map(todoMapper::mapToNotificationResponse);
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
        TodoNotification notification =
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


}