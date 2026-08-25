package com.gmsmartplanner.dto.request.budget;

import com.gmsmartplanner.enums.budget.CreditCardType;
import com.gmsmartplanner.enums.budget.EmiType;
import jakarta.validation.constraints.NotBlank;
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
    // EMI DETAILS
    // =====================================

    @NotBlank
    private String emiName;

    @NotNull
    private EmiType type;

    // =====================================
    // LOAN DETAILS
    // =====================================

    @NotNull
    private BigDecimal loanAmount;

    @NotBlank
    private String bankName;

    // =====================================
    // CREDIT CARD DETAILS
    // =====================================

    private String creditCardNumber;

    private String cardHolderName;

    private CreditCardType creditCardType;

    // =====================================
    // PAYMENT DETAILS
    // =====================================

    @NotNull
    private BigDecimal emiAmount;

    @NotNull
    private Integer totalInstallments;

    @NotNull
    private LocalDate emiDueDate;
}