package com.gmsmartplanner.dto.response.budget;

import com.gmsmartplanner.enums.budget.CreditCardType;
import com.gmsmartplanner.enums.budget.EmiType;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Builder
public class EmiResponseDTO {

    // =====================================
    // BASIC
    // =====================================

    private Long id;

    private Long categoryId;

    private String categoryName;

    private String categoryIcon;

    private EmiType type;

    // =====================================
    // LOAN DETAILS
    // =====================================

    private BigDecimal loanAmount;

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

    private BigDecimal emiAmount;

    private Integer totalInstallments;

    private Integer paidInstallments;

    private Integer remainingInstallments;

    private Integer durationMonths;

    private LocalDate emiDueDate;

    // =====================================
    // STATUS
    // =====================================

    private boolean active;
}