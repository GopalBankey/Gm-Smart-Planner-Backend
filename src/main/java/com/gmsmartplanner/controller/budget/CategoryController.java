package com.gmsmartplanner.controller.budget;

import com.gmsmartplanner.dto.request.budget.CreateCategoryRequestDTO;
import com.gmsmartplanner.dto.request.budget.UpdateCategoryRequestDTO;
import com.gmsmartplanner.dto.response.budget.CategoryResponseDTO;
import com.gmsmartplanner.enums.todo.CategoryType;
import com.gmsmartplanner.payload.ApiResponse;
import com.gmsmartplanner.service.budget.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService
            categoryService;

    // =====================================
    // CREATE CATEGORY
    // =====================================

    @PostMapping(
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<ApiResponse<CategoryResponseDTO>>
    createCategory(

            @Valid
            @ModelAttribute
            CreateCategoryRequestDTO dto

    ) {

        CategoryResponseDTO response =
                categoryService
                        .createCategory(dto);

        return ResponseEntity.ok(

                ApiResponse
                        .<CategoryResponseDTO>builder()

                        .success(true)

                        .message(
                                "Category created successfully"
                        )

                        .data(response)

                        .build()
        );
    }

    // =====================================
    // GET CATEGORIES
    // =====================================

    @GetMapping
    public ResponseEntity<ApiResponse<List<CategoryResponseDTO>>>
    getCategories(

            @RequestParam(required = false)
            CategoryType type

    ) {

        List<CategoryResponseDTO> response =
                categoryService
                        .getCategories(type);

        return ResponseEntity.ok(

                ApiResponse
                        .<List<CategoryResponseDTO>>builder()

                        .success(true)

                        .message(
                                "Categories fetched successfully"
                        )

                        .data(response)

                        .build()
        );
    }

    // =====================================
    // GET CATEGORY
    // =====================================

    @GetMapping("/{categoryId}")
    public ResponseEntity<ApiResponse<CategoryResponseDTO>>
    getCategory(

            @PathVariable
            Long categoryId

    ) {

        CategoryResponseDTO response =
                categoryService
                        .getCategory(categoryId);

        return ResponseEntity.ok(

                ApiResponse
                        .<CategoryResponseDTO>builder()

                        .success(true)

                        .message(
                                "Category fetched successfully"
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
    public ResponseEntity<ApiResponse<CategoryResponseDTO>>
    updateCategory(

            @PathVariable
            Long categoryId,

            @ModelAttribute
            UpdateCategoryRequestDTO dto

    ) {

        CategoryResponseDTO response =
                categoryService
                        .updateCategory(

                                categoryId,

                                dto
                        );

        return ResponseEntity.ok(

                ApiResponse
                        .<CategoryResponseDTO>builder()

                        .success(true)

                        .message(
                                "Category updated successfully"
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
                .deleteCategory(categoryId);

        return ResponseEntity.ok(

                ApiResponse
                        .<Void>builder()

                        .success(true)

                        .message(
                                "Category deleted successfully"
                        )

                        .build()
        );
    }
}