package com.gmsmartplanner.controller.todo;

import com.gmsmartplanner.dto.request.todo.CreateTodoRequestDTO;
import com.gmsmartplanner.dto.request.todo.ShareTodoRequestDTO;
import com.gmsmartplanner.dto.request.todo.TodoFilterRequestDTO;
import com.gmsmartplanner.dto.request.todo.UpdateTodoRequestDTO;
import com.gmsmartplanner.dto.response.todo.TodoHomeResponseDTO;
import com.gmsmartplanner.dto.response.todo.TodoResponseDTO;
import com.gmsmartplanner.dto.response.todo.TodoSummaryDTO;
import com.gmsmartplanner.payload.ApiResponse;
import com.gmsmartplanner.service.todo.TodoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/todos")
@Slf4j
public class TodoController {

    private final TodoService
            todoService;

    // =====================================
    // CREATE TODO
    // =====================================
    @PostMapping
    public ResponseEntity<ApiResponse<TodoResponseDTO>>
    createTodo(

            Authentication authentication,

            @Valid
            @RequestBody
            CreateTodoRequestDTO dto

    ) {

        String username =
                authentication.getName();

        log.info(
                "Creating todo for user : {}",
                username
        );

        TodoResponseDTO response =
                todoService.createTodo(

                        username,

                        dto
                );

        return ResponseEntity.status(
                HttpStatus.CREATED
        ).body(

                ApiResponse
                        .<TodoResponseDTO>builder()

                        .success(true)

                        .message(
                                "Todo created successfully"
                        )

                        .data(response)

                        .build()
        );
    }

    // =====================================
    // UPDATE TODO
    // =====================================
    @PutMapping("/{todoId}")
    public ResponseEntity<ApiResponse<TodoResponseDTO>>
    updateTodo(

            Authentication authentication,

            @PathVariable
            Long todoId,

            @Valid
            @RequestBody
            UpdateTodoRequestDTO dto

    ) {

        String username =
                authentication.getName();

        log.info(
                "Updating todo : {}",
                todoId
        );

        TodoResponseDTO response =
                todoService.updateTodo(

                        username,

                        todoId,

                        dto
                );

        return ResponseEntity.ok(

                ApiResponse
                        .<TodoResponseDTO>builder()

                        .success(true)

                        .message(
                                "Todo updated successfully"
                        )

                        .data(response)

                        .build()
        );
    }

    // =====================================
    // DELETE TODO
    // =====================================
    @DeleteMapping("/{todoId}")
    public ResponseEntity<ApiResponse<Void>>
    deleteTodo(

            Authentication authentication,

            @PathVariable
            Long todoId

    ) {

        String username =
                authentication.getName();

        log.info(
                "Deleting todo : {}",
                todoId
        );

        todoService.deleteTodo(

                username,

                todoId
        );

        return ResponseEntity.ok(

                ApiResponse
                        .<Void>builder()

                        .success(true)

                        .message(
                                "Todo deleted successfully"
                        )

                        .build()
        );
    }

    // =====================================
    // GET TODO DETAILS
    // =====================================
    @GetMapping("/{todoId}")
    public ResponseEntity<ApiResponse<TodoResponseDTO>>
    getTodoById(

            Authentication authentication,

            @PathVariable
            Long todoId

    ) {

        String username =
                authentication.getName();

        TodoResponseDTO response =
                todoService.getTodoById(

                        username,

                        todoId
                );

        return ResponseEntity.ok(

                ApiResponse
                        .<TodoResponseDTO>builder()

                        .success(true)

                        .message(
                                "Todo fetched successfully"
                        )

                        .data(response)

                        .build()
        );
    }

    // =====================================
    // GET MY TODOS
    // =====================================
    @GetMapping
    public ResponseEntity<ApiResponse<Page<TodoResponseDTO>>>
    getMyTodos(

            Authentication authentication,

            @RequestParam(
                    defaultValue = "0"
            )
            int page,

            @RequestParam(
                    defaultValue = "10"
            )
            int size

    ) {

        String username =
                authentication.getName();

        Pageable pageable =
                PageRequest.of(page, size);

        Page<TodoResponseDTO> response =
                todoService.getMyTodos(

                        username,

                        pageable
                );

        return ResponseEntity.ok(

                ApiResponse
                        .<Page<TodoResponseDTO>>builder()

                        .success(true)

                        .message(
                                "Todos fetched successfully"
                        )

                        .data(response)

                        .build()
        );
    }

