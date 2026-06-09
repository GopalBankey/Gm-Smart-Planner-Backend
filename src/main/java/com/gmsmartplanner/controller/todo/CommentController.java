package com.gmsmartplanner.controller.todo;

import com.gmsmartplanner.dto.request.todo.CreateCommentRequestDTO;
import com.gmsmartplanner.dto.response.todo.CommentResponseDTO;
import com.gmsmartplanner.payload.ApiResponse;
import com.gmsmartplanner.service.todo.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/comments")
public class CommentController {

    private final CommentService
            commentService;

    // =====================================
    // ADD COMMENT
    // =====================================
    @PostMapping("/{todoId}")
    public ResponseEntity<ApiResponse<CommentResponseDTO>>
    addComment(

            Authentication authentication,

            @PathVariable
            Long todoId,

            @Valid
            @RequestBody
            CreateCommentRequestDTO dto

    ) {

        CommentResponseDTO response =
                commentService.addComment(

                        authentication.getName(),

                        todoId,

                        dto
                );

        return ResponseEntity.status(
                HttpStatus.CREATED
        ).body(

                ApiResponse
                        .<CommentResponseDTO>builder()

                        .success(true)

                        .message(
                                "Comment added successfully"
                        )

                        .data(response)

                        .build()
        );
    }

    // =====================================
    // GET COMMENTS
    // =====================================
    @GetMapping("/{todoId}")
    public ResponseEntity<ApiResponse<List<CommentResponseDTO>>>
    getComments(

            Authentication authentication,

            @PathVariable
            Long todoId

    ) {

        List<CommentResponseDTO> response =
                commentService.getComments(

                        authentication.getName(),

                        todoId
                );

        return ResponseEntity.ok(

                ApiResponse
                        .<List<CommentResponseDTO>>builder()

                        .success(true)

                        .message(
                                "Comments fetched successfully"
                        )

                        .data(response)

                        .build()
        );
    }

    // =====================================
    // UPDATE COMMENT
    // =====================================
    @PutMapping("/{commentId}")
    public ResponseEntity<ApiResponse<CommentResponseDTO>>
    updateComment(

            Authentication authentication,

            @PathVariable
            Long commentId,

            @Valid
            @RequestBody
            CreateCommentRequestDTO dto

    ) {

        CommentResponseDTO response =
                commentService.updateComment(

                        authentication.getName(),

                        commentId,

                        dto
                );

        return ResponseEntity.ok(

                ApiResponse
                        .<CommentResponseDTO>builder()

                        .success(true)

                        .message(
                                "Comment updated successfully"
                        )

                        .data(response)

                        .build()
        );
    }

    // =====================================
    // DELETE COMMENT
    // =====================================
    @DeleteMapping("/{commentId}")
    public ResponseEntity<ApiResponse<Void>>
    deleteComment(

            Authentication authentication,

            @PathVariable
            Long commentId

    ) {

        commentService.deleteComment(

                authentication.getName(),

                commentId
        );

        return ResponseEntity.ok(

                ApiResponse
                        .<Void>builder()

                        .success(true)

                        .message(
                                "Comment deleted successfully"
                        )

                        .build()
        );
    }
}