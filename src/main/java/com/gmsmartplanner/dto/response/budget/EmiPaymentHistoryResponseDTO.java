package com.gmsmartplanner.dto.response.budget;

import com.gmsmartplanner.enums.budget.EmiPaymentStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class EmiPaymentHistoryResponseDTO {

    private Long id;

    private Long emiId;

    private String emiName;

    private LocalDate paymentDate;

    private Integer paymentMonth;

    private Integer paymentYear;

    private EmiPaymentStatus status;
}