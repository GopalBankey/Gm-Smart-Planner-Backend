package com.gmsmartplanner.dto.response.budget;

import com.gmsmartplanner.enums.todo.CategoryType;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CategoryResponseDTO {

    private Long id;

    private String name;

    private String iconUrl;

    private String colorCode;

    private CategoryType type;

    private boolean active;
}