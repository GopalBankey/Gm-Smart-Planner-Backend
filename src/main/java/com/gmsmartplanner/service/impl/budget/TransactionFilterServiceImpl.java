package com.gmsmartplanner.service.impl.budget;

import com.gmsmartplanner.dto.request.budget.TransactionFilterRequestDTO;
import com.gmsmartplanner.dto.response.budget.*;
import com.gmsmartplanner.entity.User;
import com.gmsmartplanner.entity.budget.Category;
import com.gmsmartplanner.entity.budget.Emi;
import com.gmsmartplanner.entity.budget.EmiPaymentHistory;
import com.gmsmartplanner.entity.budget.Transaction;
import com.gmsmartplanner.enums.budget.EmiPaymentStatus;
import com.gmsmartplanner.enums.budget.TransactionType;
import com.gmsmartplanner.mapper.budget.TransactionMapper;
import com.gmsmartplanner.repository.budget.EmiPaymentHistoryRepository;
import com.gmsmartplanner.repository.budget.TransactionRepository;
import com.gmsmartplanner.service.UserHelperService;
import com.gmsmartplanner.service.budget.TransactionFilterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionFilterServiceImpl
        implements TransactionFilterService {

    private final TransactionRepository
            transactionRepository;

    private final TransactionMapper
            transactionMapper;

    private final UserHelperService
            userHelperService;

    private final EmiPaymentHistoryRepository emiPaymentHistoryRepository;


    // =====================================
    // HOME DATA
    // =====================================

    @Override
    public BudgetHomeResponseDTO getHomeData(

            String username,

            String month

    ) {

        // =====================================
        // GET CURRENT USER
        // =====================================

        User user =

                userHelperService
                        .getCurrentUser(
                                username
                        );

        // =====================================
        // PARSE SELECTED MONTH
        // =====================================

        YearMonth yearMonth =

                YearMonth.parse(
                        month
                );

        LocalDateTime startDate =

                yearMonth
                        .atDay(1)
                        .atStartOfDay();

        LocalDateTime endDate =

                yearMonth
                        .atEndOfMonth()
                        .atTime(
                                23,
                                59,
                                59
                        );

        // =====================================
        // GET NORMAL TRANSACTIONS
        // =====================================

        List<Transaction> transactions =

                transactionRepository

                        .findAllByUserAndTransactionDateBetweenAndActiveTrueOrderByTransactionDateDesc(

                                user,

                                startDate,

                                endDate
                        );

        // =====================================
        // NORMAL TRANSACTION INCOME
        // =====================================

        BigDecimal income =

                transactions
                        .stream()

                        .filter(t ->
                                t.getType()
                                        == TransactionType.INCOME
                        )

                        .map(
                                Transaction::getAmount
                        )

                        .reduce(
                                BigDecimal.ZERO,
                                BigDecimal::add
                        );

        // =====================================
        // NORMAL TRANSACTION EXPENSE
        // =====================================

        BigDecimal normalExpense =

                transactions
                        .stream()

                        .filter(t ->
                                t.getType()
                                        == TransactionType.EXPENSE
                        )

                        .map(
                                Transaction::getAmount
                        )

                        .reduce(
                                BigDecimal.ZERO,
                                BigDecimal::add
                        );

        // =====================================
        // GET EMI PAYMENT HISTORY
        // =====================================

        List<EmiPaymentHistory> emiPayments =

                emiPaymentHistoryRepository

                        .findAllByUserAndPaymentYearAndPaymentMonthOrderByPaymentDateDesc(

                                user,

                                yearMonth.getYear(),

                                yearMonth.getMonthValue()
                        );

        // =====================================
        // EMI EXPENSE
        // =====================================

        BigDecimal emiExpense =

                emiPayments
                        .stream()

                        .filter(history ->
                                history.getStatus()
                                        == EmiPaymentStatus.PAID
                        )

                        .map(history ->
                                history.getEmi()
                                        .getEmiAmount()
                        )

                        .filter(Objects::nonNull)

                        .reduce(
                                BigDecimal.ZERO,
                                BigDecimal::add
                        );

        // =====================================
        // TOTAL MONTHLY EXPENSE
        // =====================================

        BigDecimal expense =

                normalExpense
                        .add(
                                emiExpense
                        );

        // =====================================
        // MONTHLY BALANCE
        // =====================================

        BigDecimal monthlyBalance =

                income.subtract(
                        expense
                );

        // =====================================
        // TOTAL INCOME
        // =====================================

        BigDecimal totalIncome =

                transactionRepository
                        .getTotalAmountByType(

                                user,

                                TransactionType.INCOME
                        );

        // =====================================
        // TOTAL NORMAL EXPENSE
        // =====================================

        BigDecimal totalExpense =

                transactionRepository
                        .getTotalAmountByType(

                                user,

                                TransactionType.EXPENSE
                        );

        // =====================================
        // TOTAL PAID EMI EXPENSE
        // =====================================

        BigDecimal totalEmiExpense =

                emiPaymentHistoryRepository

                        .getTotalPaidEmiAmount(

                                user,

                                EmiPaymentStatus.PAID
                        );

        // =====================================
        // HANDLE NULL TOTALS
        // =====================================

        if (totalIncome == null) {

            totalIncome =
                    BigDecimal.ZERO;
        }

        if (totalExpense == null) {

            totalExpense =
                    BigDecimal.ZERO;
        }

        if (totalEmiExpense == null) {

            totalEmiExpense =
                    BigDecimal.ZERO;
        }

        // =====================================
        // TOTAL EXPENSE INCLUDING EMI
        // =====================================

        BigDecimal totalExpenseIncludingEmi =

                totalExpense
                        .add(
                                totalEmiExpense
                        );

        // =====================================
        // CURRENT BALANCE
        // =====================================

        BigDecimal currentBalance =

                totalIncome

                        .subtract(
                                totalExpenseIncludingEmi
                        );

        // =====================================
        // NORMAL TRANSACTIONS
        // =====================================

        List<TransactionResponseDTO>
                recentTransactions =

                transactions
                        .stream()

                        .map(
                                transactionMapper
                                        ::mapToResponse
                        )

                        .toList();

        // =====================================
        // EMI TRANSACTIONS
        // =====================================

        List<TransactionResponseDTO>
                emiTransactions =

                emiPayments
                        .stream()

                        .filter(history ->
                                history.getStatus()
                                        == EmiPaymentStatus.PAID
                        )

                        .map(
                                this::mapEmiPaymentToTransaction
                        )

                        .toList();

        // =====================================
        // COMBINE TRANSACTIONS
        // =====================================

        List<TransactionResponseDTO>
                combinedTransactions =

                new ArrayList<>(
                        recentTransactions
                );

        combinedTransactions.addAll(
                emiTransactions
        );

        // =====================================
        // SORT BY DATE DESCENDING
        // =====================================

        combinedTransactions.sort(

                Comparator
                        .comparing(
                                TransactionResponseDTO
                                        ::getTransactionDate,

                                Comparator
                                        .nullsLast(
                                                Comparator
                                                        .reverseOrder()
                                        )
                        )
        );

        // =====================================
        // LIMIT RECENT TRANSACTIONS
        // =====================================

        List<TransactionResponseDTO>
                finalRecentTransactions =

                combinedTransactions
                        .stream()

                        .limit(10)

                        .toList();

        // =====================================
        // RETURN HOME RESPONSE
        // =====================================

        return BudgetHomeResponseDTO

                .builder()

                .currentBalance(
                        currentBalance
                )

                .monthlyBalance(
                        monthlyBalance
                )

                .income(
                        income
                )

                .expense(
                        expense
                )

                .currency(
                        "INR"
                )

                .selectedMonth(
                        month
                )

                .recentTransactions(
                        finalRecentTransactions
                )

                .build();
    }

//    @Override
//    public BudgetHomeResponseDTO getHomeData(
//
//            String username,
//
//            String month
//
//    ) {
//
//        // =====================================
//        // GET CURRENT USER
//        // =====================================
//
//        User user =
//
//                userHelperService
//                        .getCurrentUser(
//                                username
//                        );
//
//        // =====================================
//        // PARSE SELECTED MONTH
//        // =====================================
//
//        YearMonth yearMonth =
//
//                YearMonth.parse(
//                        month
//                );
//
//        LocalDateTime startDate =
//
//                yearMonth
//                        .atDay(1)
//                        .atStartOfDay();
//
//        LocalDateTime endDate =
//
//                yearMonth
//                        .atEndOfMonth()
//                        .atTime(
//                                23,
//                                59,
//                                59
//                        );
//
//        // =====================================
//        // GET NORMAL TRANSACTIONS
//        // =====================================
//
//        List<Transaction> transactions =
//
//                transactionRepository
//
//                        .findAllByUserAndTransactionDateBetweenAndActiveTrueOrderByTransactionDateDesc(
//
//                                user,
//
//                                startDate,
//
//                                endDate
//                        );
//
//        // =====================================
//        // NORMAL TRANSACTION INCOME
//        // =====================================
//
//        BigDecimal income =
//
//                transactions
//                        .stream()
//                        .filter(t ->
//                                t.getType()
//                                        == TransactionType.INCOME
//                        )
//                        .map(
//                                Transaction::getAmount
//                        )
//                        .reduce(
//                                BigDecimal.ZERO,
//                                BigDecimal::add
//                        );
//
//        // =====================================
//        // NORMAL TRANSACTION EXPENSE
//        // =====================================
//
//        BigDecimal normalExpense =
//
//                transactions
//                        .stream()
//                        .filter(t ->
//                                t.getType()
//                                        == TransactionType.EXPENSE
//                        )
//                        .map(
//                                Transaction::getAmount
//                        )
//                        .reduce(
//                                BigDecimal.ZERO,
//                                BigDecimal::add
//                        );
//
//        // =====================================
//        // GET EMI PAYMENT HISTORY
//        // =====================================
//
//        List<EmiPaymentHistory> emiPayments =
//
//                emiPaymentHistoryRepository
//
//                        .findAllByUserAndPaymentYearAndPaymentMonthOrderByPaymentDateDesc(
//
//                                user,
//
//                                yearMonth.getYear(),
//
//                                yearMonth.getMonthValue()
//                        );
//
//        // =====================================
//        // EMI EXPENSE
//        // =====================================
//
//        BigDecimal emiExpense =
//
//                emiPayments
//                        .stream()
//                        .filter(history ->
//                                history.getStatus()
//                                        == EmiPaymentStatus.PAID
//                        )
//                        .map(history ->
//                                history.getEmi()
//                                        .getEmiAmount()
//                        )
//                        .reduce(
//                                BigDecimal.ZERO,
//                                BigDecimal::add
//                        );
//
//        // =====================================
//        // TOTAL MONTHLY EXPENSE
//        // =====================================
//
//        BigDecimal expense =
//
//                normalExpense
//                        .add(
//                                emiExpense
//                        );
//
//        // =====================================
//        // MONTHLY BALANCE
//        // =====================================
//
//        BigDecimal monthlyBalance =
//
//                income.subtract(
//                        expense
//                );
//
//        // =====================================
//        // TOTAL INCOME
//        // =====================================
//
//        BigDecimal totalIncome =
//
//                transactionRepository
//                        .getTotalAmountByType(
//
//                                user,
//
//                                TransactionType.INCOME
//                        );
//
//        // =====================================
//        // TOTAL NORMAL EXPENSE
//        // =====================================
//
//        BigDecimal totalExpense =
//
//                transactionRepository
//                        .getTotalAmountByType(
//
//                                user,
//
//                                TransactionType.EXPENSE
//                        );
//
//        // =====================================
//        // CURRENT BALANCE
//        // =====================================
//
//        BigDecimal currentBalance =
//
//                totalIncome
//
//                        .subtract(
//                                totalExpense
//                        );
//
//        // =====================================
//        // NORMAL TRANSACTIONS
//        // =====================================
//
//        List<TransactionResponseDTO> recentTransactions =
//
//                transactions
//                        .stream()
//                        .map(
//                                transactionMapper::mapToResponse
//                        )
//                        .toList();
//
//        // =====================================
//        // EMI TRANSACTIONS
//        // =====================================
//
//        List<TransactionResponseDTO> emiTransactions =
//
//                emiPayments
//                        .stream()
//                        .filter(history ->
//                                history.getStatus()
//                                        == EmiPaymentStatus.PAID
//                        )
//                        .map(
//                                this::mapEmiPaymentToTransaction
//                        )
//                        .toList();
//
//        // =====================================
//        // COMBINE TRANSACTIONS
//        // =====================================
//
//        List<TransactionResponseDTO> combinedTransactions =
//
//                new java.util.ArrayList<>(
//                        recentTransactions
//                );
//
//        combinedTransactions.addAll(
//                emiTransactions
//        );
//
//        // =====================================
//        // SORT BY DATE DESCENDING
//        // =====================================
//
//        combinedTransactions.sort(
//
//                java.util.Comparator
//                        .comparing(
//                                TransactionResponseDTO::getTransactionDate,
//                                java.util.Comparator.reverseOrder()
//                        )
//        );
//
//        // =====================================
//        // LIMIT RECENT TRANSACTIONS
//        // =====================================
//
//        List<TransactionResponseDTO> finalRecentTransactions =
//
//                combinedTransactions
//                        .stream()
//                        .limit(10)
//                        .toList();
//
//        // =====================================
//        // RETURN HOME RESPONSE
//        // =====================================
//
//        return BudgetHomeResponseDTO
//
//                .builder()
//
//                .currentBalance(
//                        currentBalance
//                )
//
//                .monthlyBalance(
//                        monthlyBalance
//                )
//
//                .income(
//                        income
//                )
//
//                .expense(
//                        expense
//                )
//
//                .currency(
//                        "INR"
//                )
//
//                .selectedMonth(
//                        month
//                )
//
//                .recentTransactions(
//                        finalRecentTransactions
//                )
//
//                .build();
//    }




    @Override
    public TransactionFilterResponseDTO
    filterTransactions(

            String username,

            TransactionFilterRequestDTO dto

    ) {

        User user =
                userHelperService
                        .getCurrentUser(username);

        // =====================================
        // NORMAL TRANSACTIONS
        // =====================================

        List<Transaction> transactions =
                transactionRepository
                        .findAllByUserAndActiveTrueOrderByTransactionDateDesc(
                                user
                        );

        // =====================================
        // PAID EMI PAYMENTS
        // =====================================

        List<EmiPaymentHistory> emiPayments =
                emiPaymentHistoryRepository
                        .findAllByUserAndStatusOrderByPaymentDateDesc(
                                user,
                                EmiPaymentStatus.PAID
                        );

        // =====================================
        // TRANSACTION TYPE
        // =====================================

        if (dto.getTransactionType() != null
                && dto.getTransactionType()
                != TransactionType.ALL) {

            transactions =
                    transactions.stream()
                            .filter(t ->
                                    t.getType()
                                            == dto.getTransactionType()
                            )
                            .toList();

            /*
             * EMI is always an EXPENSE.
             */
            if (dto.getTransactionType()
                    != TransactionType.EXPENSE) {

                emiPayments =
                        new ArrayList<>();
            }
        }

        // =====================================
        // CATEGORY FILTER
        // =====================================

        if (dto.getCategoryIds() != null
                && !dto.getCategoryIds().isEmpty()) {

            transactions =
                    transactions.stream()
                            .filter(t ->

                                    t.getCategory() != null

                                            &&

                                            dto.getCategoryIds()
                                                    .contains(
                                                            t.getCategory()
                                                                    .getId()
                                                    )
                            )
                            .toList();

            /*
             * EMI uses EmiCategory instead of
             * normal Transaction Category.
             */
            emiPayments =
                    emiPayments.stream()
                            .filter(payment ->

                                    payment.getEmi() != null

                                            &&

                                            payment.getEmi()
                                                    .getCategory() != null

                                            &&

                                            dto.getCategoryIds()
                                                    .contains(
                                                            payment.getEmi()
                                                                    .getCategory()
                                                                    .getId()
                                                    )
                            )
                            .toList();
        }

        // =====================================
        // NORMAL TRANSACTION TOTALS
        // =====================================

        BigDecimal totalIncome =
                transactions.stream()
                        .filter(t ->
                                t.getType()
                                        == TransactionType.INCOME
                        )
                        .map(Transaction::getAmount)
                        .reduce(
                                BigDecimal.ZERO,
                                BigDecimal::add
                        );

        BigDecimal normalExpense =
                transactions.stream()
                        .filter(t ->
                                t.getType()
                                        == TransactionType.EXPENSE
                        )
                        .map(Transaction::getAmount)
                        .reduce(
                                BigDecimal.ZERO,
                                BigDecimal::add
                        );

        // =====================================
        // EMI TOTAL
        // =====================================

        BigDecimal emiExpense =
                emiPayments.stream()
                        .map(payment ->
                                payment.getEmi()
                                        .getEmiAmount()
                        )
                        .filter(Objects::nonNull)
                        .reduce(
                                BigDecimal.ZERO,
                                BigDecimal::add
                        );

        // =====================================
        // TOTAL EXPENSE
        // =====================================

        BigDecimal totalExpense =
                normalExpense
                        .add(
                                emiExpense
                        );

        // =====================================
        // NET BALANCE
        // =====================================

        BigDecimal netBalance =
                totalIncome.subtract(
                        totalExpense
                );

        // =====================================
        // NORMAL TRANSACTIONS RESPONSE
        // =====================================

        List<TransactionResponseDTO>
                responseTransactions =

                transactions.stream()
                        .map(
                                transactionMapper
                                        ::mapToResponse
                        )
                        .collect(
                                Collectors.toCollection(
                                        ArrayList::new
                                )
                        );

        // =====================================
        // EMI TRANSACTIONS RESPONSE
        // =====================================

        List<TransactionResponseDTO>
                emiTransactions =

                emiPayments.stream()

                        .map(
                                this::mapEmiPaymentToTransaction
                        )

                        .toList();

        // =====================================
        // MERGE TRANSACTIONS + EMI
        // =====================================

        responseTransactions.addAll(
                emiTransactions
        );

        // =====================================
        // SORT BY DATE DESC
        // =====================================

        responseTransactions =
                responseTransactions.stream()

                        .sorted(
                                Comparator
                                        .comparing(
                                                TransactionResponseDTO
                                                        ::getTransactionDate,
                                                Comparator
                                                        .nullsLast(
                                                                Comparator
                                                                        .reverseOrder()
                                                        )
                                        )
                        )

                        .toList();

        // =====================================
        // RESPONSE
        // =====================================

        return TransactionFilterResponseDTO
                .builder()

                .totalIncome(
                        totalIncome
                )

                .totalExpense(
                        totalExpense
                )

                .netBalance(
                        netBalance
                )

                .transactions(
                        responseTransactions
                )

                .build();
    }


//    @Override
//    public TransactionFilterResponseDTO
//    filterTransactions(
//
//            String username,
//
//            TransactionFilterRequestDTO dto
//
//    ) {
//
//        User user =
//                userHelperService
//                        .getCurrentUser(username);
//
//        List<Transaction> transactions =
//                transactionRepository
//                        .findAllByUserAndActiveTrueOrderByTransactionDateDesc(
//                                user
//                        );
//
//        // TRANSACTION TYPE
//
//        if (dto.getTransactionType() != null
//                && dto.getTransactionType()
//                != TransactionType.ALL) {
//
//            transactions =
//                    transactions.stream()
//                            .filter(t ->
//                                    t.getType()
//                                            == dto.getTransactionType()
//                            )
//                            .toList();
//        }
//
//        // CATEGORY FILTER
//
//        if (dto.getCategoryIds() != null
//                && !dto.getCategoryIds().isEmpty()) {
//
//            transactions =
//                    transactions.stream()
//                            .filter(t ->
//
//                                    t.getCategory() != null
//
//                                            &&
//
//                                            dto.getCategoryIds()
//                                                    .contains(
//                                                            t.getCategory()
//                                                                    .getId()
//                                                    )
//                            )
//                            .toList();
//        }
//
//        BigDecimal totalIncome =
//                transactions.stream()
//                        .filter(t ->
//                                t.getType()
//                                        == TransactionType.INCOME
//                        )
//                        .map(Transaction::getAmount)
//                        .reduce(
//                                BigDecimal.ZERO,
//                                BigDecimal::add
//                        );
//
//        BigDecimal totalExpense =
//                transactions.stream()
//                        .filter(t ->
//                                t.getType()
//                                        == TransactionType.EXPENSE
//                        )
//                        .map(Transaction::getAmount)
//                        .reduce(
//                                BigDecimal.ZERO,
//                                BigDecimal::add
//                        );
//
//        BigDecimal netBalance =
//                totalIncome.subtract(
//                        totalExpense
//                );
//
//        return TransactionFilterResponseDTO
//                .builder()
//                .totalIncome(totalIncome)
//                .totalExpense(totalExpense)
//                .netBalance(netBalance)
//                .transactions(
//                        transactions.stream()
//                                .map(transactionMapper::mapToResponse)
//                                .toList()
//                )
//                .build();
//    }

    // =====================================
    // ANALYTICS
    // =====================================

    @Override
    public TransactionAnalyticsResponseDTO
    getAnalytics(

            String username,

            TransactionFilterRequestDTO dto

    ) {

        User user =
                userHelperService
                        .getCurrentUser(
                                username
                        );

        // =====================================
        // GET NORMAL TRANSACTIONS
        // =====================================

        List<Transaction> transactions =
                transactionRepository
                        .findAllByUserAndActiveTrueOrderByTransactionDateDesc(
                                user
                        );

        // =====================================
        // GET ALL PAID EMI PAYMENTS
        // =====================================

        List<EmiPaymentHistory> emiPayments =
                emiPaymentHistoryRepository
                        .findAllByUserAndStatusOrderByPaymentDateDesc(
                                user,
                                EmiPaymentStatus.PAID
                        );

        LocalDate today =
                LocalDate.now();

        // =====================================
        // FILTER MODE
        // =====================================

        if (dto.getFilterMode() != null) {

            transactions =
                    transactions.stream()

                            .filter(t -> {

                                LocalDate date =
                                        t.getTransactionDate()
                                                .toLocalDate();

                                return switch (
                                        dto.getFilterMode()
                                        ) {

                                    case DAILY ->
                                            date.equals(
                                                    today
                                            );

                                    case MONTHLY ->
                                            date.getYear()
                                                    ==
                                                    today.getYear()

                                                    &&

                                                    date.getMonth()
                                                            ==
                                                            today.getMonth();

                                    case YEARLY ->
                                            date.getYear()
                                                    ==
                                                    today.getYear();

                                    default ->
                                            true;
                                };
                            })

                            .toList();

            // =====================================
            // FILTER EMI
            // =====================================

            emiPayments =
                    emiPayments.stream()

                            .filter(payment -> {

                                LocalDate date =
                                        payment.getPaymentDate();

                                return switch (
                                        dto.getFilterMode()
                                        ) {

                                    case DAILY ->
                                            date.equals(
                                                    today
                                            );

                                    case MONTHLY ->
                                            date.getYear()
                                                    ==
                                                    today.getYear()

                                                    &&

                                                    date.getMonth()
                                                            ==
                                                            today.getMonth();

                                    case YEARLY ->
                                            date.getYear()
                                                    ==
                                                    today.getYear();

                                    default ->
                                            true;
                                };
                            })

                            .toList();
        }

        // =====================================
        // TYPE FILTER
        // =====================================

        if (dto.getTransactionType() != null
                && dto.getTransactionType()
                != TransactionType.ALL) {

            transactions =
                    transactions.stream()

                            .filter(t ->

                                    t.getType()
                                            ==
                                            dto.getTransactionType()
                            )

                            .toList();

            /*
             * EMI is always EXPENSE.
             *
             * Therefore EMI should only be included
             * when EXPENSE or ALL is selected.
             */

            if (dto.getTransactionType()
                    != TransactionType.EXPENSE) {

                emiPayments =
                        new ArrayList<>();
            }
        }

        // =====================================
        // CATEGORY FILTER
        // =====================================

        if (dto.getCategoryIds() != null
                && !dto.getCategoryIds().isEmpty()) {

            transactions =
                    transactions.stream()

                            .filter(t ->

                                    t.getCategory() != null

                                            &&

                                            dto.getCategoryIds()
                                                    .contains(
                                                            t.getCategory()
                                                                    .getId()
                                                    )
                            )

                            .toList();

            /*
             * EMI category filtering.
             *
             * EmiCategory and normal Category are
             * separate entities.
             */

            emiPayments =
                    emiPayments.stream()

                            .filter(payment ->

                                    payment.getEmi() != null

                                            &&

                                            payment.getEmi()
                                                    .getCategory() != null

                                            &&

                                            dto.getCategoryIds()
                                                    .contains(
                                                            payment.getEmi()
                                                                    .getCategory()
                                                                    .getId()
                                                    )
                            )

                            .toList();
        }

        // =====================================
        // NORMAL TRANSACTION INCOME
        // =====================================

        BigDecimal totalIncome =

                transactions.stream()

                        .filter(t ->

                                t.getType()
                                        ==
                                        TransactionType.INCOME
                        )

                        .map(
                                Transaction::getAmount
                        )

                        .filter(
                                Objects::nonNull
                        )

                        .reduce(
                                BigDecimal.ZERO,
                                BigDecimal::add
                        );

        // =====================================
        // NORMAL TRANSACTION EXPENSE
        // =====================================

        BigDecimal normalExpense =

                transactions.stream()

                        .filter(t ->

                                t.getType()
                                        ==
                                        TransactionType.EXPENSE
                        )

                        .map(
                                Transaction::getAmount
                        )

                        .filter(
                                Objects::nonNull
                        )

                        .reduce(
                                BigDecimal.ZERO,
                                BigDecimal::add
                        );

        // =====================================
        // PAID EMI EXPENSE
        // =====================================

        BigDecimal emiExpense =

                emiPayments.stream()

                        .map(payment -> {

                            if (payment.getEmi() == null) {

                                return BigDecimal.ZERO;
                            }

                            return payment.getEmi()
                                    .getEmiAmount();
                        })

                        .filter(
                                Objects::nonNull
                        )

                        .reduce(
                                BigDecimal.ZERO,
                                BigDecimal::add
                        );

        // =====================================
        // TOTAL EXPENSE
        // =====================================

        BigDecimal totalExpense =

                normalExpense
                        .add(
                                emiExpense
                        );

        // =====================================
        // TOTAL AMOUNT
        // =====================================

        BigDecimal totalAmount =

                totalIncome
                        .add(
                                totalExpense
                        );

        // =====================================
        // NET BALANCE
        // =====================================

        BigDecimal netBalance =

                totalIncome
                        .subtract(
                                totalExpense
                        );

        // =====================================
        // CATEGORY WISE NORMAL TRANSACTIONS
        // =====================================

        Map<Category, List<Transaction>>
                grouped =

                transactions.stream()

                        .filter(t ->
                                t.getCategory() != null
                        )

                        .collect(

                                Collectors.groupingBy(

                                        Transaction
                                                ::getCategory
                                )
                        );

        List<CategoryAnalyticsDTO>
                categories =
                new ArrayList<>();

        // =====================================
        // NORMAL CATEGORY ANALYTICS
        // =====================================

        for (Map.Entry<Category,
                List<Transaction>>
                entry
                : grouped.entrySet()) {

            BigDecimal amount =

                    entry.getValue()

                            .stream()

                            .map(
                                    Transaction
                                            ::getAmount
                            )

                            .filter(
                                    Objects::nonNull
                            )

                            .reduce(
                                    BigDecimal.ZERO,
                                    BigDecimal::add
                            );

            double percentage =

                    totalAmount.compareTo(
                            BigDecimal.ZERO
                    ) == 0

                            ?

                            0

                            :

                            amount.multiply(
                                            BigDecimal.valueOf(
                                                    100
                                            )
                                    )

                                    .divide(
                                            totalAmount,

                                            2,

                                            RoundingMode
                                                    .HALF_UP
                                    )

                                    .doubleValue();

            categories.add(

                    CategoryAnalyticsDTO
                            .builder()

                            .categoryId(
                                    entry.getKey()
                                            .getId()
                            )

                            .categoryName(
                                    entry.getKey()
                                            .getName()
                            )

                            .amount(
                                    amount
                            )

                            .transactionCount(
                                    (long)
                                            entry.getValue()
                                                    .size()
                            )

                            .percentage(
                                    percentage
                            )

                            .build()
            );
        }

        // =====================================
        // EMI CATEGORY
        // =====================================

        if (emiExpense.compareTo(
                BigDecimal.ZERO
        ) > 0) {

            double emiPercentage =

                    totalAmount.compareTo(
                            BigDecimal.ZERO
                    ) == 0

                            ?

                            0

                            :

                            emiExpense.multiply(
                                            BigDecimal.valueOf(
                                                    100
                                            )
                                    )

                                    .divide(
                                            totalAmount,

                                            2,

                                            RoundingMode
                                                    .HALF_UP
                                    )

                                    .doubleValue();

            categories.add(

                    CategoryAnalyticsDTO
                            .builder()

                            /*
                             * EMI is a virtual category.
                             * No Category table record is created.
                             */
                            .categoryId(
                                    null
                            )

                            .categoryName(
                                    "EMI"
                            )

                            .amount(
                                    emiExpense
                            )

                            .transactionCount(
                                    (long)
                                            emiPayments.size()
                            )

                            .percentage(
                                    emiPercentage
                            )

                            .build()
            );
        }

        // =====================================
        // RESPONSE
        // =====================================

        return TransactionAnalyticsResponseDTO

                .builder()

                .totalAmount(
                        totalAmount
                )

                .totalIncome(
                        totalIncome
                )

                .totalExpense(
                        totalExpense
                )

                .netBalance(
                        netBalance
                )

                .categories(
                        categories
                )

                .build();
    }

//    @Override
//    public TransactionAnalyticsResponseDTO
//    getAnalytics(
//
//            String username,
//
//            TransactionFilterRequestDTO dto
//
//    ) {
//
//        User user =
//                userHelperService
//                        .getCurrentUser(
//                                username
//                        );
//
//        List<Transaction> transactions =
//                transactionRepository
//                        .findAllByUserAndActiveTrueOrderByTransactionDateDesc(
//                                user
//                        );
//
//        // =====================================
//        // PAID EMI
//        // =====================================
//
//        List<EmiPaymentHistory> emiPayments =
//                emiPaymentHistoryRepository
//                        .findAllByUserAndStatusOrderByPaymentDateDesc(
//                                user,
//                                EmiPaymentStatus.PAID
//                        );
//
//        LocalDate today =
//                LocalDate.now();
//
//        // =====================================
//        // FILTER MODE
//        // =====================================
//
//        if (dto.getFilterMode() != null) {
//
//            transactions =
//                    transactions.stream()
//
//                            .filter(t -> {
//
//                                LocalDate date =
//                                        t.getTransactionDate()
//                                                .toLocalDate();
//
//                                return switch (
//                                        dto.getFilterMode()
//                                        ) {
//
//                                    case DAILY ->
//                                            date.equals(
//                                                    today
//                                            );
//
//                                    case MONTHLY ->
//                                            date.getYear()
//                                                    ==
//                                                    today.getYear()
//
//                                                    &&
//
//                                                    date.getMonth()
//                                                            ==
//                                                            today.getMonth();
//
//                                    case YEARLY ->
//                                            date.getYear()
//                                                    ==
//                                                    today.getYear();
//
//                                    default ->
//                                            true;
//                                };
//                            })
//
//                            .toList();
//
//            emiPayments =
//                    emiPayments.stream()
//
//                            .filter(payment -> {
//
//                                LocalDate date =
//                                        payment.getPaymentDate();
//
//                                return switch (
//                                        dto.getFilterMode()
//                                        ) {
//
//                                    case DAILY ->
//                                            date.equals(
//                                                    today
//                                            );
//
//                                    case MONTHLY ->
//                                            date.getYear()
//                                                    ==
//                                                    today.getYear()
//
//                                                    &&
//
//                                                    date.getMonth()
//                                                            ==
//                                                            today.getMonth();
//
//                                    case YEARLY ->
//                                            date.getYear()
//                                                    ==
//                                                    today.getYear();
//
//                                    default ->
//                                            true;
//                                };
//                            })
//
//                            .toList();
//        }
//
//        // =====================================
//        // TYPE FILTER
//        // =====================================
//
//        if (dto.getTransactionType() != null) {
//
//            transactions =
//                    transactions.stream()
//
//                            .filter(t ->
//
//                                    t.getType()
//                                            ==
//                                            dto.getTransactionType()
//                            )
//
//                            .toList();
//
//            /*
//             * EMI is always EXPENSE.
//             * So when INCOME is requested,
//             * EMI must not be included.
//             */
//
//            if (dto.getTransactionType()
//                    != TransactionType.EXPENSE) {
//
//                emiPayments =
//                        new ArrayList<>();
//            }
//        }
//
//        // =====================================
//        // NORMAL TRANSACTION TOTALS
//        // =====================================
//
//        BigDecimal totalIncome =
//                transactions.stream()
//
//                        .filter(t ->
//
//                                t.getType()
//                                        ==
//                                        TransactionType.INCOME
//                        )
//
//                        .map(
//                                Transaction::getAmount
//                        )
//
//                        .reduce(
//                                BigDecimal.ZERO,
//                                BigDecimal::add
//                        );
//
//        BigDecimal normalExpense =
//                transactions.stream()
//
//                        .filter(t ->
//
//                                t.getType()
//                                        ==
//                                        TransactionType.EXPENSE
//                        )
//
//                        .map(
//                                Transaction::getAmount
//                        )
//
//                        .reduce(
//                                BigDecimal.ZERO,
//                                BigDecimal::add
//                        );
//
//        // =====================================
//        // EMI TOTAL
//        // =====================================
//
//        BigDecimal emiExpense =
//                emiPayments.stream()
//
//                        .map(payment ->
//
//                                payment.getEmi()
//                                        .getEmiAmount()
//                        )
//
//                        .reduce(
//                                BigDecimal.ZERO,
//                                BigDecimal::add
//                        );
//
//        // =====================================
//        // TOTAL EXPENSE
//        // =====================================
//
//        BigDecimal totalExpense =
//                normalExpense
//                        .add(
//                                emiExpense
//                        );
//
//        // =====================================
//        // TOTAL AMOUNT
//        // =====================================
//
//        BigDecimal totalAmount =
//                totalIncome
//                        .add(
//                                totalExpense
//                        );
//
//        // =====================================
//        // NET BALANCE
//        // =====================================
//
//        BigDecimal netBalance =
//                totalIncome
//                        .subtract(
//                                totalExpense
//                        );
//
//        // =====================================
//        // CATEGORY WISE NORMAL TRANSACTIONS
//        // =====================================
//
//        Map<Category, List<Transaction>>
//                grouped =
//
//                transactions.stream()
//
//                        .collect(
//
//                                Collectors.groupingBy(
//
//                                        Transaction
//                                                ::getCategory
//                                )
//                        );
//
//        List<CategoryAnalyticsDTO>
//                categories =
//                new ArrayList<>();
//
//        for (Map.Entry<Category,
//                List<Transaction>>
//                entry
//                : grouped.entrySet()) {
//
//            BigDecimal amount =
//                    entry.getValue()
//
//                            .stream()
//
//                            .map(
//                                    Transaction
//                                            ::getAmount
//                            )
//
//                            .reduce(
//                                    BigDecimal.ZERO,
//                                    BigDecimal::add
//                            );
//
//            double percentage =
//
//                    totalAmount.compareTo(
//                            BigDecimal.ZERO
//                    ) == 0
//
//                            ?
//
//                            0
//
//                            :
//
//                            amount.multiply(
//                                            BigDecimal.valueOf(
//                                                    100
//                                            )
//                                    )
//
//                                    .divide(
//                                            totalAmount,
//
//                                            2,
//
//                                            java.math.RoundingMode
//                                                    .HALF_UP
//                                    )
//
//                                    .doubleValue();
//
//            categories.add(
//
//                    CategoryAnalyticsDTO
//                            .builder()
//
//                            .categoryId(
//                                    entry.getKey()
//                                            .getId()
//                            )
//
//                            .categoryName(
//                                    entry.getKey()
//                                            .getName()
//                            )
//
//                            .amount(
//                                    amount
//                            )
//
//                            .transactionCount(
//                                    (long)
//                                            entry.getValue()
//                                                    .size()
//                            )
//
//                            .percentage(
//                                    percentage
//                            )
//
//                            .build()
//            );
//        }
//
//        // =====================================
//        // EMI CATEGORY
//        // =====================================
//
//        if (emiExpense.compareTo(
//                BigDecimal.ZERO
//        ) > 0) {
//
//            double emiPercentage =
//
//                    totalAmount.compareTo(
//                            BigDecimal.ZERO
//                    ) == 0
//
//                            ?
//
//                            0
//
//                            :
//
//                            emiExpense.multiply(
//                                            BigDecimal.valueOf(
//                                                    100
//                                            )
//                                    )
//
//                                    .divide(
//                                            totalAmount,
//
//                                            2,
//
//                                            java.math.RoundingMode
//                                                    .HALF_UP
//                                    )
//
//                                    .doubleValue();
//
//            categories.add(
//
//                    CategoryAnalyticsDTO
//                            .builder()
//
//                            /*
//                             * EMI is a virtual category.
//                             * No Category DB row is created.
//                             */
//                            .categoryId(
//                                    null
//                            )
//
//                            .categoryName(
//                                    "EMI"
//                            )
//
//                            .amount(
//                                    emiExpense
//                            )
//
//                            .transactionCount(
//                                    (long)
//                                            emiPayments.size()
//                            )
//
//                            .percentage(
//                                    emiPercentage
//                            )
//
//                            .build()
//            );
//        }
//
//        // =====================================
//        // RESPONSE
//        // =====================================
//
//        return TransactionAnalyticsResponseDTO
//                .builder()
//
//                .totalAmount(
//                        totalAmount
//                )
//
//                .totalIncome(
//                        totalIncome
//                )
//
//                .totalExpense(
//                        totalExpense
//                )
//
//                .netBalance(
//                        netBalance
//                )
//
//                .categories(
//                        categories
//                )
//
//                .build();
//    }


    private TransactionResponseDTO mapEmiPaymentToTransaction(

            EmiPaymentHistory history

    ) {

        Emi emi =

                history.getEmi();

        return TransactionResponseDTO

                .builder()

                // =====================================
                // ID
                // =====================================

                .id(
                        history.getId()
                )

                // =====================================
                // AMOUNT
                // =====================================

                .amount(
                        emi.getEmiAmount()
                )

                // =====================================
                // CURRENCY
                // =====================================

                .currency(
                        "INR"
                )

                // =====================================
                // TYPE
                // =====================================

                .type(
                        TransactionType.EXPENSE
                )

                // =====================================
                // CATEGORY
                // =====================================

                .categoryId(
                        emi.getCategory().getId()
                )

                .categoryName(
                        emi.getCategory().getName()
                )

                .categoryIcon(
                        emi.getCategory().getIcon()
                )

                // =====================================
                // PAYMENT METHOD
                // =====================================

                .paymentMethodId(
                        null
                )

                .paymentMethodName(
                        "EMI"
                )

                .paymentMethodIcon(
                        null
                )

                // =====================================
                // NOTE
                // =====================================

                .note(
                        emi.getEmiName()
                                + " EMI payment"
                )

                // =====================================
                // TRANSACTION DATE
                // =====================================

                .transactionDate(
                        history.getPaymentDate()
                                .atStartOfDay()
                )

                // =====================================
                // RECURRING
                // =====================================

                .recurring(
                        false
                )

                .recurringType(
                        null
                )

                .recurringUntil(
                        null
                )

                .lastCreatedDate(
                        null
                )

                // =====================================
                // PARENT
                // =====================================

                .parentTransactionId(
                        null
                )

                .parent(
                        false
                )

                // =====================================
                // ACTIVE
                // =====================================

                .active(
                        true
                )

                // =====================================
                // CREATED / UPDATED
                // =====================================

                .createdAt(
                        null
                )

                .updatedAt(
                        null
                )

                .build();
    }
}