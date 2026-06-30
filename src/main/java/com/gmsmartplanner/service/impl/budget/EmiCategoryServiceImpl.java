package com.gmsmartplanner.service.impl.budget;

import com.gmsmartplanner.dto.request.budget.CreateEmiCategoryRequestDTO;
import com.gmsmartplanner.dto.request.budget.UpdateEmiCategoryRequestDTO;
import com.gmsmartplanner.dto.response.budget.EmiCategoryResponseDTO;
import com.gmsmartplanner.entity.emi.EmiCategory;
import com.gmsmartplanner.exception.ResourceNotFoundException;

import com.gmsmartplanner.mapper.budget.EmiCategoryMapper;
import com.gmsmartplanner.repository.budget.EmiCategoryRepository;
import com.gmsmartplanner.service.FileUploadService;
import com.gmsmartplanner.service.budget.EmiCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmiCategoryServiceImpl
        implements EmiCategoryService {

    private final EmiCategoryRepository
            emiCategoryRepository;

    private final EmiCategoryMapper
            categoryMapper;

    private final FileUploadService
            fileUploadService;

    // =====================================
    // CREATE CATEGORY
    // =====================================

    @Override
    public EmiCategoryResponseDTO
    createCategory(

            CreateEmiCategoryRequestDTO dto

    ) {

        EmiCategory category =
                categoryMapper
                        .createCategory(dto);

        if (

                dto.getIcon() != null

                        &&

                        !dto.getIcon().isEmpty()

        ) {

            String iconUrl =

                    fileUploadService
                            .uploadImage(

                                    dto.getIcon(),

                                    "emi-categories"
                            );

            category.setIcon(
                    iconUrl
            );
        }

        EmiCategory savedCategory =

                emiCategoryRepository
                        .save(
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
    public List<EmiCategoryResponseDTO>
    getCategories() {

        return emiCategoryRepository

                .findAllByActiveTrue()

                .stream()

                .map(
                        categoryMapper::mapToResponse
                )

                .toList();
    }

    // =====================================
    // GET CATEGORY
    // =====================================

    @Override
    public EmiCategoryResponseDTO
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
    public EmiCategoryResponseDTO
    updateCategory(

            Long categoryId,

            UpdateEmiCategoryRequestDTO dto

    ) {

        EmiCategory category =

                getCategoryEntity(
                        categoryId
                );

        categoryMapper
                .updateCategory(

                        category,

                        dto
                );

        if (

                dto.getIcon() != null

                        &&

                        !dto.getIcon().isEmpty()

        ) {

            String iconUrl =

                    fileUploadService
                            .uploadImage(

                                    dto.getIcon(),

                                    "emi-categories"
                            );

            category.setIcon(
                    iconUrl
            );
        }

        EmiCategory updatedCategory =

                emiCategoryRepository
                        .save(
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

        EmiCategory category =

                getCategoryEntity(
                        categoryId
                );

        category.setActive(
                false
        );

        emiCategoryRepository.save(
                category
        );
    }

    // =====================================
    // GET CATEGORY ENTITY
    // =====================================

    private EmiCategory getCategoryEntity(
            Long categoryId
    ) {

        return emiCategoryRepository

                .findById(
                        categoryId
                )

                .orElseThrow(

                        () ->

                                new ResourceNotFoundException(

                                        "EMI category not found"
                                )
                );
    }
}