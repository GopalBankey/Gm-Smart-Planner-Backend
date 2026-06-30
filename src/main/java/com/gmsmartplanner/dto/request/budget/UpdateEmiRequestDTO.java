package com.gmsmartplanner.dto.request.budget;

import com.gmsmartplanner.enums.budget.CreditCardType;
import com.gmsmartplanner.enums.budget.EmiType;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class UpdateEmiRequestDTO {

    // =====================================
    // CATEGORY
    // =====================================

    private Long categoryId;

    // =====================================
    // EMI TYPE
    // =====================================

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

    private Integer durationMonths;

    private LocalDate emiDueDate;

    // =====================================
    // STATUS
    // =====================================

    private Boolean active;
}