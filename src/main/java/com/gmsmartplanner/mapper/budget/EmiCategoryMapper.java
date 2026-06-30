package com.gmsmartplanner.mapper.budget;

import com.gmsmartplanner.dto.request.budget.CreateEmiCategoryRequestDTO;
import com.gmsmartplanner.dto.request.budget.UpdateEmiCategoryRequestDTO;
import com.gmsmartplanner.dto.response.budget.EmiCategoryResponseDTO;
import com.gmsmartplanner.entity.emi.EmiCategory;
import org.springframework.stereotype.Component;

@Component
public class EmiCategoryMapper {

    // =====================================
    // CREATE
    // =====================================

    public EmiCategory createCategory(

            CreateEmiCategoryRequestDTO dto

    ) {

        EmiCategory category =
                new EmiCategory();

        category.setName(
                dto.getName()
        );

        category.setActive(
                true
        );

        return category;
    }

    // =====================================
    // UPDATE
    // =====================================

    public void updateCategory(

            EmiCategory category,

            UpdateEmiCategoryRequestDTO dto

    ) {

        if (

                dto.getName() != null

        ) {

            category.setName(
                    dto.getName()
            );
        }
    }

    // =====================================
    // RESPONSE
    // =====================================

    public EmiCategoryResponseDTO mapToResponse(

            EmiCategory category

    ) {

        return EmiCategoryResponseDTO
                .builder()

                .id(
                        category.getId()
                )

                .name(
                        category.getName()
                )

                .iconUrl(
                        category.getIcon()
                )

                .active(
                        category.isActive()
                )

                .build();
    }
}