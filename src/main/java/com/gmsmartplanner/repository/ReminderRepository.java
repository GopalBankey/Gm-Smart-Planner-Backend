package com.gmsmartplanner.repository.todo;

import com.gmsmartplanner.entity.todo.TodoReminder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ReminderRepository
        extends JpaRepository<TodoReminder, Long> {

    List<TodoReminder>
    findAllByActiveTrueAndSentFalseAndReminderTimeBefore(

            LocalDateTime time
    );
}