package com.gmsmartplanner.controller.budget;

import com.gmsmartplanner.dto.request.budget.TransactionFilterRequestDTO;
import com.gmsmartplanner.dto.response.budget.TransactionAnalyticsResponseDTO;
import com.gmsmartplanner.payload.ApiResponse;
import com.gmsmartplanner.dto.response.budget.BudgetHomeResponseDTO;
import com.gmsmartplanner.dto.response.budget.TransactionFilterResponseDTO;
import com.gmsmartplanner.service.budget.TransactionFilterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/transactions")
public class TransactionFilterController {

    private final TransactionFilterService
            transactionFilterService;

    // =====================================
    // HOME SCREEN API
    // =====================================

    @GetMapping("/home")
    public ResponseEntity<ApiResponse<BudgetHomeResponseDTO>>
    getHomeData(

            Authentication authentication,

            @RequestParam
            String month

    ) {

        String username =
                authentication.getName();

        BudgetHomeResponseDTO response =
                transactionFilterService
                        .getHomeData(

                                username,

                                month
                        );

        return ResponseEntity.ok(

                ApiResponse
                        .<BudgetHomeResponseDTO>builder()

                        .success(true)

                        .message(
                                "Budget home fetched successfully"
                        )

                        .data(response)

                        .build()
        );
    }

    // =====================================
    // FILTER
    // =====================================

    @PostMapping("/filter")
    public ResponseEntity<ApiResponse<TransactionFilterResponseDTO>>
    filterTransactions(

            Authentication authentication,

            @RequestBody
            TransactionFilterRequestDTO dto

    ) {

        String username =
                authentication.getName();

        TransactionFilterResponseDTO response =
                transactionFilterService
                        .filterTransactions(

                                username,

                                dto
                        );

        return ResponseEntity.ok(

                ApiResponse
                        .<TransactionFilterResponseDTO>builder()
                        .success(true)
                        .message("Transactions fetched successfully")
                        .data(response)
                        .build()
        );
    }

    // =====================================
    // ANALYTICS
    // =====================================

    @PostMapping("/analytics")
    public ResponseEntity<ApiResponse<TransactionAnalyticsResponseDTO>>
    getAnalytics(

            Authentication authentication,

            @RequestBody
            TransactionFilterRequestDTO dto

    ) {

        String username =
                authentication.getName();

        TransactionAnalyticsResponseDTO response =
                transactionFilterService
                        .getAnalytics(

                                username,

                                dto
                        );

        return ResponseEntity.ok(

                ApiResponse
                        .<TransactionAnalyticsResponseDTO>builder()
                        .success(true)
                        .message("Analytics fetched successfully")
                        .data(response)
                        .build()
        );
    }
}