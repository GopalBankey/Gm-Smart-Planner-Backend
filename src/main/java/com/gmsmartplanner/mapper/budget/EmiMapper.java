package com.gmsmartplanner.mapper.budget;

import com.gmsmartplanner.dto.request.budget.CreateEmiRequestDTO;
import com.gmsmartplanner.dto.request.budget.UpdateEmiRequestDTO;
import com.gmsmartplanner.dto.response.budget.EmiResponseDTO;
import com.gmsmartplanner.entity.budget.Emi;
import org.springframework.stereotype.Component;

@Component
public class EmiMapper {

    // =====================================
    // CREATE
    // =====================================

    public Emi createEmi(

            CreateEmiRequestDTO dto

    ) {

        Emi emi =
                new Emi();



        // =====================================
        // BASIC
        // =====================================

        emi.setEmiName(
                dto.getEmiName()
        );

        emi.setType(
                dto.getType()
        );

        // =====================================
        // LOAN DETAILS
        // =====================================

        emi.setLoanAmount(
                dto.getLoanAmount()
        );

        emi.setBankName(
                dto.getBankName()
        );

        // =====================================
        // CREDIT CARD DETAILS
        // =====================================

        emi.setCreditCardNumber(
                dto.getCreditCardNumber()
        );

        emi.setCardHolderName(
                dto.getCardHolderName()
        );

        emi.setCreditCardType(
                dto.getCreditCardType()
        );

        // =====================================
        // EMI DETAILS
        // =====================================

        emi.setEmiAmount(
                dto.getEmiAmount()
        );

        emi.setTotalInstallments(
                dto.getTotalInstallments()
        );

        emi.setEmiDueDate(
                dto.getEmiDueDate()
        );

        emi.setPaidInstallments(
                0
        );

        emi.setActive(
                true
        );

        return emi;
    }

    // =====================================
    // UPDATE
    // =====================================

    public void updateEmi(

            Emi emi,

            UpdateEmiRequestDTO dto

    ) {

        // =====================================
        // BASIC
        // =====================================

        if (

                dto.getEmiName()
                        != null

        ) {

            emi.setEmiName(
                    dto.getEmiName()
            );
        }

        if (

                dto.getType()
                        != null

        ) {

            emi.setType(
                    dto.getType()
            );
        }

        // =====================================
        // LOAN DETAILS
        // =====================================

        if (

                dto.getLoanAmount()
                        != null

        ) {

            emi.setLoanAmount(
                    dto.getLoanAmount()
            );
        }

        if (

                dto.getBankName()
                        != null

        ) {

            emi.setBankName(
                    dto.getBankName()
            );
        }

        // =====================================
        // CREDIT CARD DETAILS
        // =====================================

        if (

                dto.getCreditCardNumber()
                        != null

        ) {

            emi.setCreditCardNumber(
                    dto.getCreditCardNumber()
            );
        }

        if (

                dto.getCardHolderName()
                        != null

        ) {

            emi.setCardHolderName(
                    dto.getCardHolderName()
            );
        }

        if (

                dto.getCreditCardType()
                        != null

        ) {

            emi.setCreditCardType(
                    dto.getCreditCardType()
            );
        }

        // =====================================
        // EMI DETAILS
        // =====================================

        if (

                dto.getEmiAmount()
                        != null

        ) {

            emi.setEmiAmount(
                    dto.getEmiAmount()
            );
        }

        if (

                dto.getTotalInstallments()
                        != null

        ) {

            emi.setTotalInstallments(
                    dto.getTotalInstallments()
            );
        }

        if (

                dto.getEmiDueDate()
                        != null

        ) {

            emi.setEmiDueDate(
                    dto.getEmiDueDate()
            );
        }

        // =====================================
        // STATUS
        // =====================================

        if (

                dto.getActive()
                        != null

        ) {

            emi.setActive(
                    dto.getActive()
            );
        }
    }

    // =====================================
    // RESPONSE
    // =====================================

    public EmiResponseDTO mapToResponse(

            Emi emi

    ) {

        return EmiResponseDTO
                .builder()

                // =====================================
                // BASIC
                // =====================================

                .id(
                        emi.getId()
                )

                .categoryId(
                        emi.getCategory()
                                .getId()
                )

                .categoryName(
                        emi.getCategory()
                                .getName()
                )

                .categoryIcon(
                        emi.getCategory()
                                .getIcon()
                )

                .emiName(
                        emi.getEmiName()
                )

                .type(
                        emi.getType()
                )

                // =====================================
                // LOAN DETAILS
                // =====================================

                .loanAmount(
                        emi.getLoanAmount()
                )

                .bankName(
                        emi.getBankName()
                )

                // =====================================
                // CREDIT CARD DETAILS
                // =====================================

                .creditCardNumber(
                        emi.getCreditCardNumber()
                )

                .cardHolderName(
                        emi.getCardHolderName()
                )

                .creditCardType(
                        emi.getCreditCardType()
                )

                // =====================================
                // EMI DETAILS
                // =====================================

                .emiAmount(
                        emi.getEmiAmount()
                )

                .totalInstallments(
                        emi.getTotalInstallments()
                )

                .paidInstallments(
                        emi.getPaidInstallments()
                )

                .remainingInstallments(

                        emi.getTotalInstallments()

                                -

                                emi.getPaidInstallments()
                )

                .emiDueDate(
                        emi.getEmiDueDate()
                )

                // =====================================
                // STATUS
                // =====================================

                .active(
                        emi.isActive()
                )

                .build();
    }
}