package com.gmsmartplanner.controller.health;

import com.gmsmartplanner.dto.request.health.CreateDoctorRequestDTO;
import com.gmsmartplanner.dto.request.health.UpdateDoctorRequestDTO;
import com.gmsmartplanner.dto.response.health.DoctorResponseDTO;
import com.gmsmartplanner.payload.ApiResponse;
import com.gmsmartplanner.service.AccessUserService;
import com.gmsmartplanner.service.health.DoctorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/doctors")
public class DoctorController {

    private final DoctorService
            doctorService;

    private final AccessUserService
            accessUserService;

    // =====================================
    // CREATE
    // =====================================

    @PostMapping
    public ResponseEntity<ApiResponse<DoctorResponseDTO>>
    createDoctor(

            Authentication authentication,

            @RequestHeader(
                    value = "X-ACCESS-ID",
                    required = false
            )
            Long accessId,

            @Valid
            @RequestBody
            CreateDoctorRequestDTO dto

    ) {

        accessUserService
                .checkCreatePermission(
                        authentication.getName(),
                        accessId
                );

        DoctorResponseDTO response =

                doctorService
                        .createDoctor(

                                accessUserService
                                        .getEffectiveUsername(

                                                authentication.getName(),

                                                accessId
                                        ),

                                dto
                        );

        return ResponseEntity.ok(

                ApiResponse
                        .<DoctorResponseDTO>builder()

                        .success(true)

                        .message(
                                "Doctor created successfully"
                        )

                        .data(
                                response
                        )

                        .build()
        );
    }

    // =====================================
    // GET ALL
    // =====================================

    @GetMapping
    public ResponseEntity<ApiResponse<List<DoctorResponseDTO>>>
    getDoctors(

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
                        .<List<DoctorResponseDTO>>builder()

                        .success(true)

                        .message(
                                "Doctors fetched successfully"
                        )

                        .data(

                                doctorService
                                        .getDoctors(

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

    @GetMapping("/{doctorId}")
    public ResponseEntity<ApiResponse<DoctorResponseDTO>>
    getDoctor(

            Authentication authentication,

            @RequestHeader(
                    value = "X-ACCESS-ID",
                    required = false
            )
            Long accessId,

            @PathVariable
            Long doctorId

    ) {

        accessUserService
                .checkViewPermission(
                        authentication.getName(),
                        accessId
                );

        return ResponseEntity.ok(

                ApiResponse
                        .<DoctorResponseDTO>builder()

                        .success(true)

                        .message(
                                "Doctor fetched successfully"
                        )

                        .data(

                                doctorService
                                        .getDoctor(

                                                accessUserService
                                                        .getEffectiveUsername(

                                                                authentication.getName(),

                                                                accessId
                                                        ),

                                                doctorId
                                        )
                        )

                        .build()
        );
    }

    // =====================================
    // UPDATE
    // =====================================

    @PatchMapping("/{doctorId}")
    public ResponseEntity<ApiResponse<DoctorResponseDTO>>
    updateDoctor(

            Authentication authentication,

            @RequestHeader(
                    value = "X-ACCESS-ID",
                    required = false
            )
            Long accessId,

            @PathVariable
            Long doctorId,

            @RequestBody
            UpdateDoctorRequestDTO dto

    ) {

        accessUserService
                .checkUpdatePermission(
                        authentication.getName(),
                        accessId
                );

        return ResponseEntity.ok(

                ApiResponse
                        .<DoctorResponseDTO>builder()

                        .success(true)

                        .message(
                                "Doctor updated successfully"
                        )

                        .data(

                                doctorService
                                        .updateDoctor(

                                                accessUserService
                                                        .getEffectiveUsername(

                                                                authentication.getName(),

                                                                accessId
                                                        ),

                                                doctorId,

                                                dto
                                        )
                        )

                        .build()
        );
    }

    // =====================================
    // DELETE
    // =====================================

    @DeleteMapping("/{doctorId}")
    public ResponseEntity<ApiResponse<Void>>
    deleteDoctor(

            Authentication authentication,

            @RequestHeader(
                    value = "X-ACCESS-ID",
                    required = false
            )
            Long accessId,

            @PathVariable
            Long doctorId

    ) {

        accessUserService
                .checkDeletePermission(
                        authentication.getName(),
                        accessId
                );

        doctorService
                .deleteDoctor(

                        accessUserService
                                .getEffectiveUsername(

                                        authentication.getName(),

                                        accessId
                                ),

                        doctorId
                );

        return ResponseEntity.ok(

                ApiResponse
                        .<Void>builder()

                        .success(true)

                        .message(
                                "Doctor deleted successfully"
                        )

                        .build()
        );
    }
}