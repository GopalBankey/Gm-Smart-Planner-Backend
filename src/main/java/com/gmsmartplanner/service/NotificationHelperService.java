package com.gmsmartplanner.service;

import com.gmsmartplanner.entity.todo.Todo;
import com.gmsmartplanner.entity.User;
import com.gmsmartplanner.enums.NotificationReferenceType;
import com.gmsmartplanner.enums.NotificationType;

public interface NotificationHelperService {

    void createNotification(

            User user,

            Long referenceId,

            NotificationReferenceType referenceType,

            String title,

            String message,

            NotificationType type
    );
}