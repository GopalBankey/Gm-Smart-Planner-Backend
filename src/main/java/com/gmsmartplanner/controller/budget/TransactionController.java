package com.gmsmartplanner.controller.budget;

import com.gmsmartplanner.dto.request.budget.CreateTransactionRequestDTO;
import com.gmsmartplanner.dto.request.budget.UpdateTransactionRequestDTO;
import com.gmsmartplanner.dto.response.budget.TransactionResponseDTO;
import com.gmsmartplanner.enums.todo.CategoryType;
import com.gmsmartplanner.payload.ApiResponse;
import com.gmsmartplanner.service.budget.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/transactions")
public class TransactionController {

    private final TransactionService
            transactionService;

    // =====================================
    // CREATE TRANSACTION
    // =====================================

    @PostMapping
    public ResponseEntity<ApiResponse<TransactionResponseDTO>>
    createTransaction(

            Authentication authentication,

            @Valid
            @RequestBody
            CreateTransactionRequestDTO dto

    ) {

        String username =
                authentication.getName();

        TransactionResponseDTO response =
                transactionService
                        .createTransaction(

                                username,

                                dto
                        );

        return ResponseEntity.status(
                HttpStatus.CREATED
        ).body(

                ApiResponse
                        .<TransactionResponseDTO>builder()

                        .success(true)

                        .message(
                                "Transaction created successfully"
                        )

                        .data(response)

                        .build()
        );
    }

    // =====================================
    // GET TRANSACTION
    // =====================================

    @GetMapping("/{transactionId}")
    public ResponseEntity<ApiResponse<TransactionResponseDTO>>
    getTransaction(

            Authentication authentication,

            @PathVariable
            Long transactionId

    ) {

        String username =
                authentication.getName();

        TransactionResponseDTO response =
                transactionService
                        .getTransaction(

                                username,

                                transactionId
                        );

        return ResponseEntity.ok(

                ApiResponse
                        .<TransactionResponseDTO>builder()

                        .success(true)

                        .message(
                                "Transaction fetched successfully"
                        )

                        .data(response)

                        .build()
        );
    }

    // =====================================
    // UPDATE TRANSACTION
    // =====================================

    @PatchMapping("/{transactionId}")
    public ResponseEntity<ApiResponse<TransactionResponseDTO>>
    updateTransaction(

            Authentication authentication,

            @PathVariable
            Long transactionId,

            @RequestBody
            UpdateTransactionRequestDTO dto

    ) {

        String username =
                authentication.getName();

        TransactionResponseDTO response =
                transactionService
                        .updateTransaction(

                                username,

                                transactionId,

                                dto
                        );

        return ResponseEntity.ok(

                ApiResponse
                        .<TransactionResponseDTO>builder()

                        .success(true)

                        .message(
                                "Transaction updated successfully"
                        )

                        .data(response)

                        .build()
        );
    }

    // =====================================
    // DELETE TRANSACTION
    // =====================================

    @DeleteMapping("/{transactionId}")
    public ResponseEntity<ApiResponse<Void>>
    deleteTransaction(

            Authentication authentication,

            @PathVariable
            Long transactionId

    ) {

        String username =
                authentication.getName();

        transactionService
                .deleteTransaction(

                        username,

                        transactionId
                );

        return ResponseEntity.ok(

                ApiResponse
                        .<Void>builder()

                        .success(true)

                        .message(
                                "Transaction deleted successfully"
                        )

                        .build()
        );
    }
}