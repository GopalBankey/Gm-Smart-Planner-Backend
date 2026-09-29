package com.gmsmartplanner.service.health;

import com.gmsmartplanner.dto.request.health.CreateOperationRequestDTO;
import com.gmsmartplanner.dto.request.health.UpdateOperationRequestDTO;
import com.gmsmartplanner.dto.response.health.OperationResponseDTO;

import java.util.List;

public interface OperationService {

    OperationResponseDTO
    createOperation(
            String username,
            CreateOperationRequestDTO dto
    );

    List<OperationResponseDTO>
    getOperations(
            String username
    );

    OperationResponseDTO
    getOperation(
            String username,
            Long operationId
    );

    OperationResponseDTO
    updateOperation(
            String username,
            Long operationId,
            UpdateOperationRequestDTO dto
    );

    void deleteOperation(
            String username,
            Long operationId
    );
}