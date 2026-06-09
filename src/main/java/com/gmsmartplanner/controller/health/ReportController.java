package com.gmsmartplanner.controller.health;

import com.gmsmartplanner.dto.request.health.CreateReportRequestDTO;
import com.gmsmartplanner.dto.request.health.UpdateReportRequestDTO;
import com.gmsmartplanner.dto.response.health.ReportResponseDTO;
import com.gmsmartplanner.payload.ApiResponse;
import com.gmsmartplanner.service.health.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reports")
public class ReportController {

    private final ReportService reportService;

    // =====================================
    // CREATE
    // =====================================

    @PostMapping
    public ResponseEntity<ApiResponse<ReportResponseDTO>>
    create(

            Authentication authentication,

            @ModelAttribute
            CreateReportRequestDTO dto

    ) {

        return ResponseEntity.ok(

                ApiResponse
                        .<ReportResponseDTO>builder()
                        .success(true)
                        .message("Report created successfully")
                        .data(

                                reportService
                                        .createReport(

                                                authentication.getName(),

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
    public ResponseEntity<ApiResponse<List<ReportResponseDTO>>>
    getAll(

            Authentication authentication

    ) {

        return ResponseEntity.ok(

                ApiResponse
                        .<List<ReportResponseDTO>>builder()
                        .success(true)
                        .message("Reports fetched successfully")
                        .data(

                                reportService
                                        .getReports(
                                                authentication.getName()
                                        )
                        )
                        .build()
        );
    }

    // =====================================
    // GET BY ID
    // =====================================

    @GetMapping("/{reportId}")
    public ResponseEntity<ApiResponse<ReportResponseDTO>>
    getById(

            Authentication authentication,

            @PathVariable
            Long reportId

    ) {

        return ResponseEntity.ok(

                ApiResponse
                        .<ReportResponseDTO>builder()
                        .success(true)
                        .message("Report fetched successfully")
                        .data(

                                reportService
                                        .getReport(

                                                authentication.getName(),

                                                reportId
                                        )
                        )
                        .build()
        );
    }

    // =====================================
    // UPDATE
    // =====================================

    @PatchMapping("/{reportId}")
    public ResponseEntity<ApiResponse<ReportResponseDTO>>
    update(

            Authentication authentication,

            @PathVariable
            Long reportId,

            @ModelAttribute
            UpdateReportRequestDTO dto

    ) {

        return ResponseEntity.ok(

                ApiResponse
                        .<ReportResponseDTO>builder()
                        .success(true)
                        .message("Report updated successfully")
                        .data(

                                reportService
                                        .updateReport(

                                                authentication.getName(),

                                                reportId,

                                                dto
                                        )
                        )
                        .build()
        );
    }

    // =====================================
    // DELETE
    // =====================================

    @DeleteMapping("/{reportId}")
    public ResponseEntity<ApiResponse<Void>>
    delete(

            Authentication authentication,

            @PathVariable
            Long reportId

    ) {

        reportService.deleteReport(

                authentication.getName(),

                reportId
        );

        return ResponseEntity.ok(

                ApiResponse
                        .<Void>builder()
                        .success(true)
                        .message("Report deleted successfully")
                        .build()
        );
    }
}