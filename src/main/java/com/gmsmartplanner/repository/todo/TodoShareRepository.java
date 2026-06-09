package com.gmsmartplanner.repository.todo;

import com.gmsmartplanner.entity.todo.Todo;
import com.gmsmartplanner.entity.todo.TodoShare;
import com.gmsmartplanner.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TodoShareRepository
        extends JpaRepository<TodoShare, Long> {

    // =====================================
    // GET ALL SHARED TODOS OF USER
    // =====================================

    List<TodoShare>
    findAllBySharedWithUserAndActiveTrue(
            User sharedWithUser
    );

    // =====================================
    // GET ALL USERS OF TODO
    // =====================================

    List<TodoShare>
    findAllByTodoAndActiveTrue(
            Todo todo
    );

    // =====================================
    // CHECK TODO SHARED
    // =====================================

    boolean existsByTodoAndSharedWithUserAndActiveTrue(

            Todo todo,

            User sharedWithUser
    );

    // =====================================
    // GET SPECIFIC SHARE
    // =====================================

    Optional<TodoShare>
    findByTodoAndSharedWithUserAndActiveTrue(

            Todo todo,

            User sharedWithUser
    );

    // =====================================
    // DELETE ALL SHARES OF TODO
    // =====================================

    void deleteAllByTodo(
            Todo todo
    );

    // =====================================
    // COUNT SHARED USERS
    // =====================================

    long countByTodoAndActiveTrue(
            Todo todo
    );
}