package com.gmsmartplanner.entity.budget;

import com.gmsmartplanner.entity.BaseEntity;
import com.gmsmartplanner.entity.User;
import com.gmsmartplanner.enums.budget.CreditCardType;
import com.gmsmartplanner.enums.budget.EmiType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(
        name = "emis",

        indexes = {

                @Index(
                        name = "idx_emi_user",
                        columnList = "user_id"
                ),

                @Index(
                        name = "idx_emi_category",
                        columnList = "category_id"
                ),

                @Index(
                        name = "idx_emi_due_date",
                        columnList = "emi_due_date"
                )
        }
)
@Getter
@Setter
public class Emi
        extends BaseEntity {

    @Id
    @GeneratedValue(
            strategy =
                    GenerationType.IDENTITY
    )
    private Long id;

    // =====================================
    // USER
    // =====================================

    @ManyToOne(
            fetch =
                    FetchType.LAZY
    )
    @JoinColumn(
            name = "user_id",
            nullable = false
    )
    private User user;

    // =====================================
    // CATEGORY
    // =====================================

    @ManyToOne(
            fetch =
                    FetchType.LAZY
    )
    @JoinColumn(
            name = "category_id",
            nullable = false
    )
    private com.gmsmartplanner.entity.emi.EmiCategory category;

    // =====================================
    // EMI TYPE
    // =====================================

    @Enumerated(
            EnumType.STRING
    )
    @Column(
            name = "emi_type",
            nullable = false
    )
    private EmiType type;

    // =====================================
    // LOAN DETAILS
    // =====================================

    @Column(
            name = "loan_amount",
            nullable = false,
            precision = 12,
            scale = 2
    )
    private BigDecimal loanAmount;

    @Column(
            name = "bank_name",
            nullable = false
    )
    private String bankName;

    // =====================================
    // CREDIT CARD DETAILS
    // =====================================

    @Column(
            name = "credit_card_number"
    )
    private String creditCardNumber;

    @Column(
            name = "card_holder_name"
    )
    private String cardHolderName;

    @Enumerated(
            EnumType.STRING
    )
    @Column(
            name = "credit_card_type"
    )
    private CreditCardType
            creditCardType;

    // =====================================
    // EMI DETAILS
    // =====================================

    @Column(
            name = "emi_amount",
            nullable = false,
            precision = 12,
            scale = 2
    )
    private BigDecimal emiAmount;

    @Column(
            name = "total_installments",
            nullable = false
    )
    private Integer totalInstallments;

    @Column(
            name = "paid_installments",
            nullable = false
    )
    private Integer paidInstallments =
            0;

    @Column(
            name = "duration_months",
            nullable = false
    )
    private Integer durationMonths;

    @Column(
            name = "emi_due_date",
            nullable = false
    )
    private LocalDate emiDueDate;

    // =====================================
    // STATUS
    // =====================================

    @Column(
            name = "is_active",
            nullable = false
    )
    private boolean active =
            true;
}