package com.gmsmartplanner.controller.health;

import com.gmsmartplanner.dto.request.health.UpdateMedicineRefillReminderRequestDTO;
import com.gmsmartplanner.dto.response.health.MedicineRefillReminderResponseDTO;
import com.gmsmartplanner.payload.ApiResponse;
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

            @PathVariable
            Long medicineId

    ) {

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

                                                authentication.getName(),

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

            @PathVariable
            Long medicineId,

            @RequestBody
            UpdateMedicineRefillReminderRequestDTO dto

    ) {

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

                                                authentication.getName(),

                                                medicineId,

                                                dto
                                        )
                        )

                        .build()
        );
    }
}