package com.gmsmartplanner.service.budget;

import com.gmsmartplanner.dto.request.budget.CreateTransactionRequestDTO;
import com.gmsmartplanner.dto.request.budget.UpdateTransactionRequestDTO;
import com.gmsmartplanner.dto.response.budget.TransactionResponseDTO;
import com.gmsmartplanner.enums.todo.CategoryType;

import java.time.LocalDate;
import java.util.List;

public interface TransactionService {

    // =====================================
    // CREATE TRANSACTION
    // =====================================

    TransactionResponseDTO createTransaction(

            String username,

            CreateTransactionRequestDTO dto
    );


    // =====================================
    // GET TRANSACTION
    // =====================================

    TransactionResponseDTO getTransaction(

            String username,

            Long transactionId
    );

    // =====================================
    // UPDATE TRANSACTION
    // =====================================

    TransactionResponseDTO updateTransaction(

            String username,

            Long transactionId,

            UpdateTransactionRequestDTO dto
    );

    // =====================================
    // DELETE TRANSACTION
    // =====================================

    void deleteTransaction(

            String username,

            Long transactionId
    );
}