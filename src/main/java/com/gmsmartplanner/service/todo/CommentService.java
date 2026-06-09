package com.gmsmartplanner.service.todo;

import com.gmsmartplanner.dto.request.todo.CreateCommentRequestDTO;
import com.gmsmartplanner.dto.response.todo.CommentResponseDTO;

import java.util.List;

public interface CommentService {

    CommentResponseDTO addComment(

            String username,

            Long todoId,

            CreateCommentRequestDTO dto
    );

    List<CommentResponseDTO> getComments(

            String username,

            Long todoId
    );

    CommentResponseDTO updateComment(

            String username,

            Long commentId,

            CreateCommentRequestDTO dto
    );

    void deleteComment(

            String username,

            Long commentId
    );
}