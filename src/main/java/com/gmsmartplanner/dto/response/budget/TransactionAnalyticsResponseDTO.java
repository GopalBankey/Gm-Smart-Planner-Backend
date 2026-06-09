package com.gmsmartplanner.dto.response.budget;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Builder
public class TransactionAnalyticsResponseDTO {

    private BigDecimal totalAmount;

    private BigDecimal totalIncome;

    private BigDecimal totalExpense;

    private BigDecimal netBalance;

    private List<CategoryAnalyticsDTO>
            categories;
}