    // =====================================
    // SEARCH TODOS
    // =====================================
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<Page<TodoSummaryDTO>>>
    searchTodos(

            Authentication authentication,

            @RequestParam
            String query,

            @RequestParam(
                    defaultValue = "0"
            )
            int page,

            @RequestParam(
                    defaultValue = "10"
            )
            int size

    ) {

        String username =
                authentication.getName();

        Pageable pageable =
                PageRequest.of(page, size);

        Page<TodoSummaryDTO> response =
                todoService.searchTodos(

                        username,

                        query,

                        pageable
                );

        return ResponseEntity.ok(

                ApiResponse
                        .<Page<TodoSummaryDTO>>builder()

                        .success(true)

                        .message(
                                "Todos fetched successfully"
                        )

                        .data(response)

                        .build()
        );
    }

    // =====================================
    // SHARE TODO
    // =====================================
    @PostMapping("/{todoId}/share")
    public ResponseEntity<ApiResponse<TodoResponseDTO>>
    shareTodo(

            Authentication authentication,

            @PathVariable
            Long todoId,

            @Valid
            @RequestBody
            ShareTodoRequestDTO dto

    ) {

        String username =
                authentication.getName();

        TodoResponseDTO response =
                todoService.shareTodo(

                        username,

                        todoId,

                        dto
                );

        return ResponseEntity.ok(

                ApiResponse
                        .<TodoResponseDTO>builder()

                        .success(true)

                        .message(
                                "Todo shared successfully"
                        )

                        .data(response)

                        .build()
        );
    }

    // =====================================
    // COMPLETE TODO
    // =====================================
    @PatchMapping("/{todoId}/complete")
    public ResponseEntity<ApiResponse<TodoResponseDTO>>
    completeTodo(

            Authentication authentication,

            @PathVariable
            Long todoId

    ) {

        String username =
                authentication.getName();

        TodoResponseDTO response =
                todoService.completeTodo(

                        username,

                        todoId
                );

        return ResponseEntity.ok(

                ApiResponse
                        .<TodoResponseDTO>builder()

                        .success(true)

                        .message(
                                "Todo completed successfully"
                        )

                        .data(response)

                        .build()
        );
    }

    // =====================================
    // REOPEN TODO
    // =====================================
    @PatchMapping("/{todoId}/reopen")
    public ResponseEntity<ApiResponse<TodoResponseDTO>>
    reopenTodo(

            Authentication authentication,

            @PathVariable
            Long todoId

    ) {

        String username =
                authentication.getName();

        TodoResponseDTO response =
                todoService.reopenTodo(

                        username,

                        todoId
                );

        return ResponseEntity.ok(

                ApiResponse
                        .<TodoResponseDTO>builder()

                        .success(true)

                        .message(
                                "Todo reopened successfully"
                        )

                        .data(response)

                        .build()
        );
    }

    // =====================================
// FILTER TODOS
// =====================================
    @PostMapping("/filter")
    public ResponseEntity<ApiResponse<Page<TodoSummaryDTO>>>
    filterTodos(

            Authentication authentication,

            @RequestBody
            TodoFilterRequestDTO dto,

            Pageable pageable

    ) {

        String username =
                authentication.getName();

        log.info(
                "Filtering todos for user : {}",
                username
        );

        Page<TodoSummaryDTO> response =
                todoService.filterTodos(

                        username,

                        dto,

                        pageable
                );

        return ResponseEntity.ok(

                ApiResponse
                        .<Page<TodoSummaryDTO>>builder()

                        .success(true)

                        .message(
                                "Todos fetched successfully"
                        )

                        .data(response)

                        .build()
        );
    }

    // =====================================
// HOME TODOS
// =====================================
    @GetMapping("/home")
    public ResponseEntity<ApiResponse<TodoHomeResponseDTO>>
    getHomeTodos(

            Authentication authentication

    ) {

        String username =
                authentication.getName();

        TodoHomeResponseDTO response =
                todoService.getHomeTodos(
                        username
                );

        return ResponseEntity.ok(

                ApiResponse
                        .<TodoHomeResponseDTO>builder()

                        .success(true)

                        .message(
                                "Home todos fetched successfully"
                        )

                        .data(response)

                        .build()
        );
    }
}