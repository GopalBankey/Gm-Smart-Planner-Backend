package com.gmsmartplanner.mapper.health;

import com.gmsmartplanner.dto.request.health.CreateExtraMedicineRequestDTO;
import com.gmsmartplanner.dto.request.health.UpdateExtraMedicineRequestDTO;
import com.gmsmartplanner.dto.response.health.ExtraMedicineResponseDTO;
import com.gmsmartplanner.entity.health.ExtraMedicine;
import org.springframework.stereotype.Component;

@Component
public class ExtraMedicineMapper {

    // =====================================
    // CREATE
    // =====================================

    public ExtraMedicine createExtraMedicine(

            CreateExtraMedicineRequestDTO dto

    ) {

        ExtraMedicine medicine =
                new ExtraMedicine();

        medicine.setMedicineName(
                dto.getMedicineName()
        );

        medicine.setDosageStrength(
                dto.getDosageStrength()
        );

        medicine.setForm(
                dto.getForm()
        );

        medicine.setPurpose(
                dto.getPurpose()
        );

        medicine.setPillColor(
                dto.getPillColor()
        );

        medicine.setCount(
                dto.getCount()
        );

        medicine.setExpiryDate(
                dto.getExpiryDate()
        );

        medicine.setCompanyName(
                dto.getCompanyName()
        );

        medicine.setActive(
                true
        );

        return medicine;
    }

    // =====================================
    // UPDATE
    // =====================================

    public void updateExtraMedicine(

            ExtraMedicine medicine,

            UpdateExtraMedicineRequestDTO dto

    ) {

        if (dto.getMedicineName() != null) {

            medicine.setMedicineName(
                    dto.getMedicineName()
            );
        }

        if (dto.getDosageStrength() != null) {

            medicine.setDosageStrength(
                    dto.getDosageStrength()
            );
        }

        if (dto.getForm() != null) {

            medicine.setForm(
                    dto.getForm()
            );
        }

        if (dto.getPurpose() != null) {

            medicine.setPurpose(
                    dto.getPurpose()
            );
        }

        if (dto.getPillColor() != null) {

            medicine.setPillColor(
                    dto.getPillColor()
            );
        }

        if (dto.getCount() != null) {

            medicine.setCount(
                    dto.getCount()
            );
        }

        if (dto.getExpiryDate() != null) {

            medicine.setExpiryDate(
                    dto.getExpiryDate()
            );
        }

        if (dto.getCompanyName() != null) {

            medicine.setCompanyName(
                    dto.getCompanyName()
            );
        }
    }

    // =====================================
    // RESPONSE
    // =====================================

    public ExtraMedicineResponseDTO mapToResponse(

            ExtraMedicine medicine

    ) {

        return ExtraMedicineResponseDTO
                .builder()

                .id(
                        medicine.getId()
                )

                .medicineName(
                        medicine.getMedicineName()
                )

                .dosageStrength(
                        medicine.getDosageStrength()
                )

                .form(
                        medicine.getForm()
                )

                .purpose(
                        medicine.getPurpose()
                )

                .pillColor(
                        medicine.getPillColor()
                )

                .pillPhoto(
                        medicine.getPillPhoto()
                )

                .count(
                        medicine.getCount()
                )

                .expiryDate(
                        medicine.getExpiryDate()
                )

                .companyName(
                        medicine.getCompanyName()
                )

                .build();
    }
}