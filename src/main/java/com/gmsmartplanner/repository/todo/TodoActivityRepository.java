package com.gmsmartplanner.repository.todo;

import com.gmsmartplanner.entity.todo.Todo;
import com.gmsmartplanner.entity.todo.TodoActivity;
import com.gmsmartplanner.entity.User;
import com.gmsmartplanner.enums.todo.TodoActivityType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoActivityRepository
        extends JpaRepository<TodoActivity, Long> {

    // =====================================
    // GET TODO ACTIVITIES
    // =====================================

    Page<TodoActivity>
    findAllByTodoOrderByCreatedAtDesc(

            Todo todo,

            Pageable pageable
    );

    // =====================================
    // GET USER ACTIVITIES
    // =====================================

    Page<TodoActivity>
    findAllByUserOrderByCreatedAtDesc(

            User user,

            Pageable pageable
    );

    // =====================================
    // GET BY ACTIVITY TYPE
    // =====================================

    Page<TodoActivity>
    findAllByTodoAndActivityTypeOrderByCreatedAtDesc(

            Todo todo,

            TodoActivityType activityType,

            Pageable pageable
    );

    // =====================================
    // COUNT ACTIVITIES
    // =====================================

    long countByTodo(
            Todo todo
    );
}