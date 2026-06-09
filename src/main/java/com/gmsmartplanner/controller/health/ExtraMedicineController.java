package com.gmsmartplanner.controller.health;

import com.gmsmartplanner.dto.request.health.CreateExtraMedicineRequestDTO;
import com.gmsmartplanner.dto.request.health.UpdateExtraMedicineRequestDTO;
import com.gmsmartplanner.dto.response.health.ExtraMedicineResponseDTO;
import com.gmsmartplanner.enums.health.MedicineForm;
import com.gmsmartplanner.payload.ApiResponse;
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

    // =====================================
    // CREATE
    // =====================================

    @PostMapping
    public ResponseEntity<ApiResponse<ExtraMedicineResponseDTO>>
    create(

            Authentication authentication,

            @ModelAttribute
            @Valid
            CreateExtraMedicineRequestDTO dto

    ) {

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
    public ResponseEntity<ApiResponse<List<ExtraMedicineResponseDTO>>>
    getAll(

            Authentication authentication

    ) {

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
    public ResponseEntity<ApiResponse<ExtraMedicineResponseDTO>>
    getById(

            Authentication authentication,

            @PathVariable
            Long medicineId

    ) {

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

    @PatchMapping("/{medicineId}")
    public ResponseEntity<ApiResponse<ExtraMedicineResponseDTO>>
    update(

            Authentication authentication,

            @PathVariable
            Long medicineId,

            @ModelAttribute
            UpdateExtraMedicineRequestDTO dto

    ) {

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

        extraMedicineService
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