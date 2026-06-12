package com.gmsmartplanner.repository.todo;

import com.gmsmartplanner.entity.Notification;
import com.gmsmartplanner.entity.User;
import com.gmsmartplanner.enums.NotificationType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodoNotificationRepository
        extends JpaRepository<Notification, Long> {

    // =====================================
    // GET USER NOTIFICATIONS
    // =====================================

    Page<Notification>
    findAllByUserOrderByCreatedAtDesc(

            User user,

            Pageable pageable
    );

    // =====================================
    // GET UNREAD NOTIFICATIONS
    // =====================================

    Page<Notification>
    findAllByUserAndReadFalseOrderByCreatedAtDesc(

            User user,

            Pageable pageable
    );

    // =====================================
    // GET READ NOTIFICATIONS
    // =====================================

    Page<Notification>
    findAllByUserAndReadTrueOrderByCreatedAtDesc(

            User user,

            Pageable pageable
    );

    // =====================================
    // GET NOTIFICATION BY TYPE
    // =====================================

    Page<Notification>
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

    Page<Notification>
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

    List<Notification> findAllByUserAndReadFalseAndDeletedFalse(User user );
}