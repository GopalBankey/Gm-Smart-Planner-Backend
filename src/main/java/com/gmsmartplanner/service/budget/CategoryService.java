package com.gmsmartplanner.service.budget;

import com.gmsmartplanner.dto.request.budget.CreateCategoryRequestDTO;
import com.gmsmartplanner.dto.request.budget.UpdateCategoryRequestDTO;
import com.gmsmartplanner.dto.response.budget.CategoryResponseDTO;
import com.gmsmartplanner.enums.todo.CategoryType;

import java.util.List;

public interface CategoryService {

    // =====================================
    // CREATE CATEGORY
    // =====================================

    CategoryResponseDTO createCategory(
            CreateCategoryRequestDTO dto
    );

    // =====================================
    // GET CATEGORIES
    // =====================================

    List<CategoryResponseDTO>
    getCategories(
            CategoryType type
    );

    // =====================================
    // GET CATEGORY
    // =====================================

    CategoryResponseDTO getCategory(
            Long categoryId
    );

    // =====================================
    // UPDATE CATEGORY
    // =====================================

    CategoryResponseDTO updateCategory(

            Long categoryId,

            UpdateCategoryRequestDTO dto
    );

    // =====================================
    // DELETE CATEGORY
    // =====================================

    void deleteCategory(
            Long categoryId
    );
}