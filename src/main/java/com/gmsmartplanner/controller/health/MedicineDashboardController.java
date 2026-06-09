package com.gmsmartplanner.controller.health;

import com.gmsmartplanner.dto.response.health.MedicineDashboardResponseDTO;
import com.gmsmartplanner.payload.ApiResponse;
import com.gmsmartplanner.service.health.MedicineDashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/medicine/dashboard")
public class MedicineDashboardController {

    private final MedicineDashboardService
            medicineDashboardService;

    // =====================================
    // DASHBOARD
    // =====================================

    @GetMapping
    public ResponseEntity<
            ApiResponse<
                    MedicineDashboardResponseDTO
                    >
            >

    getDashboard(

            Authentication authentication

    ) {

        return ResponseEntity.ok(

                ApiResponse

                        .<MedicineDashboardResponseDTO>
                                builder()

                        .success(
                                true
                        )

                        .message(
                                "Medicine dashboard fetched successfully"
                        )

                        .data(

                                medicineDashboardService
                                        .getDashboard(

                                                authentication
                                                        .getName()
                                        )
                        )

                        .build()
        );
    }
}