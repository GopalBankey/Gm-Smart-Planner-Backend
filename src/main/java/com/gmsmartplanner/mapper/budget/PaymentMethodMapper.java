package com.gmsmartplanner.mapper.budget;

import com.gmsmartplanner.dto.request.budget.CreatePaymentMethodRequestDTO;
import com.gmsmartplanner.dto.request.budget.UpdatePaymentMethodRequestDTO;
import com.gmsmartplanner.dto.response.budget.PaymentMethodResponseDTO;
import com.gmsmartplanner.entity.budget.PaymentMethod;
import org.springframework.stereotype.Component;

@Component
public class PaymentMethodMapper {

    private static final String
            DEFAULT_ICON_URL =

            "https://cdn-icons-png.flaticon.com/512/2830/2830284.png";

    // =====================================
    // CREATE PAYMENT METHOD
    // =====================================

    public PaymentMethod createPaymentMethod(
            CreatePaymentMethodRequestDTO dto
    ) {

        PaymentMethod paymentMethod =
                new PaymentMethod();

        paymentMethod.setName(
                dto.getName()
        );

        paymentMethod.setType(
                dto.getType()
        );

        paymentMethod.setActive(true);

        return paymentMethod;
    }

    // =====================================
    // UPDATE PAYMENT METHOD
    // =====================================

    public void updatePaymentMethod(

            PaymentMethod paymentMethod,

            UpdatePaymentMethodRequestDTO dto

    ) {

        if (dto.getName() != null) {

            paymentMethod.setName(
                    dto.getName()
            );
        }

        if (dto.getType() != null) {

            paymentMethod.setType(
                    dto.getType()
            );
        }
    }

    // =====================================
    // MAP RESPONSE
    // =====================================

    public PaymentMethodResponseDTO
    mapToResponse(
            PaymentMethod paymentMethod
    ) {

        return PaymentMethodResponseDTO
                .builder()

                .id(paymentMethod.getId())

                .name(paymentMethod.getName())

                .iconUrl(

                        paymentMethod.getIcon() != null

                                ?

                                paymentMethod.getIcon()

                                :

                                DEFAULT_ICON_URL
                )

                .type(paymentMethod.getType())

                .active(
                        paymentMethod.isActive()
                )

                .build();
    }
}