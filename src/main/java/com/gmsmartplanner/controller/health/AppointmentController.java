package com.gmsmartplanner.controller.health;

import com.gmsmartplanner.dto.request.health.CreateAppointmentRequestDTO;
import com.gmsmartplanner.dto.request.health.UpdateAppointmentRequestDTO;
import com.gmsmartplanner.dto.response.health.AppointmentResponseDTO;
import com.gmsmartplanner.payload.ApiResponse;
import com.gmsmartplanner.service.AccessUserService;
import com.gmsmartplanner.service.health.AppointmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(
        "/api/v1/appointments"
)
public class AppointmentController {

    private final AppointmentService
            appointmentService;

    private final AccessUserService
            accessUserService;

    // =====================================
    // CREATE
    // =====================================

    @PostMapping
    public ResponseEntity<
            ApiResponse<
                    AppointmentResponseDTO
                    >
            >
    createAppointment(

            Authentication authentication,

            @RequestHeader(
                    value = "X-ACCESS-ID",
                    required = false
            )
            Long accessId,

            @Valid
            @RequestBody
            CreateAppointmentRequestDTO dto

    ) {

        accessUserService
                .checkCreatePermission(
                        authentication.getName(),
                        accessId
                );

        return ResponseEntity.ok(

                ApiResponse
                        .<AppointmentResponseDTO>
                                builder()

                        .success(true)

                        .message(
                                "Appointment created successfully"
                        )

                        .data(

                                appointmentService
                                        .createAppointment(

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
            ApiResponse<
                    List<
                            AppointmentResponseDTO
                            >
                    >
            >
    getAppointments(

            Authentication authentication,

            @RequestHeader(
                    value = "X-ACCESS-ID",
                    required = false
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
                        .<List<
                                AppointmentResponseDTO
                                >>
                                builder()

                        .success(true)

                        .message(
                                "Appointments fetched successfully"
                        )

                        .data(

                                appointmentService
                                        .getAppointments(

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
            "/{appointmentId}"
    )
    public ResponseEntity<
            ApiResponse<
                    AppointmentResponseDTO
                    >
            >
    getAppointment(

            Authentication authentication,

            @RequestHeader(
                    value = "X-ACCESS-ID",
                    required = false
            )
            Long accessId,

            @PathVariable
            Long appointmentId

    ) {

        accessUserService
                .checkViewPermission(
                        authentication.getName(),
                        accessId
                );

        return ResponseEntity.ok(

                ApiResponse
                        .<AppointmentResponseDTO>
                                builder()

                        .success(true)

                        .message(
                                "Appointment fetched successfully"
                        )

                        .data(

                                appointmentService
                                        .getAppointment(

                                                accessUserService
                                                        .getEffectiveUsername(

                                                                authentication.getName(),

                                                                accessId
                                                        ),

                                                appointmentId
                                        )
                        )

                        .build()
        );
    }

    // =====================================
    // UPDATE
    // =====================================

    @PatchMapping(
            "/{appointmentId}"
    )
    public ResponseEntity<
            ApiResponse<
                    AppointmentResponseDTO
                    >
            >
    updateAppointment(

            Authentication authentication,

            @RequestHeader(
                    value = "X-ACCESS-ID",
                    required = false
            )
            Long accessId,

            @PathVariable
            Long appointmentId,

            @Valid
            @RequestBody
            UpdateAppointmentRequestDTO dto

    ) {

        accessUserService
                .checkUpdatePermission(
                        authentication.getName(),
                        accessId
                );

        return ResponseEntity.ok(

                ApiResponse
                        .<AppointmentResponseDTO>
                                builder()

                        .success(true)

                        .message(
                                "Appointment updated successfully"
                        )

                        .data(

                                appointmentService
                                        .updateAppointment(

                                                accessUserService
                                                        .getEffectiveUsername(

                                                                authentication.getName(),

                                                                accessId
                                                        ),

                                                appointmentId,

                                                dto
                                        )
                        )

                        .build()
        );
    }

    // =====================================
    // COMPLETE
    // =====================================

    @PatchMapping(
            "/{appointmentId}/complete"
    )
    public ResponseEntity<
            ApiResponse<
                    AppointmentResponseDTO
                    >
            >
    completeAppointment(

            Authentication authentication,

            @RequestHeader(
                    value = "X-ACCESS-ID",
                    required = false
            )
            Long accessId,

            @PathVariable
            Long appointmentId

    ) {

        accessUserService
                .checkUpdatePermission(
                        authentication.getName(),
                        accessId
                );

        return ResponseEntity.ok(

                ApiResponse
                        .<AppointmentResponseDTO>
                                builder()

                        .success(true)

                        .message(
                                "Appointment completed successfully"
                        )

                        .data(

                                appointmentService
                                        .completeAppointment(

                                                accessUserService
                                                        .getEffectiveUsername(

                                                                authentication.getName(),

                                                                accessId
                                                        ),

                                                appointmentId
                                        )
                        )

                        .build()
        );
    }

    // =====================================
    // DELETE
    // =====================================

    @DeleteMapping(
            "/{appointmentId}"
    )
    public ResponseEntity<
            ApiResponse<
                    Void
                    >
            >
    deleteAppointment(

            Authentication authentication,

            @RequestHeader(
                    value = "X-ACCESS-ID",
                    required = false
            )
            Long accessId,

            @PathVariable
            Long appointmentId

    ) {

        accessUserService
                .checkDeletePermission(
                        authentication.getName(),
                        accessId
                );

        appointmentService
                .deleteAppointment(

                        accessUserService
                                .getEffectiveUsername(

                                        authentication.getName(),

                                        accessId
                                ),

                        appointmentId
                );

        return ResponseEntity.ok(

                ApiResponse
                        .<Void>
                                builder()

                        .success(true)

                        .message(
                                "Appointment deleted successfully"
                        )

                        .build()
        );
    }
}