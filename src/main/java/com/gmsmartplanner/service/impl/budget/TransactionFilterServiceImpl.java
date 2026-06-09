package com.gmsmartplanner.service.impl.budget;

import com.gmsmartplanner.dto.request.budget.TransactionFilterRequestDTO;
import com.gmsmartplanner.dto.response.budget.*;
import com.gmsmartplanner.entity.User;
import com.gmsmartplanner.entity.budget.Category;
import com.gmsmartplanner.entity.budget.Transaction;
import com.gmsmartplanner.enums.budget.TransactionType;
import com.gmsmartplanner.mapper.budget.TransactionMapper;
import com.gmsmartplanner.repository.budget.TransactionRepository;
import com.gmsmartplanner.service.UserHelperService;
import com.gmsmartplanner.service.budget.TransactionFilterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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

    // =====================================
    // HOME DATA
    // =====================================

    @Override
    public BudgetHomeResponseDTO getHomeData(

            String username,

            String month

    ) {

        User user =
                userHelperService
                        .getCurrentUser(username);

        YearMonth yearMonth =
                YearMonth.parse(month);

        LocalDateTime startDate =
                yearMonth.atDay(1)
                        .atStartOfDay();

        LocalDateTime endDate =
                yearMonth.atEndOfMonth()
                        .atTime(23, 59, 59);

        List<Transaction> transactions =
                transactionRepository
                        .findAllByUserAndTransactionDateBetweenOrderByTransactionDateDesc(
                                user,
                                startDate,
                                endDate
                        );

        BigDecimal income =
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

        BigDecimal expense =
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

        BigDecimal monthlyBalance =
                income.subtract(expense);

        BigDecimal totalIncome =
                transactionRepository
                        .getTotalAmountByType(
                                user,
                                TransactionType.INCOME
                        );

        BigDecimal totalExpense =
                transactionRepository
                        .getTotalAmountByType(
                                user,
                                TransactionType.EXPENSE
                        );

        BigDecimal currentBalance =
                totalIncome.subtract(
                        totalExpense
                );

        List<TransactionResponseDTO>
                recentTransactions =
                transactions.stream()
                        .limit(10)
                        .map(transactionMapper::mapToResponse)
                        .toList();

        return BudgetHomeResponseDTO
                .builder()
                .currentBalance(currentBalance)
                .monthlyBalance(monthlyBalance)
                .income(income)
                .expense(expense)
                .currency("INR")
                .selectedMonth(month)
                .recentTransactions(recentTransactions)
                .build();
    }

    // =====================================
    // FILTER TRANSACTIONS
    // =====================================

    @Override
    public TransactionFilterResponseDTO
    filterTransactions(

            String username,

            TransactionFilterRequestDTO dto

    ) {

        User user =
                userHelperService
                        .getCurrentUser(username);

        List<Transaction> transactions =
                transactionRepository
                        .findAllByUserAndActiveTrueOrderByTransactionDateDesc(
                                user
                        );

        // TRANSACTION TYPE

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
        }

        // CATEGORY FILTER

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
        }

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

        BigDecimal totalExpense =
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

        BigDecimal netBalance =
                totalIncome.subtract(
                        totalExpense
                );

        return TransactionFilterResponseDTO
                .builder()
                .totalIncome(totalIncome)
                .totalExpense(totalExpense)
                .netBalance(netBalance)
                .transactions(
                        transactions.stream()
                                .map(transactionMapper::mapToResponse)
                                .toList()
                )
                .build();
    }

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

        List<Transaction> transactions =
                transactionRepository
                        .findAllByUserAndActiveTrueOrderByTransactionDateDesc(
                                user
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
                                                    == today.getYear()

                                                    &&

                                                    date.getMonth()
                                                            ==
                                                            today.getMonth();

                                    case YEARLY ->
                                            date.getYear()
                                                    ==
                                                    today.getYear();

                                    default -> true;
                                };
                            })

                            .toList();
        }

        // =====================================
        // TYPE
        // =====================================

        if (dto.getTransactionType() != null) {

            transactions =
                    transactions.stream()

                            .filter(t ->

                                    t.getType()
                                            ==
                                            dto.getTransactionType()
                            )

                            .toList();
        }

        // =====================================
        // TOTAL
        // =====================================

        BigDecimal totalAmount =
                transactions.stream()

                        .map(
                                Transaction::getAmount
                        )

                        .reduce(
                                BigDecimal.ZERO,

                                BigDecimal::add
                        );

        // =====================================
        // CATEGORY WISE
        // =====================================

        Map<Category, List<Transaction>>
                grouped =

                transactions.stream()

                        .collect(

                                Collectors.groupingBy(

                                        Transaction
                                                ::getCategory
                                )
                        );

        List<CategoryAnalyticsDTO>
                categories =
                new ArrayList<>();

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

                                            java.math.RoundingMode.HALF_UP
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

        return TransactionAnalyticsResponseDTO
                .builder()

                .totalAmount(
                        totalAmount
                )

                .categories(
                        categories
                )

                .build();
    }
}