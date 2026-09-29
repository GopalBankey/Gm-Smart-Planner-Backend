package com.gmsmartplanner.service.impl.health;

import com.gmsmartplanner.dto.request.health.CreateAllergyRequestDTO;
import com.gmsmartplanner.dto.request.health.UpdateAllergyRequestDTO;
import com.gmsmartplanner.dto.response.health.AllergyResponseDTO;
import com.gmsmartplanner.entity.User;
import com.gmsmartplanner.entity.health.Allergy;
import com.gmsmartplanner.enums.health.ReactionType;
import com.gmsmartplanner.exception.InvalidRequestException;
import com.gmsmartplanner.exception.ResourceNotFoundException;
import com.gmsmartplanner.mapper.health.AllergyMapper;
import com.gmsmartplanner.repository.health.AllergyRepository;
import com.gmsmartplanner.service.UserHelperService;
import com.gmsmartplanner.service.health.AllergyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AllergyServiceImpl
        implements AllergyService {

    private final AllergyRepository
            allergyRepository;

    private final AllergyMapper
            allergyMapper;

    private final UserHelperService
            userHelperService;

    // =====================================
    // CREATE
    // =====================================

    @Override
    public AllergyResponseDTO
    createAllergy(

            String username,

            CreateAllergyRequestDTO dto

    ) {

        User user =
                userHelperService
                        .getCurrentUser(
                                username
                        );

        validateOtherReaction(
                dto.getReaction(),
                dto.getOtherReaction()
        );

        Allergy allergy =
                allergyMapper
                        .createAllergy(
                                dto
                        );

        allergy.setUser(
                user
        );

        Allergy saved =
                allergyRepository
                        .save(
                                allergy
                        );

        return allergyMapper
                .mapToResponse(
                        saved
                );
    }

    // =====================================
    // GET ALL
    // =====================================

    @Override
    @Transactional(readOnly = true)
    public List<AllergyResponseDTO>
    getAllergies(

            String username

    ) {

        User user =
                userHelperService
                        .getCurrentUser(
                                username
                        );

        return allergyRepository
                .findAllByUserAndActiveTrueOrderByCreatedAtDesc(
                        user
                )
                .stream()
                .map(
                        allergyMapper
                                ::mapToResponse
                )
                .toList();
    }

    // =====================================
    // GET BY ID
    // =====================================

    @Override
    @Transactional(readOnly = true)
    public AllergyResponseDTO
    getAllergy(

            String username,

            Long allergyId

    ) {

        User user =
                userHelperService
                        .getCurrentUser(
                                username
                        );

        Allergy allergy =
                getAllergyEntity(
                        user,
                        allergyId
                );

        return allergyMapper
                .mapToResponse(
                        allergy
                );
    }

    // =====================================
    // UPDATE
    // =====================================

    @Override
    public AllergyResponseDTO
    updateAllergy(

            String username,

            Long allergyId,

            UpdateAllergyRequestDTO dto

    ) {

        User user =
                userHelperService
                        .getCurrentUser(
                                username
                        );

        Allergy allergy =
                getAllergyEntity(
                        user,
                        allergyId
                );

        ReactionType reaction =

                dto.getReaction() != null

                        ?

                        dto.getReaction()

                        :

                        allergy.getReaction();

        String otherReaction =

                dto.getOtherReaction() != null

                        ?

                        dto.getOtherReaction()

                        :

                        allergy.getOtherReaction();

        // =====================================
        // VALIDATE OTHER REACTION
        // =====================================

        validateOtherReaction(
                reaction,
                otherReaction
        );

        // =====================================
        // UPDATE
        // =====================================

        allergyMapper
                .updateAllergy(
                        allergy,
                        dto
                );

        // =====================================
        // CLEAR OTHER REACTION
        // =====================================

        if (
                allergy.getReaction()
                        !=
                        ReactionType.OTHER
        ) {

            allergy.setOtherReaction(
                    null
            );
        }

        Allergy updated =
                allergyRepository
                        .save(
                                allergy
                        );

        return allergyMapper
                .mapToResponse(
                        updated
                );
    }

    // =====================================
    // DELETE
    // =====================================

    @Override
    public void deleteAllergy(

            String username,

            Long allergyId

    ) {

        User user =
                userHelperService
                        .getCurrentUser(
                                username
                        );

        Allergy allergy =
                getAllergyEntity(
                        user,
                        allergyId
                );

        allergy.setActive(
                false
        );

        allergyRepository
                .save(
                        allergy
                );
    }

    // =====================================
    // GET ENTITY
    // =====================================

    private Allergy
    getAllergyEntity(

            User user,

            Long allergyId

    ) {

        return allergyRepository
                .findByIdAndUserAndActiveTrue(

                        allergyId,

                        user
                )

                .orElseThrow(
                        () ->
                                new ResourceNotFoundException(
                                        "Allergy not found"
                                )
                );
    }

    // =====================================
    // VALIDATE OTHER REACTION
    // =====================================

    private void validateOtherReaction(

            ReactionType reaction,

            String otherReaction

    ) {

        if (
                reaction == ReactionType.OTHER
        ) {

            if (
                    otherReaction == null
                            ||
                            otherReaction.isBlank()
            ) {

                throw new InvalidRequestException(
                        "Other reaction is required"
                );
            }

        } else {

            if (
                    otherReaction != null
                            &&
                            !otherReaction.isBlank()
            ) {

                throw new InvalidRequestException(
                        "Other reaction is only allowed for OTHER reaction type"
                );
            }
        }
    }
}