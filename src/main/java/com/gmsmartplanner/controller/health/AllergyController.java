package com.gmsmartplanner.controller.health;

import com.gmsmartplanner.dto.request.health.CreateAllergyRequestDTO;
import com.gmsmartplanner.dto.request.health.UpdateAllergyRequestDTO;
import com.gmsmartplanner.dto.response.health.AllergyResponseDTO;
import com.gmsmartplanner.enums.health.AllergySeverity;
import com.gmsmartplanner.enums.health.ReactionType;
import com.gmsmartplanner.payload.ApiResponse;
import com.gmsmartplanner.service.AccessUserService;
import com.gmsmartplanner.service.health.AllergyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(
        "/api/v1/allergies"
)
public class AllergyController {

    private final AllergyService
            allergyService;

    private final AccessUserService
            accessUserService;

    // =====================================
    // CREATE
    // =====================================

    @PostMapping
    public ResponseEntity<
            ApiResponse<AllergyResponseDTO>
            >
    create(

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
            CreateAllergyRequestDTO dto

    ) {

        accessUserService
                .checkCreatePermission(

                        authentication.getName(),

                        accessId
                );

        return ResponseEntity.ok(

                ApiResponse
                        .<AllergyResponseDTO>builder()

                        .success(true)

                        .message(
                                "Allergy created successfully"
                        )

                        .data(

                                allergyService
                                        .createAllergy(

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
            ApiResponse<List<AllergyResponseDTO>>
            >
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
                        .<List<AllergyResponseDTO>>builder()

                        .success(true)

                        .message(
                                "Allergies fetched successfully"
                        )

                        .data(

                                allergyService
                                        .getAllergies(

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
            "/{allergyId}"
    )
    public ResponseEntity<
            ApiResponse<AllergyResponseDTO>
            >
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
            Long allergyId

    ) {

        accessUserService
                .checkViewPermission(

                        authentication.getName(),

                        accessId
                );

        return ResponseEntity.ok(

                ApiResponse
                        .<AllergyResponseDTO>builder()

                        .success(true)

                        .message(
                                "Allergy fetched successfully"
                        )

                        .data(

                                allergyService
                                        .getAllergy(

                                                accessUserService
                                                        .getEffectiveUsername(

                                                                authentication.getName(),

                                                                accessId
                                                        ),

                                                allergyId
                                        )
                        )

                        .build()
        );
    }

    // =====================================
    // UPDATE
    // =====================================

    @PatchMapping(
            "/{allergyId}"
    )
    public ResponseEntity<
            ApiResponse<AllergyResponseDTO>
            >
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
            Long allergyId,

            @Valid
            @RequestBody
            UpdateAllergyRequestDTO dto

    ) {

        accessUserService
                .checkUpdatePermission(

                        authentication.getName(),

                        accessId
                );

        return ResponseEntity.ok(

                ApiResponse
                        .<AllergyResponseDTO>builder()

                        .success(true)

                        .message(
                                "Allergy updated successfully"
                        )

                        .data(

                                allergyService
                                        .updateAllergy(

                                                accessUserService
                                                        .getEffectiveUsername(

                                                                authentication.getName(),

                                                                accessId
                                                        ),

                                                allergyId,

                                                dto
                                        )
                        )

                        .build()
        );
    }

    // =====================================
    // DELETE
    // =====================================

    @DeleteMapping(
            "/{allergyId}"
    )
    public ResponseEntity<
            ApiResponse<Void>
            >
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
            Long allergyId

    ) {

        accessUserService
                .checkDeletePermission(

                        authentication.getName(),

                        accessId
                );

        allergyService
                .deleteAllergy(

                        accessUserService
                                .getEffectiveUsername(

                                        authentication.getName(),

                                        accessId
                                ),

                        allergyId
                );

        return ResponseEntity.ok(

                ApiResponse
                        .<Void>builder()

                        .success(true)

                        .message(
                                "Allergy deleted successfully"
                        )

                        .build()
        );
    }

    // =====================================
    // REACTIONS
    // =====================================

    @GetMapping(
            "/reactions"
    )
    public ResponseEntity<
            ApiResponse<List<String>>
            >
    reactions() {

        return ResponseEntity.ok(

                ApiResponse
                        .<List<String>>builder()

                        .success(true)

                        .message(
                                "Allergy reactions fetched successfully"
                        )

                        .data(

                                Arrays.stream(
                                                ReactionType.values()
                                        )
                                        .map(
                                                Enum::name
                                        )
                                        .toList()
                        )

                        .build()
        );
    }

    // =====================================
    // SEVERITIES
    // =====================================

    @GetMapping(
            "/severities"
    )
    public ResponseEntity<
            ApiResponse<List<String>>
            >
    severities() {

        return ResponseEntity.ok(

                ApiResponse
                        .<List<String>>builder()

                        .success(true)

                        .message(
                                "Allergy severities fetched successfully"
                        )

                        .data(

                                Arrays.stream(
                                                AllergySeverity.values()
                                        )
                                        .map(
                                                Enum::name
                                        )
                                        .toList()
                        )

                        .build()
        );
    }
}