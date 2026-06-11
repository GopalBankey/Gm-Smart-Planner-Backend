package com.gmsmartplanner.controller.health;

import com.gmsmartplanner.dto.request.health.CreateReportRequestDTO;
import com.gmsmartplanner.dto.request.health.UpdateReportRequestDTO;
import com.gmsmartplanner.dto.response.health.ReportResponseDTO;
import com.gmsmartplanner.payload.ApiResponse;
import com.gmsmartplanner.service.AccessUserService;
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

    private final ReportService
            reportService;

    private final AccessUserService
            accessUserService;

    // =====================================
    // CREATE
    // =====================================

    @PostMapping
    public ResponseEntity<ApiResponse<ReportResponseDTO>>
    create(

            Authentication authentication,

            @RequestHeader(
                    value =
                            "X-ACCESS-ID",

                    required =
                            false
            )
            Long accessId,

            @ModelAttribute
            CreateReportRequestDTO dto

    ) {

        accessUserService
                .checkCreatePermission(

                        authentication.getName(),

                        accessId
                );

        return ResponseEntity.ok(

                ApiResponse
                        .<ReportResponseDTO>builder()

                        .success(true)

                        .message(
                                "Report created successfully"
                        )

                        .data(

                                reportService
                                        .createReport(

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
    public ResponseEntity<ApiResponse<List<ReportResponseDTO>>>
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
                        .<List<ReportResponseDTO>>builder()

                        .success(true)

                        .message(
                                "Reports fetched successfully"
                        )

                        .data(

                                reportService
                                        .getReports(

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

    @GetMapping("/{reportId}")
    public ResponseEntity<ApiResponse<ReportResponseDTO>>
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
            Long reportId

    ) {

        accessUserService
                .checkViewPermission(

                        authentication.getName(),

                        accessId
                );

        return ResponseEntity.ok(

                ApiResponse
                        .<ReportResponseDTO>builder()

                        .success(true)

                        .message(
                                "Report fetched successfully"
                        )

                        .data(

                                reportService
                                        .getReport(

                                                accessUserService
                                                        .getEffectiveUsername(

                                                                authentication
                                                                        .getName(),

                                                                accessId
                                                        ),

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

            @RequestHeader(
                    value =
                            "X-ACCESS-ID",

                    required =
                            false
            )
            Long accessId,

            @PathVariable
            Long reportId,

            @ModelAttribute
            UpdateReportRequestDTO dto

    ) {

        accessUserService
                .checkUpdatePermission(

                        authentication.getName(),

                        accessId
                );

        return ResponseEntity.ok(

                ApiResponse
                        .<ReportResponseDTO>builder()

                        .success(true)

                        .message(
                                "Report updated successfully"
                        )

                        .data(

                                reportService
                                        .updateReport(

                                                accessUserService
                                                        .getEffectiveUsername(

                                                                authentication
                                                                        .getName(),

                                                                accessId
                                                        ),

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

            @RequestHeader(
                    value =
                            "X-ACCESS-ID",

                    required =
                            false
            )
            Long accessId,

            @PathVariable
            Long reportId

    ) {

        accessUserService
                .checkDeletePermission(

                        authentication.getName(),

                        accessId
                );

        reportService
                .deleteReport(

                        accessUserService
                                .getEffectiveUsername(

                                        authentication
                                                .getName(),

                                        accessId
                                ),

                        reportId
                );

        return ResponseEntity.ok(

                ApiResponse
                        .<Void>builder()

                        .success(true)

                        .message(
                                "Report deleted successfully"
                        )

                        .build()
        );
    }
}