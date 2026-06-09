package com.gmsmartplanner.service.impl.budget;

import com.gmsmartplanner.dto.request.budget.CreateCategoryRequestDTO;
import com.gmsmartplanner.dto.request.budget.UpdateCategoryRequestDTO;
import com.gmsmartplanner.dto.response.budget.CategoryResponseDTO;
import com.gmsmartplanner.entity.budget.Category;
import com.gmsmartplanner.enums.todo.CategoryType;
import com.gmsmartplanner.exception.ResourceNotFoundException;
import com.gmsmartplanner.mapper.budget.CategoryMapper;
import com.gmsmartplanner.repository.budget.CategoryRepository;
import com.gmsmartplanner.service.budget.CategoryService;
import com.gmsmartplanner.service.FileUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl
        implements CategoryService {

    private final CategoryRepository
            categoryRepository;

    private final CategoryMapper
            categoryMapper;

    private final FileUploadService
            fileUploadService;

    // =====================================
    // CREATE CATEGORY
    // =====================================

    @Override
    public CategoryResponseDTO
    createCategory(

            CreateCategoryRequestDTO dto

    ) {

        Category category =
                categoryMapper
                        .createCategory(dto);

        // =====================================
        // UPLOAD ICON
        // =====================================

        if (dto.getIcon() != null
                && !dto.getIcon().isEmpty()) {

            String iconUrl =
                    fileUploadService
                            .uploadImage(

                                    dto.getIcon(),

                                    "categories"
                            );

            category.setIcon(
                    iconUrl
            );
        }

        Category savedCategory =
                categoryRepository.save(
                        category
                );

        return categoryMapper
                .mapToResponse(
                        savedCategory
                );
    }

    // =====================================
    // GET CATEGORIES
    // =====================================

    @Override
    public List<CategoryResponseDTO>
    getCategories(

            CategoryType type

    ) {

        List<Category> categories;

        // =====================================
        // GET ALL
        // =====================================

        if (type == null) {

            categories =
                    categoryRepository
                            .findAllByActiveTrue();

        } else {

            List<CategoryType> types =
                    List.of(
                            type,
                            CategoryType.BOTH
                    );

            categories =
                    categoryRepository
                            .findAllByTypeInAndActiveTrue(
                                    types
                            );
        }

        return categories
                .stream()
                .map(categoryMapper::mapToResponse)
                .toList();
    }

    // =====================================
    // GET CATEGORY
    // =====================================

    @Override
    public CategoryResponseDTO
    getCategory(

            Long categoryId

    ) {

        return categoryMapper
                .mapToResponse(
                        getCategoryEntity(
                                categoryId
                        )
                );
    }

    // =====================================
    // UPDATE CATEGORY
    // =====================================

    @Override
    public CategoryResponseDTO
    updateCategory(

            Long categoryId,

            UpdateCategoryRequestDTO dto

    ) {

        Category category =
                getCategoryEntity(
                        categoryId
                );

        categoryMapper
                .updateCategory(

                        category,

                        dto
                );

        // =====================================
        // UPDATE ICON
        // =====================================

        if (dto.getIcon() != null
                && !dto.getIcon().isEmpty()) {

            String iconUrl =
                    fileUploadService
                            .uploadImage(

                                    dto.getIcon(),

                                    "categories"
                            );

            category.setIcon(
                    iconUrl
            );
        }

        Category updatedCategory =
                categoryRepository.save(
                        category
                );

        return categoryMapper
                .mapToResponse(
                        updatedCategory
                );
    }

    // =====================================
    // DELETE CATEGORY
    // =====================================

    @Override
    public void deleteCategory(
            Long categoryId
    ) {

        Category category =
                getCategoryEntity(
                        categoryId
                );

        category.setActive(false);

        categoryRepository.save(
                category
        );
    }

    // =====================================
    // GET CATEGORY ENTITY
    // =====================================

    private Category getCategoryEntity(
            Long categoryId
    ) {

        return categoryRepository
                .findById(categoryId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Category not found"
                        )
                );
    }
}