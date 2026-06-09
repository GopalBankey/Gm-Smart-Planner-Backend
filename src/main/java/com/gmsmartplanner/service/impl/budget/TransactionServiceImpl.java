package com.gmsmartplanner.service.impl.budget;

import com.gmsmartplanner.dto.request.budget.CreateTransactionRequestDTO;
import com.gmsmartplanner.dto.request.budget.UpdateTransactionRequestDTO;
import com.gmsmartplanner.dto.response.budget.TransactionResponseDTO;
import com.gmsmartplanner.entity.User;
import com.gmsmartplanner.entity.budget.Category;
import com.gmsmartplanner.entity.budget.PaymentMethod;
import com.gmsmartplanner.entity.budget.Transaction;
import com.gmsmartplanner.enums.budget.TransactionType;
import com.gmsmartplanner.enums.todo.CategoryType;
import com.gmsmartplanner.exception.InvalidRequestException;
import com.gmsmartplanner.exception.ResourceNotFoundException;
import com.gmsmartplanner.mapper.budget.TransactionMapper;
import com.gmsmartplanner.repository.UserRepository;
import com.gmsmartplanner.repository.budget.CategoryRepository;
import com.gmsmartplanner.repository.budget.PaymentMethodRepository;
import com.gmsmartplanner.repository.budget.TransactionRepository;
import com.gmsmartplanner.service.UserHelperService;
import com.gmsmartplanner.service.budget.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class TransactionServiceImpl
        implements TransactionService {

    private final TransactionRepository
            transactionRepository;

    private final UserRepository
            userRepository;

    private final CategoryRepository
            categoryRepository;

    private final PaymentMethodRepository
            paymentMethodRepository;

    private final TransactionMapper
            transactionMapper;

    private final UserHelperService
            userHelperService;

    // =====================================
    // CREATE TRANSACTION
    // =====================================

    @Override
    public TransactionResponseDTO
    createTransaction(

            String username,

            CreateTransactionRequestDTO dto

    ) {

        User user =
                userHelperService
                        .getCurrentUser(
                                username
                        );

        Category category =
                getCategory(
                        dto.getCategoryId()
                );

        PaymentMethod paymentMethod =
                getPaymentMethod(
                        dto.getPaymentMethodId()
                );

        validateTransaction(

                dto.getType(),

                category,

                paymentMethod
        );

        Transaction transaction =
                transactionMapper
                        .createTransaction(

                                dto,

                                user,

                                category,

                                paymentMethod
                        );

        Transaction savedTransaction =
                transactionRepository.save(
                        transaction
                );

        // =====================================
        // SET PARENT TRANSACTION ID
        // =====================================

        if (savedTransaction.isParent()) {

            savedTransaction
                    .setParentTransactionId(
                            savedTransaction.getId()
                    );

            savedTransaction =
                    transactionRepository.save(
                            savedTransaction
                    );
        }

        return transactionMapper
                .mapToResponse(
                        savedTransaction
                );
    }

    // =====================================
    // GET TRANSACTION
    // =====================================

    @Override
    public TransactionResponseDTO
    getTransaction(

            String username,

            Long transactionId

    ) {

        User user =
                userHelperService
                        .getCurrentUser(
                                username
                        );

        Transaction transaction =
                getTransactionEntity(
                        transactionId
                );

        validateTransactionOwner(
                user,
                transaction
        );

        return transactionMapper
                .mapToResponse(
                        transaction
                );
    }

    // =====================================
    // UPDATE TRANSACTION
    // =====================================

    @Override
    public TransactionResponseDTO
    updateTransaction(

            String username,

            Long transactionId,

            UpdateTransactionRequestDTO dto

    ) {

        User user =
                userHelperService
                        .getCurrentUser(
                                username
                        );

        Transaction transaction =
                getTransactionEntity(
                        transactionId
                );

        validateTransactionOwner(
                user,
                transaction
        );

        Category category = null;

        PaymentMethod paymentMethod = null;

        if (dto.getCategoryId() != null) {

            category =
                    getCategory(
                            dto.getCategoryId()
                    );
        }

        if (dto.getPaymentMethodId() != null) {

            paymentMethod =
                    getPaymentMethod(
                            dto.getPaymentMethodId()
                    );
        }

        TransactionType finalType =
                dto.getType() != null
                        ? dto.getType()
                        : transaction.getType();

        validateTransaction(

                finalType,

                category != null
                        ? category
                        : transaction.getCategory(),

                paymentMethod != null
                        ? paymentMethod
                        : transaction.getPaymentMethod()
        );

        transactionMapper
                .updateTransaction(

                        transaction,

                        dto,

                        category,

                        paymentMethod
                );

        Transaction updatedTransaction =
                transactionRepository.save(
                        transaction
                );

        return transactionMapper
                .mapToResponse(
                        updatedTransaction
                );
    }

    // =====================================
    // DELETE TRANSACTION
    // =====================================

    @Override
    public void deleteTransaction(

            String username,

            Long transactionId

    ) {

        User user =
                userHelperService
                        .getCurrentUser(
                                username
                        );

        Transaction transaction =
                getTransactionEntity(
                        transactionId
                );

        validateTransactionOwner(
                user,
                transaction
        );

        transaction.setActive(false);

        transactionRepository.save(
                transaction
        );
    }

    // =====================================
    // GET CATEGORY
    // =====================================

    private Category getCategory(
            Long categoryId
    ) {

        return categoryRepository
                .findById(categoryId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Category not found"
                        )
                );
    }

    // =====================================
    // GET PAYMENT METHOD
    // =====================================

    private PaymentMethod getPaymentMethod(
            Long paymentMethodId
    ) {

        return paymentMethodRepository
                .findById(paymentMethodId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Payment method not found"
                        )
                );
    }

    // =====================================
    // GET TRANSACTION ENTITY
    // =====================================

    private Transaction getTransactionEntity(
            Long transactionId
    ) {

        return transactionRepository
                .findById(transactionId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Transaction not found"
                        )
                );
    }

    // =====================================
    // VALIDATE TRANSACTION OWNER
    // =====================================

    private void validateTransactionOwner(

            User user,

            Transaction transaction

    ) {

        if (!transaction.getUser()
                .getId()
                .equals(user.getId())) {

            throw new InvalidRequestException(
                    "You cannot access this transaction"
            );
        }
    }

    // =====================================
    // VALIDATE CATEGORY & PAYMENT METHOD
    // =====================================

    private void validateTransaction(

            TransactionType type,

            Category category,

            PaymentMethod paymentMethod

    ) {

        // CATEGORY VALIDATION

        if (category.getType()
                != CategoryType.BOTH

                &&

                !category.getType().name().equals(
                        type.name()
                )) {

            throw new InvalidRequestException(
                    "Invalid category for selected transaction type"
            );
        }

        // PAYMENT METHOD VALIDATION

        if (paymentMethod.getType()
                != CategoryType.BOTH

                &&

                !paymentMethod.getType().name().equals(
                        type.name()
                )) {

            throw new InvalidRequestException(
                    "Invalid payment method for selected transaction type"
            );
        }
    }
}