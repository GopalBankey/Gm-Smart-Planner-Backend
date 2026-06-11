package com.gmsmartplanner.controller.health;

import com.gmsmartplanner.dto.request.health.CreateHospitalRequestDTO;
import com.gmsmartplanner.dto.request.health.UpdateHospitalRequestDTO;
import com.gmsmartplanner.dto.response.health.HospitalResponseDTO;
import com.gmsmartplanner.payload.ApiResponse;
import com.gmsmartplanner.service.AccessUserService;
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

    private final AccessUserService
            accessUserService;

    // =====================================
    // CREATE
    // =====================================

    @PostMapping
    public ResponseEntity<ApiResponse<HospitalResponseDTO>>
    createHospital(

            Authentication authentication,

            @RequestHeader(
                    value =
                            "X-ACCESS-ID",

                    required =
                            false
            )
            Long accessId,

            @Valid
            @RequestBody
            CreateHospitalRequestDTO dto

    ) {

        accessUserService
                .checkCreatePermission(

                        authentication.getName(),

                        accessId
                );

        HospitalResponseDTO response =

                hospitalService
                        .createHospital(

                                accessUserService
                                        .getEffectiveUsername(

                                                authentication.getName(),

                                                accessId
                                        ),

                                dto
                        );

        return ResponseEntity.ok(

                ApiResponse
                        .<HospitalResponseDTO>builder()

                        .success(true)

                        .message(
                                "Hospital created successfully"
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
    public ResponseEntity<ApiResponse<List<HospitalResponseDTO>>>
    getHospitals(

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
                        .<List<HospitalResponseDTO>>builder()

                        .success(true)

                        .message(
                                "Hospitals fetched successfully"
                        )

                        .data(

                                hospitalService
                                        .getHospitals(

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

    @GetMapping("/{hospitalId}")
    public ResponseEntity<ApiResponse<HospitalResponseDTO>>
    getHospital(

            Authentication authentication,

            @RequestHeader(
                    value =
                            "X-ACCESS-ID",

                    required =
                            false
            )
            Long accessId,

            @PathVariable
            Long hospitalId

    ) {

        accessUserService
                .checkViewPermission(

                        authentication.getName(),

                        accessId
                );

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

                                                accessUserService
                                                        .getEffectiveUsername(

                                                                authentication.getName(),

                                                                accessId
                                                        ),

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

            @RequestHeader(
                    value =
                            "X-ACCESS-ID",

                    required =
                            false
            )
            Long accessId,

            @PathVariable
            Long hospitalId,

            @RequestBody
            UpdateHospitalRequestDTO dto

    ) {

        accessUserService
                .checkUpdatePermission(

                        authentication.getName(),

                        accessId
                );

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

                                                accessUserService
                                                        .getEffectiveUsername(

                                                                authentication.getName(),

                                                                accessId
                                                        ),

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

            @RequestHeader(
                    value =
                            "X-ACCESS-ID",

                    required =
                            false
            )
            Long accessId,

            @PathVariable
            Long hospitalId

    ) {

        accessUserService
                .checkDeletePermission(

                        authentication.getName(),

                        accessId
                );

        hospitalService
                .deleteHospital(

                        accessUserService
                                .getEffectiveUsername(

                                        authentication.getName(),

                                        accessId
                                ),

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