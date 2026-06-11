package com.gmsmartplanner.controller.health;

import com.gmsmartplanner.dto.request.health.CreateMedicineRequestDTO;
import com.gmsmartplanner.dto.request.health.UpdateMedicineRequestDTO;
import com.gmsmartplanner.dto.response.health.MedicineResponseDTO;
import com.gmsmartplanner.enums.health.MealType;
import com.gmsmartplanner.enums.health.MedicineForm;
import com.gmsmartplanner.payload.ApiResponse;
import com.gmsmartplanner.service.AccessUserService;
import com.gmsmartplanner.service.health.MedicineService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/medicines")
public class MedicineController {

    private final MedicineService
            medicineService;

    private final AccessUserService
            accessUserService;

    // =====================================
    // CREATE
    // =====================================

    @PostMapping(
            consumes = {
                    "multipart/form-data"
            }
    )
    public ResponseEntity<ApiResponse<MedicineResponseDTO>>
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
            CreateMedicineRequestDTO dto

    ) {

        accessUserService
                .checkCreatePermission(

                        authentication.getName(),

                        accessId
                );

        return ResponseEntity.ok(

                ApiResponse
                        .<MedicineResponseDTO>builder()

                        .success(true)

                        .message(
                                "Medicine created successfully"
                        )

                        .data(

                                medicineService
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
    public ResponseEntity<ApiResponse<List<MedicineResponseDTO>>>
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
                        .<List<MedicineResponseDTO>>builder()

                        .success(true)

                        .message(
                                "Medicines fetched successfully"
                        )

                        .data(

                                medicineService
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
    // GET
    // =====================================

    @GetMapping("/{medicineId}")
    public ResponseEntity<ApiResponse<MedicineResponseDTO>>
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
            Long medicineId

    ) {

        accessUserService
                .checkViewPermission(

                        authentication.getName(),

                        accessId
                );

        return ResponseEntity.ok(

                ApiResponse
                        .<MedicineResponseDTO>builder()

                        .success(true)

                        .message(
                                "Medicine fetched successfully"
                        )

                        .data(

                                medicineService
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

    @PatchMapping(
            value =
                    "/{medicineId}",

            consumes = {
                    "multipart/form-data"
            }
    )
    public ResponseEntity<ApiResponse<MedicineResponseDTO>>
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
            Long medicineId,

            @ModelAttribute
            UpdateMedicineRequestDTO dto

    ) {

        accessUserService
                .checkUpdatePermission(

                        authentication.getName(),

                        accessId
                );

        return ResponseEntity.ok(

                ApiResponse
                        .<MedicineResponseDTO>builder()

                        .success(true)

                        .message(
                                "Medicine updated successfully"
                        )

                        .data(

                                medicineService
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
                    value =
                            "X-ACCESS-ID",

                    required =
                            false
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

        medicineService
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
    forms() {

        return ResponseEntity.ok(

                ApiResponse
                        .<List<String>>builder()

                        .success(true)

                        .message(
                                "Medicine forms fetched"
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

    // =====================================
    // MEAL TYPES
    // =====================================

    @GetMapping("/meal-types")
    public ResponseEntity<ApiResponse<List<String>>>
    mealTypes() {

        return ResponseEntity.ok(

                ApiResponse
                        .<List<String>>builder()

                        .success(true)

                        .message(
                                "Meal types fetched"
                        )

                        .data(

                                Arrays.stream(
                                                MealType.values()
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