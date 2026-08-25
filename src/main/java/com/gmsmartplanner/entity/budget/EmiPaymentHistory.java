package com.gmsmartplanner.entity.budget;

import com.gmsmartplanner.entity.BaseEntity;
import com.gmsmartplanner.entity.User;
import com.gmsmartplanner.enums.budget.EmiPaymentStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(
        name = "emi_payment_history",

        indexes = {

                @Index(
                        name = "idx_emi_payment_user",
                        columnList = "user_id"
                ),

                @Index(
                        name = "idx_emi_payment_emi",
                        columnList = "emi_id"
                ),

                @Index(
                        name = "idx_emi_payment_month_year",
                        columnList = "payment_month,payment_year"
                )
        },

        uniqueConstraints = {

                @UniqueConstraint(

                        name = "uk_emi_payment",

                        columnNames = {

                                "emi_id",

                                "payment_month",

                                "payment_year"
                        }
                )
        }
)
@Getter
@Setter
public class EmiPaymentHistory
        extends BaseEntity {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;

    // =====================================
    // USER
    // =====================================

    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(
            name = "user_id",
            nullable = false
    )
    private User user;

    // =====================================
    // EMI
    // =====================================

    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(
            name = "emi_id",
            nullable = false
    )
    private Emi emi;

    // =====================================
    // PAYMENT DETAILS
    // =====================================

    @Column(
            name = "payment_date",
            nullable = false
    )
    private LocalDate paymentDate;

    @Column(
            name = "payment_month",
            nullable = false
    )
    private Integer paymentMonth;

    @Column(
            name = "payment_year",
            nullable = false
    )
    private Integer paymentYear;

    @Enumerated(
            EnumType.STRING
    )
    @Column(
            name = "payment_status",
            nullable = false
    )
    private EmiPaymentStatus status =
            EmiPaymentStatus.PAID;
}