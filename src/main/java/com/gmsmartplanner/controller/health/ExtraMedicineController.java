package com.gmsmartplanner.controller.health;

import com.gmsmartplanner.dto.request.health.CreateExtraMedicineRequestDTO;
import com.gmsmartplanner.dto.request.health.UpdateExtraMedicineRequestDTO;
import com.gmsmartplanner.dto.response.health.ExtraMedicineResponseDTO;
import com.gmsmartplanner.enums.health.MedicineForm;
import com.gmsmartplanner.payload.ApiResponse;
import com.gmsmartplanner.service.AccessUserService;
import com.gmsmartplanner.service.health.ExtraMedicineService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/extra-medicines")
public class ExtraMedicineController {

    private final ExtraMedicineService
            extraMedicineService;

    private final AccessUserService
            accessUserService;

    // =====================================
    // CREATE
    // =====================================

    @PostMapping
    public ResponseEntity<ApiResponse<ExtraMedicineResponseDTO>>
    create(

            Authentication authentication,

            @RequestHeader(
                    value = "X-ACCESS-ID",
                    required = false
            )
            Long accessId,

            @ModelAttribute
            @Valid
            CreateExtraMedicineRequestDTO dto

    ) {

        accessUserService
                .checkCreatePermission(
                        authentication.getName(),
                        accessId
                );

        return ResponseEntity.ok(

                ApiResponse
                        .<ExtraMedicineResponseDTO>builder()

                        .success(true)

                        .message(
                                "Medicine created successfully"
                        )

                        .data(

                                extraMedicineService
                                        .createMedicine(

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
    public ResponseEntity<ApiResponse<List<ExtraMedicineResponseDTO>>>
    getAll(

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
                        .<List<ExtraMedicineResponseDTO>>builder()

                        .success(true)

                        .message(
                                "Medicines fetched successfully"
                        )

                        .data(

                                extraMedicineService
                                        .getMedicines(

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

    @GetMapping("/{medicineId}")
    public ResponseEntity<ApiResponse<ExtraMedicineResponseDTO>>
    getById(

            Authentication authentication,

            @RequestHeader(
                    value = "X-ACCESS-ID",
                    required = false
            )
            Long accessId,

            @PathVariable
            Long medicineId

    ) {

        accessUserService
                .checkViewPermission(
                        authentication.getName(),
                        accessId
                );

        return ResponseEntity.ok(

                ApiResponse
                        .<ExtraMedicineResponseDTO>builder()

                        .success(true)

                        .message(
                                "Medicine fetched successfully"
                        )

                        .data(

                                extraMedicineService
                                        .getMedicine(

                                                accessUserService
                                                        .getEffectiveUsername(

                                                                authentication.getName(),

                                                                accessId
                                                        ),

                                                medicineId
                                        )
                        )

                        .build()
        );
    }

    // =====================================
    // UPDATE
    // =====================================

    @PatchMapping("/{medicineId}")
    public ResponseEntity<ApiResponse<ExtraMedicineResponseDTO>>
    update(

            Authentication authentication,

            @RequestHeader(
                    value = "X-ACCESS-ID",
                    required = false
            )
            Long accessId,

            @PathVariable
            Long medicineId,

            @ModelAttribute
            UpdateExtraMedicineRequestDTO dto

    ) {

        accessUserService
                .checkUpdatePermission(
                        authentication.getName(),
                        accessId
                );

        return ResponseEntity.ok(

                ApiResponse
                        .<ExtraMedicineResponseDTO>builder()

                        .success(true)

                        .message(
                                "Medicine updated successfully"
                        )

                        .data(

                                extraMedicineService
                                        .updateMedicine(

                                                accessUserService
                                                        .getEffectiveUsername(

                                                                authentication.getName(),

                                                                accessId
                                                        ),

                                                medicineId,

                                                dto
                                        )
                        )

                        .build()
        );
    }

    // =====================================
    // DELETE
    // =====================================

    @DeleteMapping("/{medicineId}")
    public ResponseEntity<ApiResponse<Void>>
    delete(

            Authentication authentication,

            @RequestHeader(
                    value = "X-ACCESS-ID",
                    required = false
            )
            Long accessId,

            @PathVariable
            Long medicineId

    ) {

        accessUserService
                .checkDeletePermission(
                        authentication.getName(),
                        accessId
                );

        extraMedicineService
                .deleteMedicine(

                        accessUserService
                                .getEffectiveUsername(

                                        authentication.getName(),

                                        accessId
                                ),

                        medicineId
                );

        return ResponseEntity.ok(

                ApiResponse
                        .<Void>builder()

                        .success(true)

                        .message(
                                "Medicine deleted successfully"
                        )

                        .build()
        );
    }

    // =====================================
    // FORMS
    // =====================================

    @GetMapping("/forms")
    public ResponseEntity<ApiResponse<List<String>>>
    getForms() {

        return ResponseEntity.ok(

                ApiResponse
                        .<List<String>>builder()

                        .success(true)

                        .message(
                                "Medicine forms fetched successfully"
                        )

                        .data(

                                Arrays.stream(
                                                MedicineForm.values()
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