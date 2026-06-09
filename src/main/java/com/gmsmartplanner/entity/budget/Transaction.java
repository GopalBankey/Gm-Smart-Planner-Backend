package com.gmsmartplanner.entity.budget;

import com.gmsmartplanner.entity.BaseEntity;
import com.gmsmartplanner.entity.User;
import com.gmsmartplanner.enums.budget.TransactionType;
import com.gmsmartplanner.enums.todo.CategoryType;
import com.gmsmartplanner.enums.budget.RecurringType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "transactions",

        indexes = {

                @Index(
                        name = "idx_transaction_user",
                        columnList = "user_id"
                ),

                @Index(
                        name = "idx_transaction_date",
                        columnList = "transaction_date"
                ),

                @Index(
                        name = "idx_transaction_type",
                        columnList = "type"
                )
        }
)
@Getter
@Setter
public class Transaction
        extends BaseEntity {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;

    // =====================================
    // USER
    // =====================================

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "user_id",
            nullable = false
    )
    private User user;

    // =====================================
    // AMOUNT
    // =====================================

    @Column(
            nullable = false,
            precision = 15,
            scale = 2
    )
    private BigDecimal amount;

    // =====================================
    // CURRENCY
    // =====================================

    @Column(nullable = false)
    private String currency = "INR";

    // =====================================
    // TYPE
    // =====================================

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType type;

    // =====================================
    // CATEGORY
    // =====================================

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "category_id",
            nullable = false
    )
    private Category category;

    // =====================================
    // PAYMENT METHOD
    // =====================================

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "payment_method_id",
            nullable = false
    )
    private PaymentMethod paymentMethod;

    // =====================================
    // NOTE
    // =====================================

    @Column(columnDefinition = "TEXT")
    private String note;

    // =====================================
    // TRANSACTION DATE
    // =====================================

    @Column(
            name = "transaction_date",
            nullable = false
    )
    private LocalDateTime transactionDate;

    // =====================================
    // RECURRING
    // =====================================

    @Column(nullable = false)
    private boolean recurring = false;

    @Enumerated(EnumType.STRING)
    private RecurringType recurringType;

    private LocalDate recurringUntil;

    private LocalDateTime lastCreatedDate;

    // =====================================
    // PARENT TRANSACTION
    // =====================================

    private Long parentTransactionId;

    @Column(nullable = false)
    private boolean parent = false;

    // =====================================
    // ACTIVE
    // =====================================

    @Column(nullable = false)
    private boolean active = true;
}