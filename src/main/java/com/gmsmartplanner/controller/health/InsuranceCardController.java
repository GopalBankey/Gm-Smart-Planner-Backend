package com.gmsmartplanner.controller.health;

import com.gmsmartplanner.dto.request.health.CreateInsuranceCardRequestDTO;
import com.gmsmartplanner.dto.request.health.UpdateInsuranceCardRequestDTO;
import com.gmsmartplanner.dto.response.health.InsuranceCardResponseDTO;
import com.gmsmartplanner.payload.ApiResponse;
import com.gmsmartplanner.service.AccessUserService;
import com.gmsmartplanner.service.health.InsuranceCardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/insurance-cards")
public class InsuranceCardController {

    private final InsuranceCardService
            insuranceCardService;

    private final AccessUserService
            accessUserService;

    // =====================================
    // CREATE
    // =====================================

    @PostMapping(
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<ApiResponse<InsuranceCardResponseDTO>>
    createInsuranceCard(

            Authentication authentication,

            @RequestHeader(
                    value = "X-ACCESS-ID",
                    required = false
            )
            Long accessId,

            @Valid
            @ModelAttribute
            CreateInsuranceCardRequestDTO dto

    ) {

        accessUserService
                .checkCreatePermission(
                        authentication.getName(),
                        accessId
                );

        InsuranceCardResponseDTO response =

                insuranceCardService
                        .createInsuranceCard(

                                accessUserService
                                        .getEffectiveUsername(

                                                authentication.getName(),

                                                accessId
                                        ),

                                dto
                        );

        return ResponseEntity.ok(

                ApiResponse
                        .<InsuranceCardResponseDTO>builder()

                        .success(true)

                        .message(
                                "Insurance card created successfully"
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
    public ResponseEntity<ApiResponse<List<InsuranceCardResponseDTO>>>
    getInsuranceCards(

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
                        .<List<InsuranceCardResponseDTO>>builder()

                        .success(true)

                        .message(
                                "Insurance cards fetched successfully"
                        )

                        .data(

                                insuranceCardService
                                        .getInsuranceCards(

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

    @GetMapping("/{insuranceCardId}")
    public ResponseEntity<ApiResponse<InsuranceCardResponseDTO>>
    getInsuranceCard(

            Authentication authentication,

            @RequestHeader(
                    value = "X-ACCESS-ID",
                    required = false
            )
            Long accessId,

            @PathVariable
            Long insuranceCardId

    ) {

        accessUserService
                .checkViewPermission(
                        authentication.getName(),
                        accessId
                );

        return ResponseEntity.ok(

                ApiResponse
                        .<InsuranceCardResponseDTO>builder()

                        .success(true)

                        .message(
                                "Insurance card fetched successfully"
                        )

                        .data(

                                insuranceCardService
                                        .getInsuranceCard(

                                                accessUserService
                                                        .getEffectiveUsername(

                                                                authentication.getName(),

                                                                accessId
                                                        ),

                                                insuranceCardId
                                        )
                        )

                        .build()
        );
    }

    // =====================================
    // UPDATE
    // =====================================

    @PatchMapping(
            value = "/{insuranceCardId}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<ApiResponse<InsuranceCardResponseDTO>>
    updateInsuranceCard(

            Authentication authentication,

            @RequestHeader(
                    value = "X-ACCESS-ID",
                    required = false
            )
            Long accessId,

            @PathVariable
            Long insuranceCardId,

            @ModelAttribute
            UpdateInsuranceCardRequestDTO dto

    ) {

        accessUserService
                .checkUpdatePermission(
                        authentication.getName(),
                        accessId
                );

        return ResponseEntity.ok(

                ApiResponse
                        .<InsuranceCardResponseDTO>builder()

                        .success(true)

                        .message(
                                "Insurance card updated successfully"
                        )

                        .data(

                                insuranceCardService
                                        .updateInsuranceCard(

                                                accessUserService
                                                        .getEffectiveUsername(

                                                                authentication.getName(),

                                                                accessId
                                                        ),

                                                insuranceCardId,

                                                dto
                                        )
                        )

                        .build()
        );
    }

    // =====================================
    // DELETE
    // =====================================

    @DeleteMapping("/{insuranceCardId}")
    public ResponseEntity<ApiResponse<Void>>
    deleteInsuranceCard(

            Authentication authentication,

            @RequestHeader(
                    value = "X-ACCESS-ID",
                    required = false
            )
            Long accessId,

            @PathVariable
            Long insuranceCardId

    ) {

        accessUserService
                .checkDeletePermission(
                        authentication.getName(),
                        accessId
                );

        insuranceCardService
                .deleteInsuranceCard(

                        accessUserService
                                .getEffectiveUsername(

                                        authentication.getName(),

                                        accessId
                                ),

                        insuranceCardId
                );

        return ResponseEntity.ok(

                ApiResponse
                        .<Void>builder()

                        .success(true)

                        .message(
                                "Insurance card deleted successfully"
                        )

                        .build()
        );
    }
}