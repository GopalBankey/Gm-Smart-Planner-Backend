package com.gmsmartplanner.dto.request.budget;

import com.gmsmartplanner.enums.budget.TransactionType;
import com.gmsmartplanner.enums.todo.CategoryType;
import com.gmsmartplanner.enums.budget.RecurringType;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class UpdateTransactionRequestDTO {

    // =====================================
    // AMOUNT
    // =====================================

    private BigDecimal amount;

    // =====================================
    // CURRENCY
    // =====================================

    private String currency;

    // =====================================
    // TYPE
    // =====================================

    private TransactionType type;

    // =====================================
    // CATEGORY
    // =====================================

    private Long categoryId;

    // =====================================
    // PAYMENT METHOD
    // =====================================

    private Long paymentMethodId;

    // =====================================
    // NOTE
    // =====================================

    private String note;

    // =====================================
    // TRANSACTION DATE
    // =====================================

    private LocalDateTime transactionDate;

    // =====================================
    // RECURRING
    // =====================================

    private Boolean recurring;

    private RecurringType recurringType;

    private LocalDate recurringUntil;
}