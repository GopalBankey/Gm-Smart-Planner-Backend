package com.gmsmartplanner.service.budget;


import com.gmsmartplanner.dto.request.budget.CreateEmiCategoryRequestDTO;
import com.gmsmartplanner.dto.request.budget.UpdateEmiCategoryRequestDTO;
import com.gmsmartplanner.dto.response.budget.EmiCategoryResponseDTO;

import java.util.List;

public interface EmiCategoryService {

    // =====================================
    // CREATE CATEGORY
    // =====================================

    EmiCategoryResponseDTO createCategory(
            CreateEmiCategoryRequestDTO dto
    );

    // =====================================
    // GET CATEGORIES
    // =====================================

    List<EmiCategoryResponseDTO>
    getCategories();

    // =====================================
    // GET CATEGORY
    // =====================================

    EmiCategoryResponseDTO getCategory(
            Long categoryId
    );

    // =====================================
    // UPDATE CATEGORY
    // =====================================

    EmiCategoryResponseDTO updateCategory(

            Long categoryId,

            UpdateEmiCategoryRequestDTO dto
    );

    // =====================================
    // DELETE CATEGORY
    // =====================================

    void deleteCategory(
            Long categoryId
    );
}