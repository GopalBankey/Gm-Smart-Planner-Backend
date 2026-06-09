package com.gmsmartplanner.controller.health;

import com.gmsmartplanner.dto.request.health.CreateMedicineRequestDTO;
import com.gmsmartplanner.dto.request.health.UpdateMedicineRequestDTO;
import com.gmsmartplanner.dto.response.health.MedicineResponseDTO;
import com.gmsmartplanner.enums.health.MealType;
import com.gmsmartplanner.enums.health.MedicineForm;
import com.gmsmartplanner.payload.ApiResponse;
import com.gmsmartplanner.service.health.MedicineService;
import jakarta.validation.Valid;
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

            @ModelAttribute
            CreateMedicineRequestDTO dto

    ) {

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
    public ResponseEntity<ApiResponse<List<MedicineResponseDTO>>>
    getAll(

            Authentication authentication

    ) {

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

                                                authentication.getName()
                                        )
                        )

                        .build()
        );
    }

    // =====================================
    // GET BY ID
    // =====================================

    @GetMapping("/{medicineId}")
    public ResponseEntity<ApiResponse<MedicineResponseDTO>>
    getById(

            Authentication authentication,

            @PathVariable
            Long medicineId

    ) {

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

                                                authentication.getName(),

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
            value = "/{medicineId}",
            consumes = {
                    "multipart/form-data"
            }
    )    public ResponseEntity<ApiResponse<MedicineResponseDTO>>
    update(

            Authentication authentication,

            @PathVariable
            Long medicineId,

            @ModelAttribute
            UpdateMedicineRequestDTO dto

    ) {

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

                                                authentication.getName(),

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

            @PathVariable
            Long medicineId

    ) {

        medicineService
                .deleteMedicine(

                        authentication.getName(),

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