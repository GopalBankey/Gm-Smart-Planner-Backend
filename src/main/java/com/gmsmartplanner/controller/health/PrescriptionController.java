package com.gmsmartplanner.controller.health;

import com.gmsmartplanner.dto.request.health.CreatePrescriptionRequestDTO;
import com.gmsmartplanner.dto.request.health.UpdatePrescriptionRequestDTO;
import com.gmsmartplanner.dto.response.health.PrescriptionResponseDTO;
import com.gmsmartplanner.payload.ApiResponse;
import com.gmsmartplanner.service.AccessUserService;
import com.gmsmartplanner.service.health.PrescriptionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(
        "/api/v1/prescriptions"
)
public class PrescriptionController {

    private final PrescriptionService
            prescriptionService;

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
            ApiResponse<PrescriptionResponseDTO>
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
            CreatePrescriptionRequestDTO dto

    ) {

        accessUserService
                .checkCreatePermission(

                        authentication.getName(),

                        accessId
                );

        return ResponseEntity.ok(

                ApiResponse
                        .<PrescriptionResponseDTO>builder()

                        .success(true)

                        .message(
                                "Prescription created successfully"
                        )

                        .data(

                                prescriptionService
                                        .createPrescription(

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
            ApiResponse<List<PrescriptionResponseDTO>>
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
                        .<List<PrescriptionResponseDTO>>builder()

                        .success(true)

                        .message(
                                "Prescriptions fetched successfully"
                        )

                        .data(

                                prescriptionService
                                        .getPrescriptions(

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
            "/{prescriptionId}"
    )
    public ResponseEntity<
            ApiResponse<PrescriptionResponseDTO>
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
            Long prescriptionId

    ) {

        accessUserService
                .checkViewPermission(

                        authentication.getName(),

                        accessId
                );

        return ResponseEntity.ok(

                ApiResponse
                        .<PrescriptionResponseDTO>builder()

                        .success(true)

                        .message(
                                "Prescription fetched successfully"
                        )

                        .data(

                                prescriptionService
                                        .getPrescription(

                                                accessUserService
                                                        .getEffectiveUsername(

                                                                authentication.getName(),

                                                                accessId
                                                        ),

                                                prescriptionId
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
                    "/{prescriptionId}",

            consumes = {
                    "multipart/form-data"
            }
    )
    public ResponseEntity<
            ApiResponse<PrescriptionResponseDTO>
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
            Long prescriptionId,

            @Valid
            @ModelAttribute
            UpdatePrescriptionRequestDTO dto

    ) {

        accessUserService
                .checkUpdatePermission(

                        authentication.getName(),

                        accessId
                );

        return ResponseEntity.ok(

                ApiResponse
                        .<PrescriptionResponseDTO>builder()

                        .success(true)

                        .message(
                                "Prescription updated successfully"
                        )

                        .data(

                                prescriptionService
                                        .updatePrescription(

                                                accessUserService
                                                        .getEffectiveUsername(

                                                                authentication.getName(),

                                                                accessId
                                                        ),

                                                prescriptionId,

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
            "/{prescriptionId}"
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
            Long prescriptionId

    ) {

        accessUserService
                .checkDeletePermission(

                        authentication.getName(),

                        accessId
                );

        prescriptionService
                .deletePrescription(

                        accessUserService
                                .getEffectiveUsername(

                                        authentication.getName(),

                                        accessId
                                ),

                        prescriptionId
                );

        return ResponseEntity.ok(

                ApiResponse
                        .<Void>builder()

                        .success(true)

                        .message(
                                "Prescription deleted successfully"
                        )

                        .build()
        );
    }
}