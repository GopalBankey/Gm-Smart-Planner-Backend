package com.gmsmartplanner.dto.request.budget;

import com.gmsmartplanner.enums.budget.CreditCardType;
import com.gmsmartplanner.enums.budget.EmiType;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class CreateEmiRequestDTO {

    // =====================================
    // CATEGORY
    // =====================================

    @NotNull
    private Long categoryId;

    // =====================================
    // EMI TYPE
    // =====================================

    @NotNull
    private EmiType type;

    // =====================================
    // LOAN DETAILS
    // =====================================

    @NotNull
    private BigDecimal loanAmount;

    @NotNull
    private String bankName;

    // =====================================
    // CREDIT CARD DETAILS
    // =====================================

    private String creditCardNumber;

    private String cardHolderName;

    private CreditCardType creditCardType;

    // =====================================
    // EMI DETAILS
    // =====================================

    @NotNull
    private BigDecimal emiAmount;

    @NotNull
    private Integer totalInstallments;

    @NotNull
    private Integer durationMonths;

    @NotNull
    private LocalDate emiDueDate;
}