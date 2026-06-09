package com.gmsmartplanner.repository.budget;

import com.gmsmartplanner.entity.User;
import com.gmsmartplanner.entity.budget.Transaction;
import com.gmsmartplanner.enums.budget.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface TransactionRepository
        extends JpaRepository<Transaction, Long> {

    // =====================================
    // GET ALL ACTIVE TRANSACTIONS
    // =====================================

    List<Transaction>
    findAllByUserAndActiveTrueOrderByTransactionDateDesc(

            User user
    );

    // =====================================
    // GET BY TYPE
    // =====================================

    List<Transaction>
    findAllByUserAndTypeAndActiveTrueOrderByTransactionDateDesc(

            User user,

            TransactionType type
    );

    // =====================================
    // GET BETWEEN DATES
    // =====================================

    List<Transaction>
    findAllByUserAndTransactionDateBetweenAndActiveTrueOrderByTransactionDateDesc(

            User user,

            LocalDateTime startDate,

            LocalDateTime endDate
    );

    // =====================================
    // GET BY TYPE AND DATE
    // =====================================

    List<Transaction>
    findAllByUserAndTypeAndTransactionDateBetweenAndActiveTrueOrderByTransactionDateDesc(

            User user,

            TransactionType type,

            LocalDateTime startDate,

            LocalDateTime endDate
    );

    // =====================================
    // GET HOME TRANSACTIONS
    // =====================================

    List<Transaction>
    findAllByUserAndTransactionDateBetweenOrderByTransactionDateDesc(

            User user,

            LocalDateTime startDate,

            LocalDateTime endDate
    );

    // =====================================
    // GET ALL
    // =====================================

    List<Transaction>
    findAllByUserOrderByTransactionDateDesc(

            User user
    );

    // =====================================
    // TOTAL AMOUNT BY TYPE
    // =====================================

    @Query("""

            SELECT COALESCE(SUM(t.amount), 0)

            FROM Transaction t

            WHERE t.user = :user
            AND t.type = :type
            AND t.active = true

            """)
    BigDecimal getTotalAmountByType(

            @Param("user")
            User user,

            @Param("type")
            TransactionType type
    );

    // =====================================
    // GET RECURRING TRANSACTIONS
    // =====================================

    List<Transaction>
    findAllByRecurringTrueAndParentTrueAndActiveTrue();
}