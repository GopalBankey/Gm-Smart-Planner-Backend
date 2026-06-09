package com.gmsmartplanner.dto.request.budget;

import com.gmsmartplanner.enums.FilterMode;
import com.gmsmartplanner.enums.budget.TransactionType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class TransactionFilterRequestDTO {

    private TransactionType transactionType;

    private FilterMode filterMode;

    private LocalDate selectedDate;

    private LocalDate startDate;

    private LocalDate endDate;

    private List<Long> categoryIds;
}