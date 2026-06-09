package com.gmsmartplanner.controller.health;

import com.gmsmartplanner.dto.response.health.MedicineHistoryResponseDTO;
import com.gmsmartplanner.payload.ApiResponse;
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

    private final
    MedicineHistoryService
            service;

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

            @PathVariable
            Long medicineId,

            @PathVariable
            Long scheduleId

    ) {

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

                                                authentication.getName(),

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

            @PathVariable
            Long medicineId,

            @PathVariable
            Long scheduleId

    ) {

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

                                                authentication.getName(),

                                                medicineId,

                                                scheduleId
                                        )
                        )

                        .build()
        );
    }

    // =====================================
    // GET ALL
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

            Authentication authentication

    ) {

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

                                                authentication.getName()
                                        )
                        )

                        .build()
        );
    }

    // =====================================
    // GET MEDICINE HISTORY
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

            @PathVariable
            Long medicineId

    ) {

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

                                                authentication.getName(),

                                                medicineId
                                        )
                        )

                        .build()
        );
    }
}