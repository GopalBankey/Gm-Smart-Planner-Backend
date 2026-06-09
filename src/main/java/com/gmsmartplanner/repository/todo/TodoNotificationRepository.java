package com.gmsmartplanner.repository.todo;

import com.gmsmartplanner.entity.todo.TodoNotification;
import com.gmsmartplanner.entity.User;
import com.gmsmartplanner.enums.NotificationType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoNotificationRepository
        extends JpaRepository<TodoNotification, Long> {

    // =====================================
    // GET USER NOTIFICATIONS
    // =====================================

    Page<TodoNotification>
    findAllByUserOrderByCreatedAtDesc(

            User user,

            Pageable pageable
    );

    // =====================================
    // GET UNREAD NOTIFICATIONS
    // =====================================

    Page<TodoNotification>
    findAllByUserAndReadFalseOrderByCreatedAtDesc(

            User user,

            Pageable pageable
    );

    // =====================================
    // GET READ NOTIFICATIONS
    // =====================================

    Page<TodoNotification>
    findAllByUserAndReadTrueOrderByCreatedAtDesc(

            User user,

            Pageable pageable
    );

    // =====================================
    // GET NOTIFICATION BY TYPE
    // =====================================

    Page<TodoNotification>
    findAllByUserAndTypeOrderByCreatedAtDesc(

            User user,

            NotificationType type,

            Pageable pageable
    );

    // =====================================
    // COUNT UNREAD NOTIFICATIONS
    // =====================================

    long countByUserAndReadFalse(
            User user
    );

    // =====================================
    // DELETE USER NOTIFICATIONS
    // =====================================

    void deleteAllByUser(
            User user
    );

    // =====================================
    // GET USER NOTIFICATIONS
    // =====================================

    Page<TodoNotification>
    findAllByUserAndDeletedFalseOrderByCreatedAtDesc(

            User user,

            Pageable pageable
    );

    // =====================================
    // UNREAD NOTIFICATION COUNT
    // =====================================

    long countByUserAndReadFalseAndDeletedFalse(
            User user
    );
}