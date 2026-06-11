package com.gmsmartplanner.controller.health;

import com.gmsmartplanner.dto.request.health.UpdateMedicineRefillReminderRequestDTO;
import com.gmsmartplanner.dto.response.health.MedicineRefillReminderResponseDTO;
import com.gmsmartplanner.payload.ApiResponse;
import com.gmsmartplanner.service.AccessUserService;
import com.gmsmartplanner.service.health.MedicineRefillReminderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(
        "/api/v1/medicines"
)
public class
MedicineRefillReminderController {

    private final
    MedicineRefillReminderService
            service;

    private final
    AccessUserService
            accessUserService;

    // =====================================
    // GET
    // =====================================

    @GetMapping(
            "/{medicineId}/refill"
    )
    public ResponseEntity<
            ApiResponse<
                    MedicineRefillReminderResponseDTO
                    >
            >
    get(

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

                        .<MedicineRefillReminderResponseDTO>
                                builder()

                        .success(
                                true
                        )

                        .message(
                                "Refill reminder fetched successfully"
                        )

                        .data(

                                service
                                        .getRefillReminder(

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

    // =====================================
    // UPDATE
    // =====================================

    @PatchMapping(
            "/{medicineId}/refill"
    )
    public ResponseEntity<
            ApiResponse<
                    MedicineRefillReminderResponseDTO
                    >
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
            Long medicineId,

            @RequestBody
            UpdateMedicineRefillReminderRequestDTO dto

    ) {

        accessUserService
                .checkUpdatePermission(

                        authentication
                                .getName(),

                        accessId
                );

        return ResponseEntity.ok(

                ApiResponse

                        .<MedicineRefillReminderResponseDTO>
                                builder()

                        .success(
                                true
                        )

                        .message(
                                "Refill reminder updated successfully"
                        )

                        .data(

                                service
                                        .updateRefillReminder(

                                                accessUserService
                                                        .getEffectiveUsername(

                                                                authentication
                                                                        .getName(),

                                                                accessId
                                                        ),

                                                medicineId,

                                                dto
                                        )
                        )

                        .build()
        );
    }
}