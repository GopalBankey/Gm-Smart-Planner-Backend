package com.gmsmartplanner.controller.health;

import com.gmsmartplanner.dto.request.health.CreateDiseaseRequestDTO;
import com.gmsmartplanner.dto.request.health.UpdateDiseaseRequestDTO;
import com.gmsmartplanner.dto.response.health.DiseaseResponseDTO;
import com.gmsmartplanner.payload.ApiResponse;
import com.gmsmartplanner.service.AccessUserService;
import com.gmsmartplanner.service.health.DiseaseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(
        "/api/v1/diseases"
)
public class DiseaseController {

    private final DiseaseService
            diseaseService;

    private final AccessUserService
            accessUserService;

    // =====================================
    // CREATE
    // =====================================

    @PostMapping
    public ResponseEntity<
            ApiResponse<DiseaseResponseDTO>
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
            @RequestBody
            CreateDiseaseRequestDTO dto

    ) {

        accessUserService
                .checkCreatePermission(

                        authentication.getName(),

                        accessId
                );

        return ResponseEntity.ok(

                ApiResponse
                        .<DiseaseResponseDTO>builder()

                        .success(true)

                        .message(
                                "Disease created successfully"
                        )

                        .data(

                                diseaseService
                                        .createDisease(

                                                accessUserService
                                                        .getEffectiveUsername(

                                                                authentication
                                                                        .getName(),

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
            ApiResponse<List<DiseaseResponseDTO>>
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
                        .<List<DiseaseResponseDTO>>builder()

                        .success(true)

                        .message(
                                "Diseases fetched successfully"
                        )

                        .data(

                                diseaseService
                                        .getDiseases(

                                                accessUserService
                                                        .getEffectiveUsername(

                                                                authentication
                                                                        .getName(),

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
            "/{diseaseId}"
    )
    public ResponseEntity<
            ApiResponse<DiseaseResponseDTO>
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
            Long diseaseId

    ) {

        accessUserService
                .checkViewPermission(

                        authentication.getName(),

                        accessId
                );

        return ResponseEntity.ok(

                ApiResponse
                        .<DiseaseResponseDTO>builder()

                        .success(true)

                        .message(
                                "Disease fetched successfully"
                        )

                        .data(

                                diseaseService
                                        .getDisease(

                                                accessUserService
                                                        .getEffectiveUsername(

                                                                authentication
                                                                        .getName(),

                                                                accessId
                                                        ),

                                                diseaseId
                                        )
                        )

                        .build()
        );
    }

    // =====================================
    // UPDATE
    // =====================================

    @PatchMapping(
            "/{diseaseId}"
    )
    public ResponseEntity<
            ApiResponse<DiseaseResponseDTO>
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
            Long diseaseId,

            @Valid
            @RequestBody
            UpdateDiseaseRequestDTO dto

    ) {

        accessUserService
                .checkUpdatePermission(

                        authentication.getName(),

                        accessId
                );

        return ResponseEntity.ok(

                ApiResponse
                        .<DiseaseResponseDTO>builder()

                        .success(true)

                        .message(
                                "Disease updated successfully"
                        )

                        .data(

                                diseaseService
                                        .updateDisease(

                                                accessUserService
                                                        .getEffectiveUsername(

                                                                authentication
                                                                        .getName(),

                                                                accessId
                                                        ),

                                                diseaseId,

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
            "/{diseaseId}"
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
            Long diseaseId

    ) {

        accessUserService
                .checkDeletePermission(

                        authentication.getName(),

                        accessId
                );

        diseaseService
                .deleteDisease(

                        accessUserService
                                .getEffectiveUsername(

                                        authentication.getName(),

                                        accessId
                                ),

                        diseaseId
                );

        return ResponseEntity.ok(

                ApiResponse
                        .<Void>builder()

                        .success(true)

                        .message(
                                "Disease deleted successfully"
                        )

                        .build()
        );
    }
}