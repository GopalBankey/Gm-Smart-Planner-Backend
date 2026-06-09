package com.gmsmartplanner.controller.health;

import com.gmsmartplanner.dto.request.health.CreateDoctorRequestDTO;
import com.gmsmartplanner.dto.request.health.UpdateDoctorRequestDTO;
import com.gmsmartplanner.dto.response.health.DoctorResponseDTO;
import com.gmsmartplanner.payload.ApiResponse;
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

    @PostMapping
    public ResponseEntity<ApiResponse<DoctorResponseDTO>>
    createDoctor(

            Authentication authentication,

            @Valid
            @RequestBody
            CreateDoctorRequestDTO dto

    ) {

        DoctorResponseDTO response =
                doctorService.createDoctor(

                        authentication.getName(),

                        dto
                );

        return ResponseEntity.ok(

                ApiResponse
                        .<DoctorResponseDTO>builder()
                        .success(true)
                        .message("Doctor created successfully")
                        .data(response)
                        .build()
        );
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<DoctorResponseDTO>>>
    getDoctors(
            Authentication authentication
    ) {

        return ResponseEntity.ok(

                ApiResponse
                        .<List<DoctorResponseDTO>>builder()
                        .success(true)
                        .message("Doctors fetched successfully")
                        .data(
                                doctorService.getDoctors(
                                        authentication.getName()
                                )
                        )
                        .build()
        );
    }

    @GetMapping("/{doctorId}")
    public ResponseEntity<ApiResponse<DoctorResponseDTO>>
    getDoctor(

            Authentication authentication,

            @PathVariable
            Long doctorId

    ) {

        return ResponseEntity.ok(

                ApiResponse
                        .<DoctorResponseDTO>builder()
                        .success(true)
                        .message("Doctor fetched successfully")
                        .data(
                                doctorService.getDoctor(
                                        authentication.getName(),
                                        doctorId
                                )
                        )
                        .build()
        );
    }

    @PatchMapping("/{doctorId}")
    public ResponseEntity<ApiResponse<DoctorResponseDTO>>
    updateDoctor(

            Authentication authentication,

            @PathVariable
            Long doctorId,

            @RequestBody
            UpdateDoctorRequestDTO dto

    ) {

        return ResponseEntity.ok(

                ApiResponse
                        .<DoctorResponseDTO>builder()
                        .success(true)
                        .message("Doctor updated successfully")
                        .data(
                                doctorService.updateDoctor(
                                        authentication.getName(),
                                        doctorId,
                                        dto
                                )
                        )
                        .build()
        );
    }

    @DeleteMapping("/{doctorId}")
    public ResponseEntity<ApiResponse<Void>>
    deleteDoctor(

            Authentication authentication,

            @PathVariable
            Long doctorId

    ) {

        doctorService.deleteDoctor(
                authentication.getName(),
                doctorId
        );

        return ResponseEntity.ok(

                ApiResponse
                        .<Void>builder()
                        .success(true)
                        .message("Doctor deleted successfully")
                        .build()
        );
    }
}