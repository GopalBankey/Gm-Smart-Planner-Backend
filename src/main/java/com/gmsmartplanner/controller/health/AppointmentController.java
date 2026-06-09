package com.gmsmartplanner.controller.health;

import com.gmsmartplanner.dto.request.health.CreateAppointmentRequestDTO;
import com.gmsmartplanner.dto.request.health.UpdateAppointmentRequestDTO;
import com.gmsmartplanner.dto.response.health.AppointmentResponseDTO;
import com.gmsmartplanner.payload.ApiResponse;
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

            @Valid
            @RequestBody
            CreateAppointmentRequestDTO dto

    ) {

        return ResponseEntity.ok(

                ApiResponse

                        .<AppointmentResponseDTO>
                                builder()

                        .success(
                                true
                        )

                        .message(
                                "Appointment created successfully"
                        )

                        .data(

                                appointmentService
                                        .createAppointment(

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
    public ResponseEntity<
            ApiResponse<
                    List<
                            AppointmentResponseDTO
                            >
                    >
            >

    getAppointments(

            Authentication authentication

    ) {

        return ResponseEntity.ok(

                ApiResponse

                        .<List<
                                AppointmentResponseDTO
                                >>
                                builder()

                        .success(
                                true
                        )

                        .message(
                                "Appointments fetched successfully"
                        )

                        .data(

                                appointmentService
                                        .getAppointments(

                                                authentication.getName()
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

            @PathVariable
            Long appointmentId

    ) {

        return ResponseEntity.ok(

                ApiResponse

                        .<AppointmentResponseDTO>
                                builder()

                        .success(
                                true
                        )

                        .message(
                                "Appointment fetched successfully"
                        )

                        .data(

                                appointmentService
                                        .getAppointment(

                                                authentication.getName(),

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

            @PathVariable
            Long appointmentId,

            @Valid
            @RequestBody
            UpdateAppointmentRequestDTO dto

    ) {

        return ResponseEntity.ok(

                ApiResponse

                        .<AppointmentResponseDTO>
                                builder()

                        .success(
                                true
                        )

                        .message(
                                "Appointment updated successfully"
                        )

                        .data(

                                appointmentService
                                        .updateAppointment(

                                                authentication.getName(),

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

            @PathVariable
            Long appointmentId

    ) {

        return ResponseEntity.ok(

                ApiResponse

                        .<AppointmentResponseDTO>
                                builder()

                        .success(
                                true
                        )

                        .message(
                                "Appointment completed successfully"
                        )

                        .data(

                                appointmentService
                                        .completeAppointment(

                                                authentication.getName(),

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

            @PathVariable
            Long appointmentId

    ) {

        appointmentService
                .deleteAppointment(

                        authentication.getName(),

                        appointmentId
                );

        return ResponseEntity.ok(

                ApiResponse

                        .<Void>
                                builder()

                        .success(
                                true
                        )

                        .message(
                                "Appointment deleted successfully"
                        )

                        .build()
        );
    }
}