package com.gmsmartplanner.controller.health;

import com.gmsmartplanner.dto.response.health.MedicineHistoryResponseDTO;
import com.gmsmartplanner.payload.ApiResponse;
import com.gmsmartplanner.service.AccessUserService;
import com.gmsmartplanner.service.health.MedicineHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(
        "/api/v1/medicines"
)
public class MedicineHistoryController {

    private final MedicineHistoryService
            service;

    private final AccessUserService
            accessUserService;

    // =====================================
    // TAKE
    // =====================================

    @PostMapping(
            "/{medicineId}/take/{scheduleId}"
    )
    public ResponseEntity<
            ApiResponse<
                    MedicineHistoryResponseDTO
                    >
            >
    take(

            Authentication authentication,

            @RequestHeader(
                    value =
                            "X-ACCESS-ID",
                    required =
                            false
            )
            Long accessId,

            @PathVariable
            Long medicineId,

            @PathVariable
            Long scheduleId

    ) {

        accessUserService
                .checkTakePermission(

                        authentication
                                .getName(),

                        accessId
                );

        return ResponseEntity.ok(

                ApiResponse

                        .<MedicineHistoryResponseDTO>
                                builder()

                        .success(
                                true
                        )

                        .message(
                                "Medicine taken successfully"
                        )

                        .data(

                                service
                                        .takeMedicine(

                                                accessUserService
                                                        .getEffectiveUsername(

                                                                authentication
                                                                        .getName(),

                                                                accessId
                                                        ),

                                                medicineId,

                                                scheduleId
                                        )
                        )

                        .build()
        );
    }

    // =====================================
    // SKIP
    // =====================================

    @PostMapping(
            "/{medicineId}/skip/{scheduleId}"
    )
    public ResponseEntity<
            ApiResponse<
                    MedicineHistoryResponseDTO
                    >
            >
    skip(

            Authentication authentication,

            @RequestHeader(
                    value =
                            "X-ACCESS-ID",
                    required =
                            false
            )
            Long accessId,

            @PathVariable
            Long medicineId,

            @PathVariable
            Long scheduleId

    ) {

        accessUserService
                .checkTakePermission(

                        authentication
                                .getName(),

                        accessId
                );

        return ResponseEntity.ok(

                ApiResponse

                        .<MedicineHistoryResponseDTO>
                                builder()

                        .success(
                                true
                        )

                        .message(
                                "Medicine skipped successfully"
                        )

                        .data(

                                service
                                        .skipMedicine(

                                                accessUserService
                                                        .getEffectiveUsername(

                                                                authentication
                                                                        .getName(),

                                                                accessId
                                                        ),

                                                medicineId,

                                                scheduleId
                                        )
                        )

                        .build()
        );
    }

    // =====================================
    // HISTORY
    // =====================================

    @GetMapping(
            "/history"
    )
    public ResponseEntity<
            ApiResponse<
                    List<
                            MedicineHistoryResponseDTO
                            >
                    >
            >
    getHistory(

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

                        authentication
                                .getName(),

                        accessId
                );

        return ResponseEntity.ok(

                ApiResponse

                        .<List<
                                MedicineHistoryResponseDTO
                                >>
                                builder()

                        .success(
                                true
                        )

                        .message(
                                "Medicine history fetched successfully"
                        )

                        .data(

                                service
                                        .getHistory(

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
    // MEDICINE HISTORY
    // =====================================

    @GetMapping(
            "/{medicineId}/history"
    )
    public ResponseEntity<
            ApiResponse<
                    List<
                            MedicineHistoryResponseDTO
                            >
                    >
            >
    getMedicineHistory(

            Authentication authentication,

            @RequestHeader(
                    value =
                            "X-ACCESS-ID",
                    required =
                            false
            )
            Long accessId,

            @PathVariable
            Long medicineId

    ) {

        accessUserService
                .checkViewPermission(

                        authentication
                                .getName(),

                        accessId
                );

        return ResponseEntity.ok(

                ApiResponse

                        .<List<
                                MedicineHistoryResponseDTO
                                >>
                                builder()

                        .success(
                                true
                        )

                        .message(
                                "Medicine history fetched successfully"
                        )

                        .data(

                                service
                                        .getMedicineHistory(

                                                accessUserService
                                                        .getEffectiveUsername(

                                                                authentication
                                                                        .getName(),

                                                                accessId
                                                        ),

                                                medicineId
                                        )
                        )

                        .build()
        );
    }
}