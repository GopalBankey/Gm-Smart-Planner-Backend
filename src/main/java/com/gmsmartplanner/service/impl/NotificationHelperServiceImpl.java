package com.gmsmartplanner.service.impl;

import com.gmsmartplanner.entity.todo.Todo;
import com.gmsmartplanner.entity.todo.TodoNotification;
import com.gmsmartplanner.entity.User;
import com.gmsmartplanner.enums.NotificationType;
import com.gmsmartplanner.repository.todo.TodoNotificationRepository;
import com.gmsmartplanner.service.NotificationHelperService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationHelperServiceImpl
        implements NotificationHelperService {

    private final TodoNotificationRepository
            todoNotificationRepository;

    @Override
    public void createNotification(

            User user,

            Todo todo,

            String title,

            String message,

            NotificationType type

    ) {

        TodoNotification notification =
                new TodoNotification();

        notification.setUser(user);

        notification.setTodo(todo);

        notification.setTitle(title);

        notification.setMessage(message);

        notification.setType(type);

        notification.setRead(false);

        notification.setSent(true);

        notification.setDeleted(false);

        todoNotificationRepository.save(
                notification
        );
    }
}