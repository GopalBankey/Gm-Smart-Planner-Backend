package com.gmsmartplanner.dto.response.budget;

import com.gmsmartplanner.enums.budget.TransactionType;
import com.gmsmartplanner.enums.todo.CategoryType;
import com.gmsmartplanner.enums.budget.RecurringType;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
public class TransactionResponseDTO {

    // =====================================
    // ID
    // =====================================

    private Long id;

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

    private String categoryName;

    private String categoryIcon;

    private String categoryColorCode;

    // =====================================
    // PAYMENT METHOD
    // =====================================

    private Long paymentMethodId;

    private String paymentMethodName;

    private String paymentMethodIcon;

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

    private boolean recurring;

    private RecurringType recurringType;

    private LocalDate recurringUntil;

    private LocalDateTime lastCreatedDate;

    // =====================================
    // PARENT
    // =====================================

    private Long parentTransactionId;

    private boolean parent;

    // =====================================
    // ACTIVE
    // =====================================

    private boolean active;

    // =====================================
    // CREATED / UPDATED
    // =====================================

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}