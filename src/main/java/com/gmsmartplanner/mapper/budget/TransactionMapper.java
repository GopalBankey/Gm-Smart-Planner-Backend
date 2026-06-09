package com.gmsmartplanner.mapper.budget;

import com.gmsmartplanner.dto.request.budget.CreateTransactionRequestDTO;
import com.gmsmartplanner.dto.request.budget.UpdateTransactionRequestDTO;
import com.gmsmartplanner.dto.response.budget.TransactionResponseDTO;
import com.gmsmartplanner.entity.budget.Category;
import com.gmsmartplanner.entity.budget.PaymentMethod;
import com.gmsmartplanner.entity.budget.Transaction;
import com.gmsmartplanner.entity.User;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper {

    // =====================================
    // CREATE TRANSACTION
    // =====================================

    public Transaction createTransaction(

            CreateTransactionRequestDTO dto,

            User user,

            Category category,

            PaymentMethod paymentMethod

    ) {

        Transaction transaction =
                new Transaction();

        transaction.setUser(user);

        transaction.setAmount(
                dto.getAmount()
        );

        transaction.setCurrency(
                dto.getCurrency()
        );

        transaction.setType(
                dto.getType()
        );

        transaction.setCategory(
                category
        );

        transaction.setPaymentMethod(
                paymentMethod
        );

        transaction.setNote(
                dto.getNote()
        );

        transaction.setTransactionDate(
                dto.getTransactionDate()
        );

        transaction.setRecurring(
                dto.isRecurring()
        );

        transaction.setRecurringType(
                dto.getRecurringType()
        );

        transaction.setRecurringUntil(
                dto.getRecurringUntil()
        );

        transaction.setLastCreatedDate(
                dto.getTransactionDate()
        );

        transaction.setParent(
                dto.isRecurring()
        );

        transaction.setActive(true);

        return transaction;
    }

    // =====================================
    // UPDATE TRANSACTION
    // =====================================

    public void updateTransaction(

            Transaction transaction,

            UpdateTransactionRequestDTO dto,

            Category category,

            PaymentMethod paymentMethod

    ) {

        if (dto.getAmount() != null) {

            transaction.setAmount(
                    dto.getAmount()
            );
        }

        if (dto.getCurrency() != null
                && !dto.getCurrency().isBlank()) {

            transaction.setCurrency(
                    dto.getCurrency()
            );
        }

        if (dto.getType() != null) {

            transaction.setType(
                    dto.getType()
            );
        }

        if (category != null) {

            transaction.setCategory(
                    category
            );
        }

        if (paymentMethod != null) {

            transaction.setPaymentMethod(
                    paymentMethod
            );
        }

        if (dto.getNote() != null) {

            transaction.setNote(
                    dto.getNote()
            );
        }

        if (dto.getTransactionDate() != null) {

            transaction.setTransactionDate(
                    dto.getTransactionDate()
            );
        }

        if (dto.getRecurring() != null) {

            transaction.setRecurring(
                    dto.getRecurring()
            );
        }

        if (dto.getRecurringType() != null) {

            transaction.setRecurringType(
                    dto.getRecurringType()
            );
        }

        if (dto.getRecurringUntil() != null) {

            transaction.setRecurringUntil(
                    dto.getRecurringUntil()
            );
        }
    }

    // =====================================
    // MAP RESPONSE
    // =====================================

    public TransactionResponseDTO
    mapToResponse(
            Transaction transaction
    ) {

        return TransactionResponseDTO
                .builder()

                .id(transaction.getId())

                .amount(
                        transaction.getAmount()
                )

                .currency(
                        transaction.getCurrency()
                )

                .type(
                        transaction.getType()
                )

                // CATEGORY
                .categoryId(
                        transaction.getCategory().getId()
                )

                .categoryName(
                        transaction.getCategory().getName()
                )

                .categoryIcon(
                        transaction.getCategory().getIcon()
                )

                .categoryColorCode(
                        transaction.getCategory().getColorCode()
                )

                // PAYMENT METHOD
                .paymentMethodId(
                        transaction.getPaymentMethod().getId()
                )

                .paymentMethodName(
                        transaction.getPaymentMethod().getName()
                )

                .paymentMethodIcon(
                        transaction.getPaymentMethod().getIcon()
                )

                .note(
                        transaction.getNote()
                )

                .transactionDate(
                        transaction.getTransactionDate()
                )

                // RECURRING
                .recurring(
                        transaction.isRecurring()
                )

                .recurringType(
                        transaction.getRecurringType()
                )

                .recurringUntil(
                        transaction.getRecurringUntil()
                )

                .lastCreatedDate(
                        transaction.getLastCreatedDate()
                )

                // PARENT
                .parentTransactionId(
                        transaction.getParentTransactionId()
                )

                .parent(
                        transaction.isParent()
                )

                // ACTIVE
                .active(
                        transaction.isActive()
                )

                // CREATED / UPDATED
                .createdAt(
                        transaction.getCreatedAt()
                )

                .updatedAt(
                        transaction.getUpdatedAt()
                )

                .build();
    }
}