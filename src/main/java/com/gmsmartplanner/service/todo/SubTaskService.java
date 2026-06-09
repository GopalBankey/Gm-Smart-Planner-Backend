package com.gmsmartplanner.service.todo;

import com.gmsmartplanner.dto.response.todo.SubTaskResponseDTO;

public interface SubTaskService {

    SubTaskResponseDTO completeSubTask(

            String username,

            Long subTaskId
    );

    SubTaskResponseDTO reopenSubTask(

            String username,

            Long subTaskId
    );
}