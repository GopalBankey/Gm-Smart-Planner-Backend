package com.gmsmartplanner.repository.todo;

import com.gmsmartplanner.entity.todo.Todo;
import com.gmsmartplanner.entity.todo.TodoReminder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TodoReminderRepository
        extends JpaRepository<TodoReminder, Long> {

    // =====================================
    // GET TODO REMINDERS
    // =====================================

    List<TodoReminder>
    findAllByTodoAndActiveTrue(
            Todo todo
    );

    // =====================================
    // GET PENDING REMINDERS
    // =====================================

    List<TodoReminder>
    findAllBySentFalseAndActiveTrueAndReminderTimeBefore(

            LocalDateTime currentTime
    );

    // =====================================
    // GET ACTIVE REMINDERS
    // =====================================

    List<TodoReminder>
    findAllByActiveTrueAndReminderTimeAfter(

            LocalDateTime currentTime
    );

    // =====================================
    // COUNT REMINDERS
    // =====================================

    long countByTodoAndActiveTrue(
            Todo todo
    );

    // =====================================
    // DELETE TODO REMINDERS
    // =====================================

    void deleteAllByTodo(
            Todo todo
    );

    List<TodoReminder>
    findAllByActiveTrueAndSentFalseAndReminderTimeBefore(

            LocalDateTime time
    );
}