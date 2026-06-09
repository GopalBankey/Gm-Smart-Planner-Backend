package com.gmsmartplanner.controller.todo;

import com.gmsmartplanner.dto.response.todo.SubTaskResponseDTO;
import com.gmsmartplanner.payload.ApiResponse;
import com.gmsmartplanner.service.todo.SubTaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/subtasks")
public class SubTaskController {

    private final SubTaskService
            subTaskService;

    // =====================================
    // COMPLETE SUB TASK
    // =====================================
    @PatchMapping("/{subTaskId}/complete")
    public ResponseEntity<ApiResponse<SubTaskResponseDTO>>
    completeSubTask(

            Authentication authentication,

            @PathVariable
            Long subTaskId

    ) {

        SubTaskResponseDTO response =
                subTaskService.completeSubTask(

                        authentication.getName(),

                        subTaskId
                );

        return ResponseEntity.ok(

                ApiResponse
                        .<SubTaskResponseDTO>builder()

                        .success(true)

                        .message(
                                "Sub task updated successfully"
                        )

                        .data(response)

                        .build()
        );
    }

    // =====================================
// REOPEN SUB TASK
// =====================================
    @PatchMapping("/{subTaskId}/reopen")
    public ResponseEntity<ApiResponse<SubTaskResponseDTO>>
    reopenSubTask(

            Authentication authentication,

            @PathVariable
            Long subTaskId

    ) {

        SubTaskResponseDTO response =
                subTaskService
                        .reopenSubTask(

                                authentication.getName(),

                                subTaskId
                        );

        return ResponseEntity.ok(

                ApiResponse
                        .<SubTaskResponseDTO>builder()

                        .success(true)

                        .message(
                                "Sub task reopened successfully"
                        )

                        .data(response)

                        .build()
        );
    }
}