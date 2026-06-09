package com.gmsmartplanner.controller.budget;

import com.gmsmartplanner.dto.request.budget.CreatePaymentMethodRequestDTO;
import com.gmsmartplanner.dto.request.budget.UpdatePaymentMethodRequestDTO;
import com.gmsmartplanner.dto.response.budget.PaymentMethodResponseDTO;
import com.gmsmartplanner.enums.todo.CategoryType;
import com.gmsmartplanner.payload.ApiResponse;
import com.gmsmartplanner.service.budget.PaymentMethodService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/payment-methods")
@RequiredArgsConstructor
public class PaymentMethodController {

    private final PaymentMethodService
            paymentMethodService;

    // =====================================
    // CREATE PAYMENT METHOD
    // =====================================

    @PostMapping(
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<ApiResponse<PaymentMethodResponseDTO>>
    createPaymentMethod(

            @Valid
            @ModelAttribute
            CreatePaymentMethodRequestDTO dto

    ) {

        PaymentMethodResponseDTO response =
                paymentMethodService
                        .createPaymentMethod(
                                dto
                        );

        return ResponseEntity.ok(

                ApiResponse
                        .<PaymentMethodResponseDTO>builder()

                        .success(true)

                        .message(
                                "Payment method created successfully"
                        )

                        .data(response)

                        .build()
        );
    }

    // =====================================
    // GET PAYMENT METHODS
    // =====================================
    @GetMapping
    public ResponseEntity<ApiResponse<List<PaymentMethodResponseDTO>>>
    getPaymentMethods(

            @RequestParam(required = false)
            CategoryType type

    ) {

        List<PaymentMethodResponseDTO> response =
                paymentMethodService
                        .getPaymentMethods(
                                type
                        );

        return ResponseEntity.ok(

                ApiResponse
                        .<List<PaymentMethodResponseDTO>>builder()

                        .success(true)

                        .message(
                                "Payment methods fetched successfully"
                        )

                        .data(response)

                        .build()
        );
    }

    // =====================================
    // GET PAYMENT METHOD
    // =====================================

    @GetMapping("/{paymentMethodId}")
    public ResponseEntity<ApiResponse<PaymentMethodResponseDTO>>
    getPaymentMethod(

            @PathVariable
            Long paymentMethodId

    ) {

        PaymentMethodResponseDTO response =
                paymentMethodService
                        .getPaymentMethod(
                                paymentMethodId
                        );

        return ResponseEntity.ok(

                ApiResponse
                        .<PaymentMethodResponseDTO>builder()

                        .success(true)

                        .message(
                                "Payment method fetched successfully"
                        )

                        .data(response)

                        .build()
        );
    }

    // =====================================
    // UPDATE PAYMENT METHOD
    // =====================================

    @PatchMapping(
            value = "/{paymentMethodId}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<ApiResponse<PaymentMethodResponseDTO>>
    updatePaymentMethod(

            @PathVariable
            Long paymentMethodId,

            @ModelAttribute
            UpdatePaymentMethodRequestDTO dto

    ) {

        PaymentMethodResponseDTO response =
                paymentMethodService
                        .updatePaymentMethod(

                                paymentMethodId,

                                dto
                        );

        return ResponseEntity.ok(

                ApiResponse
                        .<PaymentMethodResponseDTO>builder()

                        .success(true)

                        .message(
                                "Payment method updated successfully"
                        )

                        .data(response)

                        .build()
        );
    }

    // =====================================
    // DELETE PAYMENT METHOD
    // =====================================

    @DeleteMapping("/{paymentMethodId}")
    public ResponseEntity<ApiResponse<Void>>
    deletePaymentMethod(

            @PathVariable
            Long paymentMethodId

    ) {

        paymentMethodService
                .deletePaymentMethod(
                        paymentMethodId
                );

        return ResponseEntity.ok(

                ApiResponse
                        .<Void>builder()

                        .success(true)

                        .message(
                                "Payment method deleted successfully"
                        )

                        .build()
        );
    }
}