package com.gmsmartplanner.service;

import com.gmsmartplanner.entity.todo.Todo;
import com.gmsmartplanner.entity.User;
import com.gmsmartplanner.enums.NotificationType;

public interface NotificationHelperService {

    void createNotification(

            User user,

            Todo todo,

            String title,

            String message,

            NotificationType type
    );
}