package com.gmsmartplanner.dto.response.budget;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EmiCategoryResponseDTO {

    private Long id;

    private String name;

    private String iconUrl;

    private boolean active;
}