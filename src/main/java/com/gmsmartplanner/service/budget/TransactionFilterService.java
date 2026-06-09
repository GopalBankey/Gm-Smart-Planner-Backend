package com.gmsmartplanner.service.budget;

import com.gmsmartplanner.dto.request.budget.TransactionFilterRequestDTO;
import com.gmsmartplanner.dto.response.budget.BudgetHomeResponseDTO;
import com.gmsmartplanner.dto.response.budget.TransactionAnalyticsResponseDTO;
import com.gmsmartplanner.dto.response.budget.TransactionFilterResponseDTO;

public interface TransactionFilterService {

    BudgetHomeResponseDTO getHomeData(
            String username,
            String month
    );

    TransactionFilterResponseDTO filterTransactions(
            String username,
            TransactionFilterRequestDTO dto
    );

    TransactionAnalyticsResponseDTO getAnalytics(
            String username,
            TransactionFilterRequestDTO dto
    );
}