package com.gmsmartplanner.dto.response.budget;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Builder
public class BudgetHomeResponseDTO {

    private BigDecimal currentBalance;

    private BigDecimal monthlyBalance;

    private BigDecimal income;

    private BigDecimal expense;

    private String currency;

    private String selectedMonth;

    private List<TransactionResponseDTO>
            recentTransactions;
}