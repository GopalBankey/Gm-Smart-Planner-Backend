package com.gmsmartplanner.repository.todo;

import com.gmsmartplanner.entity.todo.Todo;
import com.gmsmartplanner.entity.todo.TodoChecklist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TodoChecklistRepository
        extends JpaRepository<TodoChecklist, Long> {

    // =====================================
    // GET TODO CHECKLISTS
    // =====================================

    List<TodoChecklist>
    findAllByTodoAndDeletedFalseOrderByDisplayOrderAsc(
            Todo todo
    );

    // =====================================
    // PAGINATED CHECKLISTS
    // =====================================

    Page<TodoChecklist>
    findAllByTodoAndDeletedFalse(

            Todo todo,

            Pageable pageable
    );

    // =====================================
    // GET COMPLETED CHECKLISTS
    // =====================================

    List<TodoChecklist>
    findAllByTodoAndCompletedTrueAndDeletedFalse(
            Todo todo
    );

    // =====================================
    // GET PENDING CHECKLISTS
    // =====================================

    List<TodoChecklist>
    findAllByTodoAndCompletedFalseAndDeletedFalse(
            Todo todo
    );

    // =====================================
    // COUNT CHECKLISTS
    // =====================================

    long countByTodoAndDeletedFalse(
            Todo todo
    );

    // =====================================
    // COUNT COMPLETED CHECKLISTS
    // =====================================

    long countByTodoAndCompletedTrueAndDeletedFalse(
            Todo todo
    );

    Optional<TodoChecklist>
    findByIdAndDeletedFalse(
            Long id
    );
}