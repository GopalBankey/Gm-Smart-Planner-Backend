package com.gmsmartplanner.mapper.health;

import com.gmsmartplanner.dto.request.health.CreateAllergyRequestDTO;
import com.gmsmartplanner.dto.request.health.UpdateAllergyRequestDTO;
import com.gmsmartplanner.dto.response.health.AllergyResponseDTO;
import com.gmsmartplanner.entity.health.Allergy;
import org.springframework.stereotype.Component;

@Component
public class AllergyMapper {

    // =====================================
    // CREATE
    // =====================================

    public Allergy
    createAllergy(

            CreateAllergyRequestDTO dto

    ) {

        Allergy allergy =
                new Allergy();

        allergy.setAllergicTo(
                dto.getAllergicTo()
        );

        allergy.setReaction(
                dto.getReaction()
        );

        allergy.setOtherReaction(
                dto.getOtherReaction()
        );

        allergy.setSeverity(
                dto.getSeverity()
        );

        allergy.setDoctorAdvice(
                dto.getDoctorAdvice()
        );

        allergy.setActive(
                true
        );

        return allergy;
    }

    // =====================================
    // UPDATE
    // =====================================

    public void
    updateAllergy(

            Allergy allergy,

            UpdateAllergyRequestDTO dto

    ) {

        if (
                dto.getAllergicTo()
                        != null
        ) {

            allergy.setAllergicTo(
                    dto.getAllergicTo()
            );
        }

        if (
                dto.getReaction()
                        != null
        ) {

            allergy.setReaction(
                    dto.getReaction()
            );
        }

        if (
                dto.getOtherReaction()
                        != null
        ) {

            allergy.setOtherReaction(
                    dto.getOtherReaction()
            );
        }

        if (
                dto.getSeverity()
                        != null
        ) {

            allergy.setSeverity(
                    dto.getSeverity()
            );
        }

        if (
                dto.getDoctorAdvice()
                        != null
        ) {

            allergy.setDoctorAdvice(
                    dto.getDoctorAdvice()
            );
        }
    }

    // =====================================
    // RESPONSE
    // =====================================

    public AllergyResponseDTO
    mapToResponse(

            Allergy allergy

    ) {

        return AllergyResponseDTO
                .builder()

                .id(
                        allergy.getId()
                )

                .allergicTo(
                        allergy.getAllergicTo()
                )

                .reaction(
                        allergy.getReaction()
                )

                .otherReaction(
                        allergy.getOtherReaction()
                )

                .severity(
                        allergy.getSeverity()
                )

                .doctorAdvice(
                        allergy.getDoctorAdvice()
                )

                .active(
                        allergy.isActive()
                )

                .build();
    }
}