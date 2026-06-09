package com.gmsmartplanner.mapper.budget;

import com.gmsmartplanner.dto.request.budget.CreateCategoryRequestDTO;
import com.gmsmartplanner.dto.request.budget.UpdateCategoryRequestDTO;
import com.gmsmartplanner.dto.response.budget.CategoryResponseDTO;
import com.gmsmartplanner.entity.budget.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    private static final String
            DEFAULT_ICON_URL =

            "https://cdn-icons-png.flaticon.com/512/3081/3081559.png";

    // =====================================
    // CREATE CATEGORY
    // =====================================

    public Category createCategory(
            CreateCategoryRequestDTO dto
    ) {

        Category category =
                new Category();

        category.setName(
                dto.getName()
        );

        category.setColorCode(
                dto.getColorCode()
        );

        category.setType(
                dto.getType()
        );

        category.setActive(true);

        return category;
    }

    // =====================================
    // UPDATE CATEGORY
    // =====================================

    public void updateCategory(

            Category category,

            UpdateCategoryRequestDTO dto

    ) {

        if (dto.getName() != null) {

            category.setName(
                    dto.getName()
            );
        }

        if (dto.getColorCode() != null) {

            category.setColorCode(
                    dto.getColorCode()
            );
        }

        if (dto.getType() != null) {

            category.setType(
                    dto.getType()
            );
        }
    }

    // =====================================
    // MAP RESPONSE
    // =====================================

    public CategoryResponseDTO
    mapToResponse(
            Category category
    ) {

        return CategoryResponseDTO
                .builder()

                .id(category.getId())

                .name(category.getName())

                .iconUrl(

                        category.getIcon() != null

                                ?

                                category.getIcon()

                                :

                                DEFAULT_ICON_URL
                )

                .colorCode(
                        category.getColorCode()
                )

                .type(category.getType())

                .active(category.isActive())

                .build();
    }
}