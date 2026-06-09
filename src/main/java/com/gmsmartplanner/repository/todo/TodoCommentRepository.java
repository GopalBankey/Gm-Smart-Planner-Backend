package com.gmsmartplanner.repository.todo;

import com.gmsmartplanner.entity.todo.Todo;
import com.gmsmartplanner.entity.todo.TodoComment;
import com.gmsmartplanner.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TodoCommentRepository
        extends JpaRepository<TodoComment, Long> {

    // =====================================
    // GET TODO COMMENTS
    // =====================================

    Page<TodoComment>
    findAllByTodoAndDeletedFalseOrderByCreatedAtDesc(

            Todo todo,

            Pageable pageable
    );

    // =====================================
    // GET USER COMMENTS
    // =====================================

    Page<TodoComment>
    findAllByUserAndDeletedFalseOrderByCreatedAtDesc(

            User user,

            Pageable pageable
    );

    // =====================================
    // COUNT COMMENTS
    // =====================================

    long countByTodoAndDeletedFalse(
            Todo todo
    );

    List<TodoComment>
    findAllByTodoAndDeletedFalseOrderByCreatedAtAsc(

            Todo todo
    );

    Optional<TodoComment>
    findByIdAndDeletedFalse(
            Long id
    );
}