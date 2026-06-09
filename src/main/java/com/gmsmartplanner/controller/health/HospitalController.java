package com.gmsmartplanner.controller.health;

import com.gmsmartplanner.dto.request.health.CreateHospitalRequestDTO;
import com.gmsmartplanner.dto.request.health.UpdateHospitalRequestDTO;
import com.gmsmartplanner.dto.response.health.HospitalResponseDTO;
import com.gmsmartplanner.payload.ApiResponse;
import com.gmsmartplanner.service.health.HospitalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/hospitals")
public class HospitalController {

    private final HospitalService
            hospitalService;

    // =====================================
    // CREATE
    // =====================================

    @PostMapping
    public ResponseEntity<ApiResponse<HospitalResponseDTO>>
    createHospital(

            Authentication authentication,

            @Valid
            @RequestBody
            CreateHospitalRequestDTO dto

    ) {

        HospitalResponseDTO response =
                hospitalService
                        .createHospital(

                                authentication.getName(),

                                dto
                        );

        return ResponseEntity.ok(

                ApiResponse
                        .<HospitalResponseDTO>builder()

                        .success(true)

                        .message(
                                "Hospital created successfully"
                        )

                        .data(response)

                        .build()
        );
    }

    // =====================================
    // GET ALL
    // =====================================

    @GetMapping
    public ResponseEntity<ApiResponse<List<HospitalResponseDTO>>>
    getHospitals(

            Authentication authentication

    ) {

        return ResponseEntity.ok(

                ApiResponse
                        .<List<HospitalResponseDTO>>builder()

                        .success(true)

                        .message(
                                "Hospitals fetched successfully"
                        )

                        .data(

                                hospitalService
                                        .getHospitals(

                                                authentication.getName()
                                        )
                        )

                        .build()
        );
    }

    // =====================================
    // GET BY ID
    // =====================================

    @GetMapping("/{hospitalId}")
    public ResponseEntity<ApiResponse<HospitalResponseDTO>>
    getHospital(

            Authentication authentication,

            @PathVariable
            Long hospitalId

    ) {

        return ResponseEntity.ok(

                ApiResponse
                        .<HospitalResponseDTO>builder()

                        .success(true)

                        .message(
                                "Hospital fetched successfully"
                        )

                        .data(

                                hospitalService
                                        .getHospital(

                                                authentication.getName(),

                                                hospitalId
                                        )
                        )

                        .build()
        );
    }

    // =====================================
    // UPDATE
    // =====================================

    @PatchMapping("/{hospitalId}")
    public ResponseEntity<ApiResponse<HospitalResponseDTO>>
    updateHospital(

            Authentication authentication,

            @PathVariable
            Long hospitalId,

            @RequestBody
            UpdateHospitalRequestDTO dto

    ) {

        return ResponseEntity.ok(

                ApiResponse
                        .<HospitalResponseDTO>builder()

                        .success(true)

                        .message(
                                "Hospital updated successfully"
                        )

                        .data(

                                hospitalService
                                        .updateHospital(

                                                authentication.getName(),

                                                hospitalId,

                                                dto
                                        )
                        )

                        .build()
        );
    }

    // =====================================
    // DELETE
    // =====================================

    @DeleteMapping("/{hospitalId}")
    public ResponseEntity<ApiResponse<Void>>
    deleteHospital(

            Authentication authentication,

            @PathVariable
            Long hospitalId

    ) {

        hospitalService
                .deleteHospital(

                        authentication.getName(),

                        hospitalId
                );

        return ResponseEntity.ok(

                ApiResponse
                        .<Void>builder()

                        .success(true)

                        .message(
                                "Hospital deleted successfully"
                        )

                        .build()
        );
    }
}