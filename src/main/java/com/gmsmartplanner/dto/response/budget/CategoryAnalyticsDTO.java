package com.gmsmartplanner.dto.response.budget;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class CategoryAnalyticsDTO {

    private Long categoryId;

    private String categoryName;

    private BigDecimal amount;

    private Long transactionCount;

    private Double percentage;
}