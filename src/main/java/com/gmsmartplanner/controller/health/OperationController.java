package com.gmsmartplanner.controller.health;

import com.gmsmartplanner.dto.request.health.CreateOperationRequestDTO;
import com.gmsmartplanner.dto.request.health.UpdateOperationRequestDTO;
import com.gmsmartplanner.dto.response.health.OperationResponseDTO;
import com.gmsmartplanner.payload.ApiResponse;
import com.gmsmartplanner.service.AccessUserService;
import com.gmsmartplanner.service.health.OperationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(
        "/api/v1/operations"
)
public class OperationController {

    private final OperationService
            operationService;

    private final AccessUserService
            accessUserService;

    // =====================================
    // CREATE
    // =====================================

    @PostMapping(
            consumes = {
                    "multipart/form-data"
            }
    )
    public ResponseEntity<
            ApiResponse<OperationResponseDTO>
            >
    create(

            Authentication authentication,

            @RequestHeader(
                    value =
                            "X-ACCESS-ID",

                    required =
                            false
            )
            Long accessId,

            @Valid
            @ModelAttribute
            CreateOperationRequestDTO dto

    ) {

        accessUserService
                .checkCreatePermission(

                        authentication.getName(),

                        accessId
                );

        return ResponseEntity.ok(

                ApiResponse
                        .<OperationResponseDTO>builder()

                        .success(true)

                        .message(
                                "Operation created successfully"
                        )

                        .data(

                                operationService
                                        .createOperation(

                                                accessUserService
                                                        .getEffectiveUsername(

                                                                authentication.getName(),

                                                                accessId
                                                        ),

                                                dto
                                        )
                        )

                        .build()
        );
    }

    // =====================================
    // GET ALL
    // =====================================

    @GetMapping
    public ResponseEntity<
            ApiResponse<List<OperationResponseDTO>>
            >
    getAll(

            Authentication authentication,

            @RequestHeader(
                    value =
                            "X-ACCESS-ID",

                    required =
                            false
            )
            Long accessId

    ) {

        accessUserService
                .checkViewPermission(

                        authentication.getName(),

                        accessId
                );

        return ResponseEntity.ok(

                ApiResponse
                        .<List<OperationResponseDTO>>builder()

                        .success(true)

                        .message(
                                "Operations fetched successfully"
                        )

                        .data(

                                operationService
                                        .getOperations(

                                                accessUserService
                                                        .getEffectiveUsername(

                                                                authentication.getName(),

                                                                accessId
                                                        )
                                        )
                        )

                        .build()
        );
    }

    // =====================================
    // GET BY ID
    // =====================================

    @GetMapping(
            "/{operationId}"
    )
    public ResponseEntity<
            ApiResponse<OperationResponseDTO>
            >
    getById(

            Authentication authentication,

            @RequestHeader(
                    value =
                            "X-ACCESS-ID",

                    required =
                            false
            )
            Long accessId,

            @PathVariable
            Long operationId

    ) {

        accessUserService
                .checkViewPermission(

                        authentication.getName(),

                        accessId
                );

        return ResponseEntity.ok(

                ApiResponse
                        .<OperationResponseDTO>builder()

                        .success(true)

                        .message(
                                "Operation fetched successfully"
                        )

                        .data(

                                operationService
                                        .getOperation(

                                                accessUserService
                                                        .getEffectiveUsername(

                                                                authentication.getName(),

                                                                accessId
                                                        ),

                                                operationId
                                        )
                        )

                        .build()
        );
    }

    // =====================================
    // UPDATE
    // =====================================

    @PatchMapping(
            value =
                    "/{operationId}",

            consumes = {
                    "multipart/form-data"
            }
    )
    public ResponseEntity<
            ApiResponse<OperationResponseDTO>
            >
    update(

            Authentication authentication,

            @RequestHeader(
                    value =
                            "X-ACCESS-ID",

                    required =
                            false
            )
            Long accessId,

            @PathVariable
            Long operationId,

            @Valid
            @ModelAttribute
            UpdateOperationRequestDTO dto

    ) {

        accessUserService
                .checkUpdatePermission(

                        authentication.getName(),

                        accessId
                );

        return ResponseEntity.ok(

                ApiResponse
                        .<OperationResponseDTO>builder()

                        .success(true)

                        .message(
                                "Operation updated successfully"
                        )

                        .data(

                                operationService
                                        .updateOperation(

                                                accessUserService
                                                        .getEffectiveUsername(

                                                                authentication.getName(),

                                                                accessId
                                                        ),

                                                operationId,

                                                dto
                                        )
                        )

                        .build()
        );
    }

    // =====================================
    // DELETE
    // =====================================

    @DeleteMapping(
            "/{operationId}"
    )
    public ResponseEntity<
            ApiResponse<Void>
            >
    delete(

            Authentication authentication,

            @RequestHeader(
                    value =
                            "X-ACCESS-ID",

                    required =
                            false
            )
            Long accessId,

            @PathVariable
            Long operationId

    ) {

        accessUserService
                .checkDeletePermission(

                        authentication.getName(),

                        accessId
                );

        operationService
                .deleteOperation(

                        accessUserService
                                .getEffectiveUsername(

                                        authentication.getName(),

                                        accessId
                                ),

                        operationId
                );

        return ResponseEntity.ok(

                ApiResponse
                        .<Void>builder()

                        .success(true)

                        .message(
                                "Operation deleted successfully"
                        )

                        .build()
        );
    }
}