package com.gmsmartplanner.service.budget;

import com.gmsmartplanner.dto.request.budget.CreatePaymentMethodRequestDTO;
import com.gmsmartplanner.dto.request.budget.UpdatePaymentMethodRequestDTO;
import com.gmsmartplanner.dto.response.budget.PaymentMethodResponseDTO;
import com.gmsmartplanner.enums.todo.CategoryType;

import java.util.List;

public interface PaymentMethodService {

    // =====================================
    // CREATE PAYMENT METHOD
    // =====================================

    PaymentMethodResponseDTO createPaymentMethod(
            CreatePaymentMethodRequestDTO dto
    );

    // =====================================
    // GET PAYMENT METHODS
    // =====================================

    List<PaymentMethodResponseDTO>
    getPaymentMethods(
            CategoryType type
    );

    // =====================================
    // GET PAYMENT METHOD
    // =====================================

    PaymentMethodResponseDTO getPaymentMethod(
            Long paymentMethodId
    );

    // =====================================
    // UPDATE PAYMENT METHOD
    // =====================================

    PaymentMethodResponseDTO updatePaymentMethod(

            Long paymentMethodId,

            UpdatePaymentMethodRequestDTO dto
    );

    // =====================================
    // DELETE PAYMENT METHOD
    // =====================================

    void deletePaymentMethod(
            Long paymentMethodId
    );
}