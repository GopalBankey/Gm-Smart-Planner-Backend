package com.gmsmartplanner.controller.budget;

import com.gmsmartplanner.dto.request.budget.CreateEmiCategoryRequestDTO;
import com.gmsmartplanner.dto.request.budget.UpdateEmiCategoryRequestDTO;
import com.gmsmartplanner.dto.response.budget.EmiCategoryResponseDTO;
import com.gmsmartplanner.payload.ApiResponse;
import com.gmsmartplanner.service.budget.EmiCategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/emi/categories")
@RequiredArgsConstructor
public class EmiCategoryController {

    private final EmiCategoryService
            categoryService;

    // =====================================
    // CREATE CATEGORY
    // =====================================

    @PostMapping(
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<ApiResponse<EmiCategoryResponseDTO>>
    createCategory(

            @Valid
            @ModelAttribute
            CreateEmiCategoryRequestDTO dto

    ) {

        EmiCategoryResponseDTO response =
                categoryService
                        .createCategory(dto);

        return ResponseEntity.ok(

                ApiResponse
                        .<EmiCategoryResponseDTO>builder()

                        .success(true)

                        .message(
                                "EMI category created successfully"
                        )

                        .data(response)

                        .build()
        );
    }

    // =====================================
    // GET CATEGORIES
    // =====================================

    @GetMapping
    public ResponseEntity<ApiResponse<List<EmiCategoryResponseDTO>>>
    getCategories() {

        List<EmiCategoryResponseDTO> response =
                categoryService
                        .getCategories();

        return ResponseEntity.ok(

                ApiResponse
                        .<List<EmiCategoryResponseDTO>>builder()

                        .success(true)

                        .message(
                                "EMI categories fetched successfully"
                        )

                        .data(response)

                        .build()
        );
    }

    // =====================================
    // GET CATEGORY
    // =====================================

    @GetMapping("/{categoryId}")
    public ResponseEntity<ApiResponse<EmiCategoryResponseDTO>>
    getCategory(

            @PathVariable
            Long categoryId

    ) {

        EmiCategoryResponseDTO response =
                categoryService
                        .getCategory(
                                categoryId
                        );

        return ResponseEntity.ok(

                ApiResponse
                        .<EmiCategoryResponseDTO>builder()

                        .success(true)

                        .message(
                                "EMI category fetched successfully"
                        )

                        .data(response)

                        .build()
        );
    }

    // =====================================
    // UPDATE CATEGORY
    // =====================================

    @PatchMapping(
            value = "/{categoryId}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<ApiResponse<EmiCategoryResponseDTO>>
    updateCategory(

            @PathVariable
            Long categoryId,

            @ModelAttribute
            UpdateEmiCategoryRequestDTO dto

    ) {

        EmiCategoryResponseDTO response =
                categoryService
                        .updateCategory(

                                categoryId,

                                dto
                        );

        return ResponseEntity.ok(

                ApiResponse
                        .<EmiCategoryResponseDTO>builder()

                        .success(true)

                        .message(
                                "EMI category updated successfully"
                        )

                        .data(response)

                        .build()
        );
    }

    // =====================================
    // DELETE CATEGORY
    // =====================================

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ApiResponse<Void>>
    deleteCategory(

            @PathVariable
            Long categoryId

    ) {

        categoryService
                .deleteCategory(
                        categoryId
                );

        return ResponseEntity.ok(

                ApiResponse
                        .<Void>builder()

                        .success(true)

                        .message(
                                "EMI category deleted successfully"
                        )

                        .build()
        );
    }
}