package com.gmsmartplanner.dto.request.budget;

import com.gmsmartplanner.enums.budget.TransactionType;
import com.gmsmartplanner.enums.todo.CategoryType;
import com.gmsmartplanner.enums.budget.RecurringType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class CreateTransactionRequestDTO {

    // =====================================
    // AMOUNT
    // =====================================

    @NotNull
    @DecimalMin(value = "0.0")
    private BigDecimal amount;

    // =====================================
    // CURRENCY
    // =====================================

    private String currency = "INR";

    // =====================================
    // TYPE
    // =====================================

    @NotNull
    private TransactionType type;

    // =====================================
    // CATEGORY
    // =====================================

    @NotNull
    private Long categoryId;

    // =====================================
    // PAYMENT METHOD
    // =====================================

    @NotNull
    private Long paymentMethodId;

    // =====================================
    // NOTE
    // =====================================

    private String note;

    // =====================================
    // TRANSACTION DATE
    // =====================================

    @NotNull
    private LocalDateTime transactionDate;

    // =====================================
    // RECURRING
    // =====================================

    private boolean recurring = false;

    private RecurringType recurringType;

    private LocalDate recurringUntil;
}