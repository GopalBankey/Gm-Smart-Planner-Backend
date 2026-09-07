package com.gmsmartplanner.repository.budget;

import com.gmsmartplanner.entity.User;
import com.gmsmartplanner.entity.budget.Emi;
import com.gmsmartplanner.entity.budget.EmiPaymentHistory;
import com.gmsmartplanner.enums.budget.EmiPaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;

import java.util.List;
import java.util.Optional;

public interface EmiPaymentHistoryRepository
        extends JpaRepository<EmiPaymentHistory, Long> {

    Optional<EmiPaymentHistory>
    findByEmiAndPaymentMonthAndPaymentYear(

            Emi emi,

            Integer paymentMonth,

            Integer paymentYear
    );

    List<EmiPaymentHistory>
    findAllByEmiOrderByPaymentDateDesc(

            Emi emi
    );
    List<EmiPaymentHistory>
    findAllByUserAndPaymentYearAndPaymentMonthOrderByPaymentDateDesc(

            User user,

            Integer paymentYear,

            Integer paymentMonth
    );
    List<EmiPaymentHistory>
    findAllByUserAndStatusOrderByPaymentDateDesc(
            User user,
            EmiPaymentStatus status
    );

    // =====================================
// GET TOTAL PAID EMI AMOUNT
// =====================================

    @Query("""
        SELECT COALESCE(
            SUM(e.emiAmount),
            0
        )
        FROM EmiPaymentHistory h
        JOIN h.emi e
        WHERE h.user = :user
          AND h.status = :status
        """)
    BigDecimal getTotalPaidEmiAmount(

            @Param("user")
            User user,

            @Param("status")
            EmiPaymentStatus status
    );
}