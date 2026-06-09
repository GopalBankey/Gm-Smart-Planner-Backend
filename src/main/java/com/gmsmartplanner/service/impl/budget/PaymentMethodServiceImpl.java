package com.gmsmartplanner.service.impl.budget;

import com.gmsmartplanner.dto.request.budget.CreatePaymentMethodRequestDTO;
import com.gmsmartplanner.dto.request.budget.UpdatePaymentMethodRequestDTO;
import com.gmsmartplanner.dto.response.budget.PaymentMethodResponseDTO;
import com.gmsmartplanner.entity.budget.PaymentMethod;
import com.gmsmartplanner.enums.todo.CategoryType;
import com.gmsmartplanner.exception.ResourceNotFoundException;
import com.gmsmartplanner.mapper.budget.PaymentMethodMapper;
import com.gmsmartplanner.repository.budget.PaymentMethodRepository;
import com.gmsmartplanner.service.FileUploadService;
import com.gmsmartplanner.service.budget.PaymentMethodService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentMethodServiceImpl
        implements PaymentMethodService {

    private final PaymentMethodRepository
            paymentMethodRepository;

    private final PaymentMethodMapper
            paymentMethodMapper;

    private final FileUploadService
            fileUploadService;

    // =====================================
    // CREATE PAYMENT METHOD
    // =====================================

    @Override
    public PaymentMethodResponseDTO
    createPaymentMethod(

            CreatePaymentMethodRequestDTO dto

    ) {

        PaymentMethod paymentMethod =
                paymentMethodMapper
                        .createPaymentMethod(dto);

        // =====================================
        // UPLOAD ICON
        // =====================================

        if (dto.getIcon() != null
                && !dto.getIcon().isEmpty()) {

            String iconUrl =
                    fileUploadService
                            .uploadImage(

                                    dto.getIcon(),

                                    "payment-methods"
                            );

            paymentMethod.setIcon(
                    iconUrl
            );
        }

        PaymentMethod savedPaymentMethod =
                paymentMethodRepository.save(
                        paymentMethod
                );

        return paymentMethodMapper
                .mapToResponse(
                        savedPaymentMethod
                );
    }

    // =====================================
    // GET PAYMENT METHODS
    // =====================================

    @Override
    public List<PaymentMethodResponseDTO>
    getPaymentMethods(

            CategoryType type

    ) {

        List<PaymentMethod> paymentMethods;

        // =====================================
        // GET ALL
        // =====================================

        if (type == null) {

            paymentMethods =
                    paymentMethodRepository
                            .findAllByActiveTrue();

        } else {

            List<CategoryType> types =
                    List.of(
                            type,
                            CategoryType.BOTH
                    );

            paymentMethods =
                    paymentMethodRepository
                            .findAllByTypeInAndActiveTrue(
                                    types
                            );
        }

        return paymentMethods
                .stream()
                .map(paymentMethodMapper::mapToResponse)
                .toList();
    }

    // =====================================
    // GET PAYMENT METHOD
    // =====================================

    @Override
    public PaymentMethodResponseDTO
    getPaymentMethod(

            Long paymentMethodId

    ) {

        return paymentMethodMapper
                .mapToResponse(
                        getPaymentMethodEntity(
                                paymentMethodId
                        )
                );
    }

    // =====================================
    // UPDATE PAYMENT METHOD
    // =====================================

    @Override
    public PaymentMethodResponseDTO
    updatePaymentMethod(

            Long paymentMethodId,

            UpdatePaymentMethodRequestDTO dto

    ) {

        PaymentMethod paymentMethod =
                getPaymentMethodEntity(
                        paymentMethodId
                );

        paymentMethodMapper
                .updatePaymentMethod(

                        paymentMethod,

                        dto
                );

        // =====================================
        // UPDATE ICON
        // =====================================

        if (dto.getIcon() != null
                && !dto.getIcon().isEmpty()) {

            String iconUrl =
                    fileUploadService
                            .uploadImage(

                                    dto.getIcon(),

                                    "payment-methods"
                            );

            paymentMethod.setIcon(
                    iconUrl
            );
        }

        PaymentMethod updatedPaymentMethod =
                paymentMethodRepository.save(
                        paymentMethod
                );

        return paymentMethodMapper
                .mapToResponse(
                        updatedPaymentMethod
                );
    }

    // =====================================
    // DELETE PAYMENT METHOD
    // =====================================

    @Override
    public void deletePaymentMethod(
            Long paymentMethodId
    ) {

        PaymentMethod paymentMethod =
                getPaymentMethodEntity(
                        paymentMethodId
                );

        paymentMethod.setActive(false);

        paymentMethodRepository.save(
                paymentMethod
        );
    }

    // =====================================
    // GET PAYMENT METHOD ENTITY
    // =====================================

    private PaymentMethod
    getPaymentMethodEntity(
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
}