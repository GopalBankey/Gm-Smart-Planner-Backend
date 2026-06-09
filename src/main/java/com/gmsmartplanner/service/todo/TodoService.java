package com.gmsmartplanner.service.todo;

import com.gmsmartplanner.dto.request.todo.CreateTodoRequestDTO;
import com.gmsmartplanner.dto.request.todo.ShareTodoRequestDTO;
import com.gmsmartplanner.dto.request.todo.TodoFilterRequestDTO;
import com.gmsmartplanner.dto.request.todo.UpdateTodoRequestDTO;
import com.gmsmartplanner.dto.response.todo.TodoHomeResponseDTO;
import com.gmsmartplanner.dto.response.todo.TodoResponseDTO;
import com.gmsmartplanner.dto.response.todo.TodoSummaryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TodoService {

    TodoResponseDTO createTodo(

            String username,

            CreateTodoRequestDTO dto
    );

    TodoResponseDTO updateTodo(

            String username,

            Long todoId,

            UpdateTodoRequestDTO dto
    );

    void deleteTodo(

            String username,

            Long todoId
    );

    TodoResponseDTO getTodoById(

            String username,

            Long todoId
    );

    Page<TodoResponseDTO> getMyTodos(

            String username,

            Pageable pageable
    );

    Page<TodoSummaryDTO> searchTodos(

            String username,

            String query,

            Pageable pageable
    );

    TodoResponseDTO shareTodo(

            String username,

            Long todoId,

            ShareTodoRequestDTO dto
    );

    TodoResponseDTO completeTodo(

            String username,

            Long todoId
    );

    TodoResponseDTO reopenTodo(

            String username,

            Long todoId
    );

    // =====================================
// FILTER TODOS
// =====================================
    Page<TodoSummaryDTO> filterTodos(

            String username,

            TodoFilterRequestDTO dto,

            Pageable pageable
    );

    // =====================================
// HOME TODOS
// =====================================
    TodoHomeResponseDTO getHomeTodos(
            String username
    );
}