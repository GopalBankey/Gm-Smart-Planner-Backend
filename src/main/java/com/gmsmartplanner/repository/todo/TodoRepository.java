package com.gmsmartplanner.repository.todo;

import com.gmsmartplanner.entity.todo.Todo;
import com.gmsmartplanner.entity.User;
import com.gmsmartplanner.enums.todo.TodoPriority;
import com.gmsmartplanner.enums.todo.TodoStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TodoRepository
        extends JpaRepository<Todo, Long> {

    // =====================================
    // FIND TODO
    // =====================================

    Optional<Todo> findByIdAndDeletedFalse(
            Long id
    );

    // =====================================
    // GET USER TODOS
    // =====================================

    Page<Todo> findAllByOwnerAndDeletedFalse(

            User owner,

            Pageable pageable
    );

    // =====================================
    // SEARCH TODOS
    // =====================================

    @Query("""

            SELECT t
            FROM Todo t

            WHERE t.owner = :user

            AND t.deleted = false

            AND (

                LOWER(t.title)
                LIKE LOWER(CONCAT('%', :query, '%'))

                OR

                LOWER(t.description)
                LIKE LOWER(CONCAT('%', :query, '%'))
            )

            """)
    Page<Todo> searchTodos(

            User user,

            String query,

            Pageable pageable
    );

    // =====================================
    // EXISTS BY SHARED TASK ID
    // =====================================

    boolean existsBySharedTaskId(
            String sharedTaskId
    );


    @Query("""

        SELECT DISTINCT t

        FROM Todo t

        LEFT JOIN TodoShare ts
        ON ts.todo = t

        WHERE t.deleted = false

        AND (

            t.owner = :user

            OR

            (
                ts.sharedWithUser = :user
                AND ts.active = true
            )
        )

        ORDER BY t.createdAt DESC

        """)
    Page<Todo> findMyAndSharedTodos(

            User user,

            Pageable pageable
    );
    @Query("""

SELECT DISTINCT t
FROM Todo t

LEFT JOIN TodoShare ts
ON ts.todo = t
AND ts.active = true

WHERE t.deleted = false

AND (

    (:includeOwn = true
        AND t.owner = :user)

    OR

    (:includeShared = true
        AND ts.sharedWithUser = :user)
)

AND (

    :priority IS NULL
    OR t.priority = :priority
)

AND (

    :status IS NULL
    OR t.status = :status
)

AND (

    CAST(:startDate AS date) IS NULL
    OR t.taskDateTime >= :startDate
)

AND (

    CAST(:endDate AS date) IS NULL
    OR t.taskDateTime <= :endDate
)

ORDER BY
t.taskDateTime DESC,
t.createdAt DESC

""")
    Page<Todo> filterTodos(

            @Param("user")
            User user,

            @Param("priority")
            TodoPriority priority,

            @Param("status")
            TodoStatus status,

            @Param("startDate")
            LocalDateTime startDate,

            @Param("endDate")
            LocalDateTime endDate,

            @Param("includeOwn")
            boolean includeOwn,

            @Param("includeShared")
            boolean includeShared,

            Pageable pageable
    );

    @Query("""

SELECT DISTINCT t
FROM Todo t

LEFT JOIN TodoShare ts
ON ts.todo = t
AND ts.active = true

WHERE t.deleted = false

AND (

    t.owner = :user

    OR

    ts.sharedWithUser = :user
)

ORDER BY t.createdAt DESC

""")
    List<Todo> findMyAndSharedTodosWithoutPagination(

            @Param("user")
            User user
    );
}