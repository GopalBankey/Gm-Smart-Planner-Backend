package com.gmsmartplanner.dto.response.budget;

import com.gmsmartplanner.enums.todo.CategoryType;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PaymentMethodResponseDTO {

    private Long id;

    private String name;

    private String iconUrl;

    private CategoryType type;

    private boolean active;
}