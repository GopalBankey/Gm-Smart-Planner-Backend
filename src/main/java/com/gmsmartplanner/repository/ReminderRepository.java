package com.gmsmartplanner.repository;

import com.gmsmartplanner.entity.Reminder;
import com.gmsmartplanner.enums.NotificationReferenceType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ReminderRepository
        extends JpaRepository<Reminder, Long> {

    // =====================================
    // PENDING
    // =====================================

    List<Reminder>
    findAllByActiveTrueAndSentFalseAndReminderTimeBefore(

            LocalDateTime time
    );

    // =====================================
    // BY REFERENCE
    // =====================================

    List<Reminder>
    findAllByReferenceIdAndReferenceType(

            Long referenceId,

            NotificationReferenceType
                    referenceType
    );

    // =====================================
    // DELETE
    // =====================================

    void deleteAllByReferenceIdAndReferenceType(

            Long referenceId,

            NotificationReferenceType
                    referenceType
    );
}